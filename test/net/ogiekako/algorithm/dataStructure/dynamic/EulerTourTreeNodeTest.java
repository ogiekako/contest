package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.SplayTree;
import net.ogiekako.algorithm.dataStructure.dynamic.test.DynamicTreeTester;
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
    public void test() throws Exception {
        DynamicTreeTester.test(new DynamicTreeTester.Generator() {
            public DynamicTree create(int n) {
                final DynamicTreeNode[] nodes = new DynamicTreeNode[n];
                for (int i = 0; i < n; i++) nodes[i] = new ETT(i);
                return new DynamicTree() {
                    public void cut(int vertex, int _) {
                        nodes[vertex].cut();
                    }
                    public void link(int root, int parent) {
                        nodes[root].link(nodes[parent]);
                    }
                    public int root(int vertex) {
                        return ((ETT) nodes[vertex].root()).id;
                    }
                    public void evert(int v) {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        });
    }
}
