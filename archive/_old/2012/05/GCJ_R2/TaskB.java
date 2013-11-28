package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long H = in.nextInt();
        long W = in.nextInt();
        E[] es = new E[N];
        for (int i = 0; i < N; i++) es[i] = new E(i, in.nextLong());
        Arrays.sort(es);

        long[] X = new long[N];
        long[] Y = new long[N];
        TreeSet<Long> xset = new TreeSet<Long>();
        TreeSet<Long> yset = new TreeSet<Long>();
        for (int i = 0; i < N; i++) {
            xset.add(-es[i].R);
            yset.add(-es[i].R);
            long[] xcand = tols(xset.toArray(new Long[0]));
            long[] ycand = tols(yset.toArray(new Long[0]));
            xset.remove(-es[i].R);
            yset.remove(-es[i].R);
            boolean ok2 = false;
            loop:
            for (long xx : xcand)
                for (long yy : ycand) {
                    long xi = xx + es[i].R;
                    long yi = yy + es[i].R;
                    if (xi > H || yi > W) continue;
                    boolean ok = true;
                    for (int j = 0; j < i; j++) {
                        long xj = X[es[j].id];
                        long yj = Y[es[j].id];
                        if (Math.abs(xj - xi) < es[i].R + es[j].R && Math.abs(yj - yi) < es[i].R + es[j].R) {
                            ok = false;
                        }
                    }
                    if (ok) {
                        ok2 = true;
                        X[es[i].id] = xi;
                        Y[es[i].id] = yi;
                        if (xi + es[i].R <= H)
                            xset.add(xi + es[i].R);
                        if (yi + es[i].R <= W)
                            yset.add(yi + es[i].R);
                        break loop;
                    }
                }
            if (!ok2) {
                throw new AssertionError();
            }
        }
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < N; i++) {
            b.append(" "); b.append(X[i]); b.append(" "); b.append(Y[i]);
        }
        out.printf("Case #%d:%s\n", testNumber, b.toString());
    }
    class E implements Comparable<E> {
        long R;
        int id;

        E(int id, long r) {
            this.id = id;
            R = r;
        }

        public int compareTo(E o) {
            return -Long.signum(R - o.R);
        }
    }

    private long[] tols(Long[] Ls) {
        long[] ls = new long[Ls.length];
        for (int i = 0; i < ls.length; i++) ls[i] = Ls[i];
        return ls;
    }
}
