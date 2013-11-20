package net.ogiekako.algorithm.dataStructure.dynamic;
import net.ogiekako.algorithm.dataStructure.dynamic.test.DynamicTreeTester;
import org.junit.Test;
public class EulerTourTreeTest {
    @Test
    public void test() throws Exception {
        DynamicTreeTester.setLog(false);
        long start = System.currentTimeMillis();
        DynamicTreeTester.test(new DynamicTreeTester.Generator() {
            public DynamicTree create(int n) {
                return new EulerTourTree(n);
            }
        });
        long time = System.currentTimeMillis() - start;
        if (time > 2000) throw new AssertionError(time);
    }
}