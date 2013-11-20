package on2013_06.on2013_06_01_Google_Code_Jam_Round_2_2013.A___Ticket_Swapping;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.math.BigInteger;
import java.util.Arrays;

public class TaskA {
    long N;
    BigInteger MOD = BigInteger.valueOf(1000002013L);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        for (int test = 1; test <= T; test++) {
            N = in.nextLong();
            int M = in.nextInt();
            P[] os = new P[M], es = new P[M];
            BigInteger exp = BigInteger.ZERO;
            for (int i = 0; i < M; i++) {
                long o = in.nextLong(), e = in.nextLong(), p = in.nextLong();
                os[i] = new P(o, p);
                es[i] = new P(e, p);
                exp = exp.add(BigInteger.valueOf(calc(e - o)).multiply(BigInteger.valueOf(p)));
            }
            Arrays.sort(os);
            Arrays.sort(es);
            BigInteger act = BigInteger.ZERO;
            for (int i = 0; i < M; i++) {
                for (int j = M - 1; j >= 0; j--) {
                    if (os[j].x <= es[i].x) {
                        long dist = es[i].x - os[j].x;
                        long move = Math.min(os[j].person, es[i].person);
                        act = act.add(BigInteger.valueOf(calc(dist)).multiply(BigInteger.valueOf(move)));
                        os[j].person -= move;
                        es[i].person -= move;
                    }
                }
            }
            long res = exp.subtract(act).mod(MOD).longValue();
            out.printFormat("Case #%d: %d\n", test, res);
        }
    }
    private long calc(long n) {
        return (2 * N - n + 1) * n / 2;
    }
    class P implements Comparable<P> {
        long x;
        long person;
        P(long x, long person) {
            this.x = x;
            this.person = person;
        }
        @Override
        public int compareTo(P o) {
            return Long.compare(x, o.x);
        }
    }
}
