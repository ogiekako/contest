package net.ogiekako.algorithm.graph.testing;

import junit.framework.Assert;
import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GraphTester<V> {
    public static interface Generator<V> {
        V result(Graph graph, Random rnd);

        void assertCorrect(Graph graph, V result);

        /**
         * @param graph given graph
         * @return true if the graph is a valid instance of the algorithm.
         */
        boolean valid(Graph graph);

        Edge edge(int from, int to, Random rnd);
    }

    private final GraphTester.Generator<V> gen;
    private Random rnd = new Random(12012830L);
    private Collection<GraphGenerator> testGraphs = new ArrayList<GraphGenerator>();

    private GraphTester(GraphTester.Generator<V> gen) {
        this.gen = gen;
    }

    public static <V> void test(GraphTester.Generator<V> gen) {
        GraphTester<V> tester = new GraphTester<V>(gen);
        tester.test();
    }

    private void test() {
        generate();
        int numTest = 0;
        int maxNode = 0;
        int maxEdge = 0;

        long maxTime = 0;
        for (GraphGenerator graphGenerator : testGraphs) {
            Graph graph = graphGenerator.generate();
            if (!gen.valid(graph)) continue;
            long start = System.currentTimeMillis();
            V res = gen.result(graph, rnd);
            long time = System.currentTimeMillis() - start;
            maxTime = Math.max(time, maxTime);
            gen.assertCorrect(graph, res);
            numTest++;
            maxNode = Math.max(maxNode, graph.size());
            int numEdge = 0;
            for (int i = 0; i < graph.size(); i++) numEdge += graph.edges(i).size();
            maxEdge = Math.max(maxEdge, numEdge);
        }

        Assert.assertTrue(numTest > 10);

        System.out.printf("max time = %.2fs\n", maxTime / 1000.0);
        System.out.printf("#test = %d\n", numTest);
        System.out.printf("max #node = %d\n", maxNode);
        System.out.printf("max #edge = %d\n", maxEdge);
    }

    interface GraphGenerator {
        Graph generate();
    }

    class RandomGraphGenerator implements GraphGenerator {
        private final int n, m;
        boolean directed;
        RandomGraphGenerator(int n, int m, boolean directed) {
            this.n = n; this.m = m;
            this.directed = directed;
        }

        public Graph generate() {
            Graph graph = directed ? new Graph(n) : new UndirectedGraph(n);
            for (int i = 0; i < m; i++) {
                int from = rnd.nextInt(n);
                int to = rnd.nextInt(n);
                graph.add(gen.edge(from, to, rnd));
            }
            return graph;
        }
    }

    class PathGenerator implements GraphGenerator {
        private int[] order;
        boolean directed;
        PathGenerator(int[] order, boolean directed) {
            this.order = order;
            this.directed = directed;
        }
        public Graph generate() {
            int n = order.length;
            Graph graph = directed ? new Graph(n) : new UndirectedGraph(n);
            for (int i = 0; i < order.length - 1; i++) {
                graph.add(gen.edge(order[i], order[i + 1], rnd));
            }
            return graph;
        }
    }

    class StarGenerator implements GraphGenerator {
        private int[] order;
        private final boolean directed;
        StarGenerator(int[] order, boolean directed) {
            this.order = order;
            this.directed = directed;
        }
        public Graph generate() {
            int n = order.length;
            Graph graph = directed ? new Graph(n) : new UndirectedGraph(n);
            for (int i = 1; i < order.length; i++) {
                graph.add(gen.edge(order[0], order[i], rnd));
            }
            return graph;
        }
    }

    private void generate() {
        for (int n = 1; n <= 10; n++) {
            for (int m = 0; m <= 20; m = m == 0 ? 1 : m * 2) {
                random(n, m);
            }
        }
        random(100, 100);
        random(100, 1000);
        random(1000, 1000);
        random(1000, 100000);
        random(100000, 100000);
        path(100);
        path(100000);
        star(10);
        star(100);
        star(1000);
        star(10000);
        star((int) Math.sqrt(1e7));
        star(100000);
    }

    private void star(int node) {
        star(ArrayUtils.createOrder(node));
        star(ArrayUtils.reversed(ArrayUtils.createOrder(node)));
        star(ArrayUtils.shuffled(ArrayUtils.createOrder(node)));
    }

    private void star(int[] order) {
        testGraphs.add(new StarGenerator(order, true));
        testGraphs.add(new StarGenerator(order, false));
    }

    private void path(int node) {
        path(ArrayUtils.createOrder(node));
        path(ArrayUtils.reversed(ArrayUtils.createOrder(node)));
        path(ArrayUtils.shuffled(ArrayUtils.createOrder(node)));
    }

    private void path(int[] order) {
        testGraphs.add(new PathGenerator(order, true));
        testGraphs.add(new PathGenerator(order, false));
    }

    private void random(int numNode, int numEdge) {
        testGraphs.add(new RandomGraphGenerator(numNode, numEdge, true));
        testGraphs.add(new RandomGraphGenerator(numNode, numEdge, false));
    }
}
