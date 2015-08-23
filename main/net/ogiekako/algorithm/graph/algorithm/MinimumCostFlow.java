package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Asserts;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

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
    final Graph graph;
    final int n;
    final double[] supply;
    double[] potential;
    double cost;
    Edge[] path;

    public MinimumCostFlow(Graph _graph) {
        this.graph = _graph;
        n = graph.size();
        supply = new double[n];
    }

    // returns Long.MAX_VALUE if impossible
    // Deprecated. Use new MinimumCostFlow(graph).minimumCostFlow(...).
    public static long minimumCostFlow(Graph graph, int source, int sink, long flow) {
        // TOOD: Don't cast to long.
        return (long) new MinimumCostFlow(graph).minimumCostFlow(source, sink, flow);
    }

    // Deprecated. Use new MinimumCostFlow(graph).minimumCostFlow(...).
    public static long minimumCostCirculation(Graph graph) {
        return (long) new MinimumCostFlow(graph).minimumCostCirculation();
    }

    public double minimumCostFlow(int source, int sink) {
        return minimumCostFlow(source, sink, Long.MAX_VALUE);
    }

    // returns Double.POSITIVE_INFINITY if impossible
    public double minimumCostFlow(int source, int sink, long flow) {
        supply[source] += flow;
        supply[sink] -= flow;
        return minimumCostCirculation();
    }

    // returns Double.POSITIVE_INFINITY if impossible
    double minimumCostCirculation() {
        potential = new double[n];
        cost = 0;
        for (int v = 0; v < n; v++) for (Edge e : graph.edges(v)) if (e.residue() > 0 && e.cost() < 0) cancel(e);
        for (; ; ) {
            Queue<Pair<Double, Integer>> que = new PriorityQueue<Pair<Double, Integer>>();
            double[] distance = new double[n];
            Arrays.fill(distance, Double.POSITIVE_INFINITY);
            for (int v = 0; v < n; v++)
                if (supply[v] > 0) {
                    que.offer(Pair.of(0D, v));
                    distance[v] = 0;
                }
            if (que.isEmpty()) return cost;
            path = new Edge[n];
            while (!que.isEmpty()) {
                Pair<Double, Integer> entry = que.poll();
                double dist = entry.first;
                int v = entry.second;
                if (dist > distance[v]) continue;
                for (Edge e : graph.edges(v))
                    if (e.residue() > 0) {
                        int u = e.to();
                        double nDist = (dist + (potential[v] - potential[u])) + e.cost();
                        if (nDist < distance[u] - 1e-9) {
                            distance[u] = nDist;
                            path[u] = e;
                            que.offer(Pair.of(nDist, u));
                        }
                    }
            }
            for (int sink = 0; sink < n; sink++)
                if (supply[sink] < 0) {
                    if (path[sink] == null) return Double.POSITIVE_INFINITY;
                    double pushFlow = -supply[sink];
                    int v;
                    for (v = sink; path[v] != null; v = path[v].from())
                        pushFlow = Math.min(pushFlow, path[v].residue());
                    pushFlow = Math.min(pushFlow, supply[v]);
                    supply[v] -= pushFlow;
                    supply[sink] += pushFlow;
                    for (v = sink; path[v] != null; v = path[v].from()) {
                        path[v].pushFlow(pushFlow);
                        cost += path[v].cost() * pushFlow;
                    }
                }
            for (int v = 0; v < n; v++) if (distance[v] < Double.POSITIVE_INFINITY) potential[v] += distance[v];
        }
    }

    private void cancel(Edge e) {
        supply[e.to()] += e.residue();
        supply[e.from()] -= e.residue();
        cost += e.residue() * e.cost();
        e.pushFlow(e.residue());
    }

    /**
     * Graph shouldn't contain a negative cycle.
     * Returns Double.POSITIVE_INFINITY if it's not possible to send `flow' amount of flow.
     * Otherwise returns the minimum cost.
     * <p/>
     * This method computes the result as follows:
     * - Run Bellman Ford algorithm once to compute initial potentials, which takes O(nm) time.
     * - Run Dijkstra's algorithm O(flow) times, which takes O(flow (m + n log m)) time.
     * <p>
     * Verified:
     * - AOJ 1095 KND Factory (double) (890ms): http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=1489767
     * - AOJ 2290 Attack the Moles (negative) (10510ms): http://judge.u-aizu.ac.jp/onlinejudge/review.jsp?rid=1489770
     * </p>
     */
    public double primalDual(final int s, final int t, double flow) {
        double[] potential = calcInitialPotential(s);
        double res = 0;
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
                    double modifiedCost = e.cost() - (potential[e.to()] - potential[cur.v]);

                    Asserts.assertNonNegative(modifiedCost + 1e-9);
                    // Avoid considering a zero-cycle a negative cycle.
                    modifiedCost = Math.max(modifiedCost, 0);
                    if (costs[e.to()] > costs[cur.v] + modifiedCost) {
                        costs[e.to()] = costs[cur.v] + modifiedCost;
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
                res += e.cost() * min;
                e.pushFlow(min);
            }
            flow -= min;
        }
        return res;
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
        return new BellmanFord(graph).sssp(from);
    }
}
