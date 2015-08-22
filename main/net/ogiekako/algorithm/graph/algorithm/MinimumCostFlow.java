package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

/**
 * Thm.                                                                              <br></br>
 * f : E -> N is a minimum cost flow iff its residual graph G has no negative cost cycle.   <br></br>
 * Proof.                                                                            <br></br>
 * =>) If there is a negative cycle, we can improve f pushing a flow along the cycle.<br></br>
 * <=) Suppose there is a minimum cost flow f'. Then, f'-f is a circular flow in G
 * because if f(e) = c(e), (f'-f)(e) &le 0 and if f(e) = 0, (f'-f)(e) &ge 0.
 * The cycle can be represented by a linear combination of unit capacity circular flows (It can be easily proved by induction.)
 * Hence if G has no negative cost cycle, cost(f') - cost(f) = cost(f'-f) &ge 0, thus f is also a minimum cost flow.
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

    // returns Long.MAX_VALUE if impossible
    // TODO: Return Double.POSITIVE_INFINITY instead of Long.MAX_VALUE.
    public double minimumCostFlow(int source, int sink, long flow) {
        supply[source] += flow;
        supply[sink] -= flow;
        return minimumCostCirculation();
    }

    // returns Long.MAX_VALUE if impossible
    // TODO: Return Double.POSITIVE_INFINITY instead of Long.MAX_VALUE.
    double minimumCostCirculation() {
        potential = new double[n];
        cost = 0;
        for (int v = 0; v < n; v++) for (Edge e : graph.edges(v)) if (e.residue() > 0 && e.cost() < 0) cancel(e);
        for (; ; ) {
            Queue<Pair<Double, Integer>> que = new PriorityQueue<Pair<Double, Integer>>();
            double[] distance = new double[n];
            Arrays.fill(distance, Long.MAX_VALUE);
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
                    if (path[sink] == null) return Long.MAX_VALUE;
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
            for (int v = 0; v < n; v++) if (distance[v] < Long.MAX_VALUE) potential[v] += distance[v];
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
     * If the graph initially has negative cycle(s), those cycle should cancelled before this method is called.
     * Returns Double.POSITIVE_INFINITY if it's not possible to send `flow' amount for flows.
     * Otherwise returns the minimum cost.
     * <p/>
     * This method computes the result as follows:
     * - Run Bellman Ford algorithm once to compute initial potentials, which takes O(nm) time.
     * - Run Dijkstra's algorithm O(flow) times, which takes O(flow (m + n log m)) time.
     * <p>
     *     Verified: AOJ 1095 KND Factory (1030ms)
     * </p>
     */
    public double primalDual(final int s, final int t, double flow) {
        double[] potential = calcInitialPotential(s);
        double res = 0;
        Edge[] prev = new Edge[n];
        Queue<Entry> que = new PriorityQueue<Entry>();
        while (flow > 0) {
            double[] costs = new double[n];
            boolean[] visited = new boolean[n];
            Arrays.fill(costs, Double.POSITIVE_INFINITY);
            costs[s] = 0;
            prev[s] = prev[t] = null;
            que.clear();
            que.offer(new Entry(0, s));
            while (!que.isEmpty()) {
                Entry cur = que.poll();
                if (visited[cur.v]) continue;
                visited[cur.v] = true;
                for (Edge e : graph.edges(cur.v))
                    if (e.residue() > 0 && !visited[e.to()]) {
                        if (costs[e.to()] > costs[cur.v] + e.cost() + potential[cur.v] - potential[e.to()]) {
                            costs[e.to()] = costs[cur.v] + e.cost() + potential[cur.v] - potential[e.to()];
                            prev[e.to()] = e;
                            que.offer(new Entry(costs[e.to()], e.to()));
                        }
                    }
            }
            if (prev[t] == null) return Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                potential[i] = costs[i];
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
        double[] potential = new double[n];
        Arrays.fill(potential, Double.POSITIVE_INFINITY);
        potential[from] = 0;
        for (int iter = 0; iter < n; iter++) {
            boolean updated = false;
            for (int i = 0; i < n; i++) {
                for (Edge e : graph.edges(i)) {
                    if (e.residue() <= 0) continue;
                    if (potential[e.to()] > e.cost() + potential[i]) {
                        updated = true;
                        potential[e.to()] = e.cost() + potential[i];
                    }
                }
            }
            if (!updated) {
                return potential;
            }
        }
        throw new IllegalArgumentException("Negative Cycle");
    }
}
