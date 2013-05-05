package net.ogiekako.algorithm.graph.algorithm;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
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
    final long[] supply;
    long[] potential;
    long cost;
    Edge[] path;
    MinimumCostFlow(Graph _graph) {
        this.graph = _graph;
        n = graph.size();
        supply = new long[n];
    }
    long minimumCostFlow(int source, int sink, long flow) {
        supply[source] += flow; supply[sink] -= flow;
        return minimumCostCirculation();
    }

    // returns Long.MAX_VALUE if impossible
    long minimumCostCirculation() {
        potential = new long[n];
        cost = 0;
        for (int v = 0; v < n; v++) for (Edge e : graph.edges(v)) if (e.residue() > 0 && e.cost() < 0) cancel(e);
        for (; ; ) {
            Queue<Pair<Long, Integer>> que = new PriorityQueue<Pair<Long, Integer>>();
            long[] distance = new long[n];
            Arrays.fill(distance, Long.MAX_VALUE);
            for (int v = 0; v < n; v++)
                if (supply[v] > 0) {
                    que.offer(Pair.of(0L, v));
                    distance[v] = 0;
                }
            if (que.isEmpty()) return cost;
            path = new Edge[n];
            while (!que.isEmpty()) {
                Pair<Long, Integer> entry = que.poll();
                long dist = entry.first;
                int v = entry.second;
                if (dist > distance[v]) continue;
                for (Edge e : graph.edges(v))
                    if (e.residue() > 0) {
                        int u = e.to();
                        long nDist = (dist + (potential[v] - potential[u])) + e.cost();
                        if (nDist < distance[u]) {
                            distance[u] = nDist;
                            path[u] = e;
                            que.offer(Pair.of(nDist, u));
                        }
                    }
            }
            for (int sink = 0; sink < n; sink++)
                if (supply[sink] < 0) {
                    if (path[sink] == null) return Long.MAX_VALUE;
                    long pushFlow = -supply[sink];
                    int v;
                    for (v = sink; path[v] != null; v = path[v].from())
                        pushFlow = Math.min(pushFlow, path[v].residue());
                    pushFlow = Math.min(pushFlow, supply[v]);
                    supply[v] -= pushFlow; supply[sink] += pushFlow;
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

    // returns Long.MAX_VALUE if impossible
    public static long minimumCostFlow(Graph graph, int source, int sink, long flow) {
        return new MinimumCostFlow(graph).minimumCostFlow(source, sink, flow);
    }
    public static long minimumCostCirculation(Graph graph) {
        return new MinimumCostFlow(graph).minimumCostCirculation();
    }
}
