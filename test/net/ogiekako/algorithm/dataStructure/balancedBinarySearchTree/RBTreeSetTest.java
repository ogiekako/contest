package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;
import net.ogiekako.algorithm.utils.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
public class RBTreeSetTest {
    private boolean DEBUG = true;
    Set<Integer> expected;
    RBTreeSet<Integer> actual;
    boolean assertSame = true;
    Random rnd;
    @Before
    public void setUp() throws Exception {
        init();
    }
    void init() {
        expected = new TreeSet<Integer>();
        actual = new RBTreeSet<Integer>();
        rnd = new Random(1240192840L);
        assertSame = true;
    }

    void add(int a) {
//        System.err.println("now: " + Arrays.toString(actual.toArray(new Integer[actual.size()])));
//        System.err.println("add " + a);
        assertValidity(expected.add(a), actual.add(a));
    }
    void remove(int a) {
//        System.err.println("now: " + expected);
//        System.err.println("remove " + a);
        assertValidity(expected.remove(a), actual.remove(a));
    }
    void clear() {
        System.err.println("clear");
        expected.clear();
        actual.clear();
        assertValidity(true, true);
    }
    private void assertValidity(boolean exp, boolean act) {
        Assert.assertEquals(exp, act);
        Assert.assertEquals(expected.size(), actual.size());
        if (assertSame) {
            Integer[] expArr = expected.toArray(new Integer[expected.size()]);
            Integer[] actArr = actual.toArray(new Integer[actual.size()]);
            Assert.assertArrayEquals(expArr, actArr);
        }
    }
    @Test
    public void testAddRemove() throws Exception {
        add(1); // [1]
        add(1); // [1]
        add(3); // [1,3]
        add(2); // [1,2,3]
        remove(3); // [1,2]
        remove(4); // [1,2]
        add(4);    // [1,2,4]
        add(5);    // [1,2,4,5]
        add(10);   // [1,2,4,5,10]
        add(9);    // [1,2,4,5,9,10]
        add(6);    // [1,2,4,5,6,9,10]
        add(3);    // [1,2,3,4,5,6,9,10]
        remove(2); // [1,3,4,5,6,9,10]
        add(3);    // [1,3,4,5,6,9,10]
        remove(5); // [1,3,4,6,9,10]
        remove(1); // [3,4,6,9,10]
        add(5);    // [3,4,5,6,9,10]
        remove(10);// [3,4,5,6,9]
        remove(3); // [4,5,6,9]
        remove(5); // [4,6,9]
        remove(4); // [6,9]
        remove(9); // [6]
        remove(6); // []
    }
    @Test
    public void testClear() {
        for (int i = 0; i < 10; i++) {
            int n = 1000;
            int[] arr = TestUtils.generateRandomIntArray(n, 100 * 2, rnd);
            for (int a : arr) {
                int val = a / 2;
                boolean add = a % 2 == 0;
                if (a % 10 == 0) clear();
                else if (add) add(val);
                else remove(val);
                PersistentRedBlackTree.assertValidCondition(actual.root);
            }
        }
    }

    @Test(timeout = 2000L)
    public void testSpeed() {
        assertSame = false;
        int n = 100000;
        int[] arr = TestUtils.generateRandomIntArray(n, 10000 * 2);
        for (int a : arr) {
            int val = a / 2;
            boolean add = a % 2 == 0;
            if (add) add(val);
            else remove(val);
        }
    }


}
