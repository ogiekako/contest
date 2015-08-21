package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.graph.*;

import java.util.Arrays;
import java.util.Random;

public class DynamicTreeNodeTest {
    public static int test_Link_Cut_Root(long seed, int numNodes, int numQuery, DynamicTreeNode[] nodes) {
        int[] parent = new int[numNodes];
        Arrays.fill(parent, -1);
        Graph forest = new UndirectedGraph(numNodes);
        Random rnd = new Random(seed);
        int[] query = new int[numQuery];
        int[] x = new int[numQuery];
        int[] y = new int[numQuery];
        for (int i = 0; i < numQuery; i++) {
            query[i] = rnd.nextInt(3);
            if (query[i] == 1) x[i] = rnd.nextInt(numNodes);
            else if (query[i] == 0) {
                x[i] = rnd.nextInt(numNodes - 1) + 1;
                y[i] = rnd.nextInt(x[i]);
            } else {
                x[i] = rnd.nextInt(numNodes);
                y[i] = rnd.nextInt(numNodes);
            }
        }
        for (int i = 0; i < numQuery; i++) {
            if (query[i] == 0) {
                if (parent[x[i]] == -1) {// link
                    parent[x[i]] = y[i];
                    forest.add(new SimpleEdge(x[i], y[i]));
                    nodes[x[i]].link(nodes[y[i]]);
                    if (nodes[x[i]].root() != nodes[y[i]].root()) throw new AssertionError();
                }
            } else if (query[i] == 1) {// cut
                if (parent[x[i]] != -1) {
                    y[i] = parent[x[i]];
                    Edge rem = null;
                    for (Edge e : forest.edges(x[i])) {
                        if (e.to() == parent[x[i]]) rem = e;
                    }
                    if (rem == null) throw new AssertionError();
                    forest.remove(rem);
                    if (nodes[x[i]].root() != nodes[y[i]].root()) throw new AssertionError();

                    nodes[x[i]].cut();
                    parent[x[i]] = -1;

                    if (nodes[x[i]].root() == nodes[y[i]].root()) throw new AssertionError();
                }
            } else {
                boolean exp = GraphUtils.reachable(forest, x[i], y[i]);
                boolean res = nodes[x[i]].root() == nodes[y[i]].root();
                if (exp != res) return i;
            }

        }
        return numQuery;
    }
}
