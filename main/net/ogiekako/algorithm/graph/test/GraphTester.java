package net.ogiekako.algorithm.graph.test;

import net.ogiekako.algorithm.graph.BidirectionalGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class GraphTester<V> {
    public static interface Generator<V> {
        V result(Graph graph);

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
    private Collection<Graph> testGraphs = new ArrayList<Graph>();

    private GraphTester(GraphTester.Generator<V> gen) {
        this.gen = gen;
    }

    public static <V> void test(GraphTester.Generator<V> gen) {
        GraphTester<V> tester = new GraphTester<V>(gen);
        tester.test();
    }

    private void test() {
        generate();
        // check validity
        int numTest = 0;
        int maxNode = 0;
        int maxEdge = 0;

        long maxTime = 0;
        for (Graph graph : testGraphs) {
            if (!gen.valid(graph)) continue;
            long start = System.currentTimeMillis();
            V res = gen.result(graph);
            long time = System.currentTimeMillis() - start;
            maxTime = Math.max(time, maxTime);
            gen.assertCorrect(graph, res);
            numTest++;
            maxNode = Math.max(maxNode, graph.size());
            int numEdge = 0;
            for(int i=0;i<graph.size();i++)numEdge += graph.edges(i).size();
            maxEdge = Math.max(maxEdge, numEdge);
        }

        System.out.printf("max time = %.2fs\n", maxTime / 1000.0);
        System.out.printf("#test = %d\n", numTest);
        System.out.printf("max #node = %d\n",maxNode);
        System.out.printf("max #edge = %d\n",maxEdge);
    }

    private void generate() {
        for (int n = 1; n <= 10; n++) {
            for (int m = 0; m <= 20; m++) {
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
        star(order, new Graph(order.length));
        star(order, new BidirectionalGraph(order.length));
    }

    private void star(int[] order, Graph graph) {
        for (int i = 1; i < order.length; i++){
            graph.add(gen.edge(order[0], order[i], rnd));
        }
        testGraphs.add(graph);
    }

    private void path(int numNode) {
        path(ArrayUtils.createOrder(numNode));

        int[] order = ArrayUtils.createOrder(numNode);
        ArrayUtils.reverse(order);
        path(order);

        order = ArrayUtils.createOrder(numNode);
        ArrayUtils.shuffle(order);
        path(order);
    }

    private void path(int[] order) {
        path(order, new Graph(order.length));
        path(order, new BidirectionalGraph(order.length));
    }

    private void path(int[] order, Graph graph) {
        for (int i = 0; i < order.length - 1; i++) {
            graph.add(order[i], order[i + 1]);
        }
        testGraphs.add(graph);
    }

    private void random(int numNode, int numEdge) {
        random(numNode, numEdge, new Graph(numNode));
        random(numNode, numEdge, new BidirectionalGraph(numNode));
    }

    private void random(int numNode, int numEdge, Graph graph) {
        for (int i = 0; i < numEdge; i++) {
            int from = rnd.nextInt(numNode);
            int to = rnd.nextInt(numNode);
            graph.add(gen.edge(from, to, rnd));
        }
        testGraphs.add(graph);
    }
}
