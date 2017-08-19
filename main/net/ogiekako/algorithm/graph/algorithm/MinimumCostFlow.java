package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Asserts;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <pre>
 * Thm.                                                                              <br></br>
 * f : E -> N is a minimum cost flow <=> its residual graph G has no negative cost cycle.   <br></br>
 * Proof.                                                                            <br></br>
 * =>) If there is a negative cycle, we can improve f pushing a flow along the cycle.<br></br>
 * <=) Suppose there is a minimum cost flow f'. Then, f'-f is a circular flow in G
 * because if f(e) = c(e), (f'-f)(e) &le 0 and if f(e) = 0, (f'-f)(e) &ge 0.
 * The cycle can be represented by a linear combination of unit capacity circular flows (It can be easily proved by induction.)
 * Hence if G has no negative cost cycle, cost(f') - cost(f) = cost(f'-f) &ge 0, thus f is also a minimum cost flow.
 * </pre>
 * <p/>
 * Primal Dual method.
 * Ref: <a href = "http://www.kitsunemimi.org/icpc/minimum_cost_flow.html">kitsune- ICPC Minimum Cost Flow Library</a>
 * <a href="http://www.amazon.com/dp/3642244874">B. Korte, J. Vygen; Combinatorial Optimization: Theory and Algorithms</a>
 * <p/>
 * Problems: <a href="http://apps.topcoder.com/forums/?module=Thread&threadID=589835&start=0&mc=18#967987">TopCoder forum</a>
 * <a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=17&page=show_problem&problem=1535">Data Flow (UVA 10594)</a>
 * <a href="http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1687">Crime Wave (Assignment Problem)</a>
 * <a href="http://poj.org/problem?id=2195">Going Home (Assignment problem)</a>
 */
public class MinimumCostFlow {
    Graph graph;
    final int n;
    final double[] supply;
    double totalCost;
    double totalFlow;

    public MinimumCostFlow(Graph graph) {
        this.graph = graph;
        n = graph.size();
        supply = new double[n];
    }

    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    // returns Double.POSITIVE_INFINITY if impossible.
    public double minCostFlowWithNegativeCostCancellation(int s, int t, double flow) {
        supply[s] += flow;
        supply[t] -= flow;
        return minimumCostCirculation();
    }

    /**
     * Solve minimum cost circulation problem.
     * F = flow + (負の辺の容量の総和) としたとき、O(F (m + n) log m)).
     */
    // returns Double.POSITIVE_INFINITY if impossible
    public double minimumCostCirculation() {
        double[] potential = new double[n];
        totalCost = 0;

        // Cancel negative edges.
        for (int v = 0; v < n; v++)
            for (Edge e : graph.edges(v))
                if (e.residue() > 0 && e.cost() < 0) {
                    supply[e.to()] += e.residue();
                    supply[e.from()] -= e.residue();
                    totalCost += e.residue() * e.cost();
                    e.pushFlow(e.residue());
                }
        // TODO: the code below has almost the same logic used in primalDual. Extract a common method.
        for (; ; ) {
            Queue<Entry> que = new PriorityQueue<Entry>();
            double[] distance = new double[n];
            Arrays.fill(distance, Double.POSITIVE_INFINITY);
            for (int v = 0; v < n; v++)
                if (supply[v] > 0) {
                    que.offer(new Entry(0D, v));
                    distance[v] = 0;
                }
            if (que.isEmpty()) return totalCost;
            Edge[] prev = new Edge[n];
            while (!que.isEmpty()) {
                Entry entry = que.poll();
                double dist = entry.dist;
                int v = entry.v;
                if (dist > distance[v]) continue;
                for (Edge e : graph.edges(v)) {
                    if (e.residue() <= 0) continue;
                    int u = e.to();
                    double reducedCost = e.cost() + potential[v] - potential[u];
                    // Avoid considering a zero-cycle a negative cycle.
                    reducedCost = Math.max(reducedCost, 0);
                    if (distance[u] > dist + reducedCost) {
                        distance[u] = dist + reducedCost;
                        prev[u] = e;
                        que.offer(new Entry(distance[u], u));
                    }
                }
            }
            for (int sink = 0; sink < n; sink++) {
                if (supply[sink] >= 0) continue;

                if (prev[sink] == null) return Double.POSITIVE_INFINITY;
                double pushFlow = -supply[sink];
                int v;
                for (v = sink; prev[v] != null; v = prev[v].from())
                    pushFlow = Math.min(pushFlow, prev[v].residue());
                pushFlow = Math.min(pushFlow, supply[v]);
                supply[v] -= pushFlow;
                supply[sink] += pushFlow;
                for (v = sink; prev[v] != null; v = prev[v].from()) {
                    prev[v].pushFlow(pushFlow);
                    totalCost += prev[v].cost() * pushFlow;
                }
            }
            for (int v = 0; v < n; v++) if (distance[v] < Double.POSITIVE_INFINITY) potential[v] += distance[v];
        }
    }

    public double getFlow() {
        return totalFlow;
    }

    public double getCost() {
        return totalCost;
    }

    /**
     * Graph shouldn't contain a negative cost cycle.
     * Returns the minimum cost or Double.POSITIVE_INFINITY if it's not possible to send `flow' amount of flow.
     * getMaxflow() and getCost() can be used after this method is called for getting the maximum flow sent
     * and the minimum cost for the flow.
     * <p/>
     * This method computes the result as follows:
     * - Run Bellman Ford algorithm once to compute initial potentials, which takes O(nm) time.
     * - Run Dijkstra's algorithm O(flow) times, which takes O(flow (m + n) log m)) time.
     * <p>
     * Verified:
     * - AOJ 1095 KND Factory (double) (890ms): http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=1489767
     * - AOJ 2290 Attack the Moles (negative) (10510ms): http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=1489770
     * </p>
     */
    public double primalDual(final int s, final int t, double flow) {
        double[] potential = calcInitialPotential(s);
        totalCost = 0;
        totalFlow = 0;
        while (flow > 0) {
            boolean[] visited = new boolean[n];
            double[] costs = new double[n];
            Edge[] prev = new Edge[n];
            Queue<Entry> que = new PriorityQueue<Entry>();

            Arrays.fill(costs, Double.POSITIVE_INFINITY);
            costs[s] = 0;
            que.offer(new Entry(0, s));
            while (!que.isEmpty()) {
                Entry cur = que.poll();
                if (visited[cur.v]) continue;
                visited[cur.v] = true;
                for (Edge e : graph.edges(cur.v)) {
                    if (e.residue() <= 0) continue;
                    double reducedCost = e.cost() - (potential[e.to()] - potential[cur.v]);

                    Asserts.assertNonNegative(reducedCost + 1e-9);
                    // Avoid considering a zero-cycle a negative cycle.
                    reducedCost = Math.max(reducedCost, 0);
                    if (costs[e.to()] > costs[cur.v] + reducedCost) {
                        costs[e.to()] = costs[cur.v] + reducedCost;
                        prev[e.to()] = e;
                        que.offer(new Entry(costs[e.to()], e.to()));
                    }
                }
            }
            if (prev[t] == null) return Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                potential[i] += costs[i];
            }
            double min = flow;
            for (Edge e = prev[t]; e != null; e = prev[e.from()]) {
                min = Math.min(min, e.residue());
            }
            for (Edge e = prev[t]; e != null; e = prev[e.from()]) {
                totalCost += e.cost() * min;
                e.pushFlow(min);
            }
            flow -= min;
            totalFlow += min;
        }
        return totalCost;
    }

    class Entry implements Comparable<Entry> {
        double dist;
        int v;

        public Entry(double dist, int v) {
            this.dist = dist;
            this.v = v;
        }

        @Override
        public int compareTo(Entry o) {
            return Double.compare(dist, o.dist);
        }
    }

    private double[] calcInitialPotential(int from) {
        boolean allPositive = true;
        for (int i = 0; i < n; i++) {
            for (Edge e : graph.edges(i)) {
                if (e.residue() > 0 && e.cost() < 0) allPositive = false;
            }
        }
        return allPositive ? new Dijkstra(graph).sssp(from) : new BellmanFord(graph).sssp(from);
    }
}
