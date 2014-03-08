package on2013_12.on2013_12_08_Recruite_2013.TaskH;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskH {
    String[] A, B;
    boolean[][][] dp;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            int N = in.nextInt();
            A = new String[N];
            for (int i = 0; i < N; i++) {
                A[i] = in.nextLine();
            }
            int M = in.nextInt();
            B = new String[M];
            for (int i = 0; i < M; i++) {
                B[i] = in.nextLine();
            }
            debug(A);
            debug(B);
            dp = new boolean[N + 1][M + 1][1024];
            f(0, 0, 0);
            int res = 0;
            for (int i = 0; i < 1024; i++) if (dp[N][M][i]) res++;
            out.println(res);
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    private void f(int i, int j, int val) {
        if (dp[i][j][val]) return;
        dp[i][j][val] = true;
        if (i < A.length) {
            g(A[i], i, val);
            f(ni, j, nval);
        }
        if (j < B.length) {
            g(B[j], j, val);
            f(i, ni, nval);
        }
    }
    int nval;
    int ni;
    private void g(String s, final int i, int val) {
        String[] ss = s.split(" ");
        ni = i + 1;
        nval = val;
        if (s.startsWith("MOV")) {
            if (ss.length != 3) throw new AssertionError();
            int r1 = ss[1].charAt(1) - '0';
            int r2 = ss[2].charAt(1) - '0';
            nval = set(nval, r1, get(nval, r2));
        } else if (s.startsWith("A")) {
            if (ss.length != 3) throw new AssertionError();
            int r1 = ss[1].charAt(1) - '0';
            int r2 = ss[2].charAt(1) - '0';
            nval = add(nval, r1, get(nval, r2));
        } else if (s.startsWith("INC")) {
            if (ss.length != 2) throw new AssertionError();
            int r1 = ss[1].charAt(1) - '0';
            nval = add(nval, r1, 1);
        } else if (s.startsWith("TEST")) {
            if (ss.length != 3) throw new AssertionError();
            int r1 = ss[1].charAt(1) - '0';
            int r2 = ss[2].charAt(1) - '0';
            if (r1 != r2) {
                nval = set(nval, r2, get(nval, r1));
                nval = set(nval, r1, 1);
            }
        } else if (s.startsWith("IF")) {
            if (ss.length != 4) throw new AssertionError();
            int r1 = ss[1].charAt(1) - '0';
            int r2 = ss[2].charAt(1) - '0';
            int off = Integer.valueOf(ss[3]);
            if (ss[3].charAt(0) == '-' && off >= 0) throw new AssertionError();
            if (get(val, r1) < get(val, r2)) {
                ni = i + off;
            }
        } else throw new AssertionError(s);
    }
    private int add(int val, int r, int reg) {
        if (r < 0 || r > 4) throw new AssertionError();
        if (reg >= 4 && reg < 0) throw new AssertionError();
        int cur = get(val, r);
        cur += reg;
        cur &= 3;
        return set(val, r, cur);
    }

    private int set(int val, int r, int reg) {
        if (r < 0 || r > 4) throw new AssertionError();
        if (reg >= 4 && reg < 0) throw new AssertionError();
        val &= (~(3 << 2 * r));
        val += reg << 2 * r;
        return val;
    }
    private int get(int val, int r) {
        if (r < 0 || r > 4) throw new AssertionError();
        return (val >> 2 * r) & 3;
    }


}
