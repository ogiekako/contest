package net.ogiekako.algorithm.dataStructure.dynamic.dyncon;
import net.ogiekako.algorithm.dataStructure.dynamic.DynamicTree;
import net.ogiekako.algorithm.dataStructure.dynamic.test.DynamicTreeTester;
import org.junit.Test;
public class EulerTourTreeTest {
    @Test
    public void test() {
        DynamicTreeTester.setLog(false);
        long start = System.currentTimeMillis();
        DynamicTreeTester.test(new DynamicTreeTester.Generator() {
            public DynamicTree create(int n) {
                return new EulerTourTree(n);
            }
        });
        long time = System.currentTimeMillis() - start;
        if (time > 1000) throw new AssertionError(time);

    }
}
