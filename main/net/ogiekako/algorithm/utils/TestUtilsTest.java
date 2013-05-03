package net.ogiekako.algorithm.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

public class TestUtilsTest {
    @Test
    public void testGenerateRandomDistinctIntArray() throws Exception {
        int n = 100, k = 50;
        TestUtils.setRandom(1241984L);
        for (int iteration = 0; iteration < 100; iteration++) {
            int[] is = TestUtils.generateRandomDistinctIntArray(n, k);
            if (is.length != k) throw null;
            HashSet<Integer> set = new HashSet<Integer>();
            for (int i : is) {
                if (i < 0 || i >= n) throw null;
                set.add(i);
            }
            if (set.size() != k) throw null;
        }

    }
    static void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }
}
