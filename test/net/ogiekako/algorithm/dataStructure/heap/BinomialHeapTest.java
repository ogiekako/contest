package net.ogiekako.algorithm.dataStructure.heap;
import org.junit.Test;

public class BinomialHeapTest {
    @Test
    public void test() {
        Tester.test(new Tester.Gen() {
            public Heap<Integer> create() {
                return new BinomialHeap<Integer>();
            }
            public boolean meldable() {
                return true;
            }
        });
    }
}
