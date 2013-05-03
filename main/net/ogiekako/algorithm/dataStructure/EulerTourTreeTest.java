package net.ogiekako.algorithm.dataStructure;

import net.ogiekako.algorithm.dataStructure.tree.DynamicTree;
import net.ogiekako.algorithm.dataStructure.tree.DynamicTreeTest;
import net.ogiekako.algorithm.dataStructure.tree.SplayTree;
import org.junit.Test;

public class EulerTourTreeTest {
    public static class ETT extends EulerTourTree{
        int id;
        ETT(int id){
            this.id = id;
        }
        public SplayTree<EulerTourTree> getBegin(){
            return begin;
        }

        @Override
        public String toString() {
            return ""+id;
        }
    }
    @Test
    public void testLinkCutRoot() throws Exception {
        int n = 5000, m = 100000;
        DynamicTree[] nodes = new DynamicTree[n];
        for (int i = 0; i < n; i++)nodes[i] = new ETT(i);
        DynamicTreeTest.test_Link_Cut_Root(120812048L, n, m, nodes);
    }
}
