package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.graph.*;

import java.util.Arrays;
import java.util.Random;

public class DynamicTreeTest {

    public static int test_Link_Cut_Root(long seed, int numNodes, int numQuery, DynamicTree[] nodes) {
        int[] parent = new int[numNodes];
        Arrays.fill(parent, -1);
        Graph forest = new BidirectionalGraph(numNodes);
        Random rnd = new Random(seed);
        int[] query = new int[numQuery];
        int[] nodeId = new int[numQuery];
        int[] nodeId2 = new int[numQuery];
        for (int i = 0; i < numQuery; i++) {
            query[i] = rnd.nextInt(3);
            if (query[i] == 1) nodeId[i] = rnd.nextInt(numNodes);
            else if (query[i] == 0) {
                nodeId[i] = rnd.nextInt(numNodes - 1) + 1;
                nodeId2[i] = rnd.nextInt(nodeId[i]);
            } else {
                nodeId[i] = rnd.nextInt(numNodes);
                nodeId2[i] = rnd.nextInt(numNodes);
            }
        }
        for (int i = 0; i < numQuery; i++) {
//            System.err.println(Arrays.toString(ps)+" "+x[i]+" "+y[i] + " " + q[i]);
            if (query[i] == 0) {
                if (parent[nodeId[i]] == -1) {// link
                    parent[nodeId[i]] = nodeId2[i];
//                    debug(toString(nodes[x[i]]));
//                    debug(toString(nodes[y[i]]));
                    forest.add(new SimpleEdge(nodeId[i], nodeId2[i]));
                    nodes[nodeId[i]].link(nodes[nodeId2[i]]);
//                    debug("after");
//                    debug(toString(nodes[x[i]]));
//                    debug(toString(nodes[y[i]]));
                    if (nodes[nodeId[i]].root() != nodes[nodeId2[i]].root()) throw new AssertionError();
                }
            } else if (query[i] == 1) {// cut
                if (parent[nodeId[i]] != -1) {
                    nodeId2[i] = parent[nodeId[i]];
                    Edge rem = null;
                    for (Edge e : forest.edges(nodeId[i])) {
                        if (e.to() == parent[nodeId[i]]) rem = e;
                    }
                    if (rem == null) throw new AssertionError();
                    forest.remove(rem);
//                    debug(toString(nodes[x[i]]));
//                    debug(toString(nodes[y[i]]));
                    if (nodes[nodeId[i]].root() != nodes[nodeId2[i]].root()) throw new AssertionError();

                    nodes[nodeId[i]].cut();
                    parent[nodeId[i]] = -1;

                    if (nodes[nodeId[i]].root() == nodes[nodeId2[i]].root()) throw new AssertionError();
                }
            } else {
                boolean exp = GraphUtils.reachable(forest, nodeId[i], nodeId2[i]);
                boolean res = nodes[nodeId[i]].root() == nodes[nodeId2[i]].root();
                if (exp != res) return i;
            }

        }
        return numQuery;
    }
}
