package net.ogiekako.algorithm.dataStructure.heap;
import org.junit.Test;
public class BinaryHeapTest {
    @Test
    public void test() {
        Tester.test(new Tester.Gen() {
            public Heap<Integer> create() {
                return new BinaryHeap<Integer>();
            }
            public boolean meldable() {
                return false;
            }
        });
    }
}
