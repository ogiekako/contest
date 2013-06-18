package net.ogiekako.algorithm.dataStructure.dynamic;

import net.ogiekako.algorithm.dataStructure.segmentTree.SegTreeSemigroup;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SegTreeSemigroupTest {

    @Test
    public void testString() throws Exception {
        Random rnd = new Random(11234L);
        int N = 128;
        SegTreeSemigroup<String> seg = new SegTreeSemigroup<String>(N) {
            @Override
            protected String identity() {
                return "";
            }

            @Override
            protected String operate(String first, String second) {
                return first + second;
            }
        };
        char[] cs = new char[N];
        for (int i = 0; i < N; i++) {
            cs[i] = (char) ('a' + rnd.nextInt(26));
            seg.set(i, cs[i] + "");
        }
        for (int i = 0; i < 10000; i++) {
            if (rnd.nextBoolean()) {
                int p = rnd.nextInt(N);
                char c = (char) ('a' + rnd.nextInt(26));
                cs[p] = c;
                seg.set(p, c + "");
            } else {
                int to = rnd.nextInt(N + 1);
                int from = rnd.nextInt(to + 1);
                String res = seg.convolution(from, to);
                String exp = "";
                for (int j = from; j < to; j++) exp += cs[j];
                Assert.assertEquals(exp, res);
            }
        }
    }

}
