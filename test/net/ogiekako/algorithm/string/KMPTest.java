package net.ogiekako.algorithm.string;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class KMPTest {

    @Test
    public void testGenerateFailureTable() {
        Random rnd = new Random(1928479237L);
        for (int i = 0; i < 50; i++) {
            StringBuilder b = new StringBuilder();
            for (int j = 0; j < 500; j++) {
                b.append((char) (rnd.nextInt(3) + 'A'));
            }
            String s = b.toString();
            int[] next = KMP.generateFailureLink(s);
            int[] exp = generateFailureTableStupid(s);
            Assert.assertArrayEquals(next, exp);
        }
    }

    private int[] generateFailureTableStupid(String s) {
        int[] res = new int[s.length() + 1];
        res[0] = -1;
        for (int i = 1; i <= s.length(); i++) {
            int j = i - 1;
            while (!s.substring(0, i).endsWith(s.substring(0, j))) j--;
            res[i] = j;
        }
        return res;
    }

    @Test
    public void testMatch() {
        Random rnd = new Random(120980298l);
        for (int i = 0; i < 50; i++) {
            String pattern = "";
            for (int j = 0; j < 10; j++) {
                pattern += (char) (rnd.nextInt(3) + 'A');
            }
            int[] next = KMP.generateFailureLink(pattern);
            for (int j = 0; j < 10; j++) {
                StringBuilder text = new StringBuilder();
                for (int k = 0; k < 50000; k++) {
                    text.append((char) (rnd.nextInt(3) + 'A'));
                }
                int res = KMP.match(text.toString(), pattern, next);
                int exp = matchStupid(text.toString(), pattern);
                Assert.assertEquals(exp, res);
            }
        }
    }

    private int matchStupid(String text, String pattern) {
        return text.indexOf(pattern);
    }

}
