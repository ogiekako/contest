package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.SplayTree;
import org.junit.Test;

public class EulerTourTreeNodeTest {
    public static class ETT extends EulerTourTreeNode {
        int id;
        ETT(int id) {
            this.id = id;
        }
        public SplayTree<EulerTourTreeNode> getBegin() {
            return begin;
        }

        @Override
        public String toString() {
            return "" + id;
        }
    }
    @Test
    public void testLinkCutRoot() throws Exception {
        int n = 5000, m = 100000;
        DynamicTree[] nodes = new DynamicTree[n];
        for (int i = 0; i < n; i++) nodes[i] = new ETT(i);
        DynamicTreeTest.test_Link_Cut_Root(120812048L, n, m, nodes);
    }
}
