package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.dataStructure.dynamic.test.DynamicTreeTester;
import org.junit.Test;

public class EulerTourTreeTest {
    @Test(timeout=3000)
    public void test() throws Exception {
        DynamicTreeTester.setLog(false);
        DynamicTreeTester.test(new DynamicTreeTester.Generator() {
            public DynamicTree create(int n) {
                return new EulerTourTree(n);
            }
        });
    }
}
