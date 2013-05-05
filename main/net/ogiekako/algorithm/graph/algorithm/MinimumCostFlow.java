package net.ogiekako.algorithm.graph.algorithm;
import net.ogiekako.algorithm.graph.Graph;
/**
 * Thm.                                                                              <br></br>
 * f : E -> N is a minimum cost flow iff its residual graph G has no negative cost cycle.   <br></br>
 * Proof.                                                                            <br></br>
 * =>) If there is a negative cycle, we can improve f pushing a flow along the cycle.<br></br>
 * <=) Suppose there is a minimum cost flow f'. Then, f'-f is a circular flow in G
 * because if f(e) = c(e), (f'-f)(e) &le 0 and if f(e) = 0, (f'-f)(e) &ge 0.
 * The cycle can be represented by a linear combination of unit capacity circular flows (It can be easily proved by induction.)
 * Hence if G has no negative cost cycle, cost(f') - cost(f) = cost(f'-f) &ge 0, thus f is also a minimum cost flow.
 */
public class MinimumCostFlow {
    Graph graph;
    MinimumCostFlow(Graph graph) {
        this.graph = graph;
    }
    long compute(int from, int to) {
        throw new UnsupportedOperationException();
    }
}
