package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.Random;

public class Facebook13Round1C {
    private static final int MAX = 40010;
    private static final boolean[][] A = new boolean[MAX][MAX];
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int W = in.nextInt(), H = in.nextInt(), P = in.nextInt(), Q = in.nextInt(), N = in.nextInt(), X = in.nextInt(), Y = in.nextInt(), a = in.nextInt(), b = in.nextInt(), c = in.nextInt(), d = in.nextInt();
        int[] x = new int[N], y = new int[N];
        x[0] = X;
        y[0] = Y;
        for (int i = 1; i < N; i++) {
            x[i] = (int) (((long) x[i - 1] * a + (long) y[i - 1] * b + 1) % W);
            y[i] = (int) (((long) x[i - 1] * c + (long) y[i - 1] * d + 1) % H);
        }
        long res = solve(x, y, H, W, P, Q);
        out.println(res);
    }

    private long solve(int[] x, int[] y, int H, int W, int P, int Q) {
        for (int i = 0; i < W; i++) for (int j = 0; j < H; j++) A[i][j] = true;
        for (int i = 0; i < x.length; i++) A[x[i]][y[i]] = false;
        int[] S0 = new int[H + 1], S1 = new int[H + 1];
        long res = 0;
        for (int i = 1; i <= W; i++) {
            int sum = 0;
            for (int j = 1; j <= H; j++) {
                if (A[i - 1][j - 1]) sum++;
                S1[j] += sum;// Sij
            }
            if (i - P >= 0) {
                sum = 0;
                for (int j = 1; j <= H; j++) {
                    if (i - P - 1 >= 0 && A[i - P - 1][j - 1]) sum++;
                    S0[j] += sum;
                }
                for (int j = Q; j <= H; j++) {
                    int num = S1[j] - S1[j - Q] - S0[j] + S0[j - Q];
                    if (num == P * Q) res++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        testSpeed();
    }

    private static void testSpeed() {
        Facebook13Round1C instance = new Facebook13Round1C();
        int T = 20, W = 40000, H = 40000, P = 20000, Q = 20000;
        int N = 10;
        Random rnd = new Random(101928L);
        for (int i = 0; i < T; i++) {
            int[] x = new int[N], y = new int[N];
            for (int j = 0; j < N; j++) {
                x[j] = rnd.nextInt(W);
                y[j] = rnd.nextInt(H);
            }
            long res = instance.solve(x, y, H, W, P, Q);
            System.out.println(res);
        }
    }

    public static void testValidity() {
        Facebook13Round1C instance = new Facebook13Round1C();
        Random rnd = new Random(101928L);
        for (int i = 0; i < 100000; i++) {
            int W = rnd.nextInt(30) + 1, H = rnd.nextInt(30) + 1;
            int N = rnd.nextInt(10);
            int[] x = new int[N], y = new int[N];
            for (int j = 0; j < x.length; j++) {
                x[j] = rnd.nextInt(W);
                y[j] = rnd.nextInt(H);
            }
            int P = rnd.nextInt(W) + 1, Q = rnd.nextInt(H) + 1;
            long res = instance.solve(x, y, H, W, P, Q);
            long exp = instance.solveStupid(x, y, H, W, P, Q);
            if (res != exp) {
                debug("P, Q", P, Q);
                int[][] A = new int[W][H];
                for (int j = 0; j < x.length; j++) A[x[j]][y[j]] = 1;
                for (int[] a : A) debug(a);
                debug(res, exp);
                throw new AssertionError();
            }
            debug(i, res);
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private long solveStupid(int[] x, int[] y, int H, int W, int P, int Q) {
        boolean[][] A = new boolean[W][H];
        for (int i = 0; i < x.length; i++) A[x[i]][y[i]] = true;
        int res = 0;
        for (int i = P; i <= W; i++)
            for (int j = Q; j <= H; j++) {
                boolean ok = true;
                for (int k = i - P; k < i; k++) for (int l = j - Q; l < j; l++) if (A[k][l]) ok = false;
                if (ok) res++;
            }
        return res;
    }
}
