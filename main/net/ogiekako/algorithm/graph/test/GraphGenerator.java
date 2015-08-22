package net.ogiekako.algorithm.graph.test;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphUtils;
import net.ogiekako.algorithm.graph.SimpleEdge;

import java.util.HashSet;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 4:17
 * To change this template use File | Settings | File Templates.
 */
public abstract class GraphGenerator {
    private static Random RANDOM = new Random(1240810487107L);

    public static final GraphGenerator ACYCLIC = new GraphGenerator() {
        @Override
        public boolean isValid(Graph graph) {
            return GraphUtils.isAcyclic(graph);
        }
    };
    public static final GraphGenerator SIMPLE = new GraphGenerator() {
        @Override
        public boolean isValid(Graph graph) {
            HashSet<Edge> dejavu = new HashSet<Edge>();
            for (int i = 0; i < graph.size(); i++)
                for (Edge e : graph.edges(i)) {
                    if (dejavu.contains(e)) return false;
                    if (e.from() == e.to()) return false;
                    dejavu.add(e);
                }
            return true;
        }
    };
    public static final GraphGenerator ANY = new GraphGenerator() {
        @Override
        public boolean isValid(Graph graph) {
            return true;
        }
    };

    public abstract boolean isValid(Graph graph);

    public Graph generate(int vertexCount, int edgeCount) {
        return generate(vertexCount, edgeCount, RANDOM);
    }

    public Graph generate(int vertexCount, int edgeCount, Random rnd) {
        Graph graph = new Graph(vertexCount);
        for (int i = 0; i < edgeCount; i++) {
            int source = rnd.nextInt(vertexCount);
            int dest = rnd.nextInt(vertexCount);
            Edge edge = new SimpleEdge(source, dest);
            graph.add(edge);
            if (!isValid(graph)) {
                graph.remove(edge);
                i--;
            }
        }
        return graph;
    }
}
