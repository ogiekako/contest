package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.RhoSequence;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Euler375 {
    static final int S0 = 290797;
    static final int modulo = 50515093;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long n = in.nextLong();
        RhoSequence seq = RhoSequence.generate(new RhoSequence.Function() {
            public int first() {
                return (int) ((long) S0 * S0 % modulo);
            }

            public int next(int before) {
                return (int) ((long) before * before % modulo);
            }
        });
        long res = seq.calcSumOfMinOfAllSubrange(n);
        out.println(res);
    }

    public static void main(String[] args) {
        Random rnd = new Random(1124908112001248L);
        int MX = 1000;
        int MXLEN = 1000;
        for (int i = 0; i < 10000; i++) {
            final int a = rnd.nextInt(MX) + 1;
            final int b = rnd.nextInt(MX) + 1;
            RhoSequence seq = RhoSequence.generate(new RhoSequence.Function() {
                public int first() {
                    return a % b;
                }

                public int next(int before) {
                    return (int) ((long) before * before % b);
                }
            });
            int len = rnd.nextInt(MXLEN) + 1;
            long exp = 0;
            int[] array = new int[len];
            for (int j = 0; j < len; j++) array[j] = seq.get(j);
            for (int j = 0; j < len; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = j; k < len; k++) {
                    min = Math.min(min, array[k]);
                    exp += min;
                }
            }
            long res = seq.calcSumOfMinOfAllSubrange(len);
            if (exp != res) {
                System.err.println(Arrays.toString(array) + " " + seq.head + " " + seq.period + " " + len);
                System.err.println(exp + " " + res);
                throw new RuntimeException();
            }
            System.err.println(exp + " " + res);
        }
    }
}
