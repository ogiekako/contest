package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import org.junit.Assert;

import java.io.PrintWriter;
import java.util.Random;

public class PolynomialRevert {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), Q = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        int[] y = new int[Q];
        for (int i = 0; i < Q; i++) y[i] = in.nextInt();
        int[] r = solve(a, y, 30);
        for (int i = 0; i < Q; i++) out.println(r[i]);
    }

    private int[] solve(int[] _a, int[] y, int L) {
        int n = Math.max(L, _a.length);
        long[] a = new long[n];
        for (int i = 0; i < _a.length; i++) a[i] = _a[i];

        int MOD = 1 << L;
        long[][] C = MathUtils.generateCombinationMod(n, L, MOD);
        long[] oddCoefficient = new long[L];// coefficients for x' where x = 2x' + 1
        for (int j = 0; j < L; j++) {
            for (int k = 0; k < n; k++) {
                oddCoefficient[j] += C[k][j] * a[k];
            }
            oddCoefficient[j] <<= j;
        }
        long[] evenCoefficient = new long[L];// coefficients for x' where x = 2x'
        for (int i = 0; i < L; i++) evenCoefficient[i] = (1L << i) * a[i] % MOD;
        int[] countEven = count(evenCoefficient, y, L - 1, L);
        int[] countOdd = count(oddCoefficient, y, L - 1, L);
        int[] res = new int[countEven.length];
        for (int i = 0; i < res.length; i++) res[i] = countEven[i] + countOdd[i];
        return res;
    }

    private int[] count(long[] as, int[] ys, int K, int L) {// range : [0, 2^K), mod 2^L
        int MOD = 1 << L;
        int K1 = (K + 1) / 2;
        int K0 = K - K1;
        // x = 2^K1 x1 + x0 (x0 < 2^K1, x1 < 2^K0)
        // x^k = sum{j=0 to k}kCj(2^K1 x1)^j(x0)^(k-j) = k2^K1 x1 x0^(k-1) + x0^k
        // sum{k=0 to L-1} ak x^k = sum ak (k 2^K1 x1 x0^(k-1) + x0^k) = sum{k=0 to L-1} ak x0^k + 2^K1 x1 sum{k=0 to L-2} a_{k+1} (k+1) x0^k
        // = 2^K1 x1 f1(x0) + f0(x0).
        int n0 = 1 << K0;
        Node root = new Node();
        for (int x0 = 0; x0 < n0; x0++) {
            long x2k = 1;
            long f0 = 0;
            long f1 = 0;
            for (int k = 0; k < L; k++) {
                f0 = (f0 + as[k] * x2k) % MOD;
                if (k + 1 < L) f1 = (f1 + as[k + 1] * (k + 1) % MOD * x2k) % MOD;
                x2k = x2k * x0 % MOD;
            }
            root.add(f0, Math.min(K1, Long.numberOfTrailingZeros(f1)), L);
        }
        root.sum();
        int[] res = new int[ys.length];
        for (int i = 0; i < ys.length; i++) {
            res[i] = root.calc(ys[i]);
        }
        return res;
    }

    class Node {
        Node[] cs = new Node[2];
        int value;

        void add(long f0, long zero, int L, int K1) {
            add(f0, zero, 0, L, K1);
        }

        private void add(long f1, long zero, int p, int L, int K1) {
            if (p == K1 + zero) value += 1 << zero;
            if (p == L) return;
            int v = (int) (f1 >> L & 1);
            if (cs[v] == null) cs[v] = new Node();
            cs[v].add(f1, zero, p + 1, L, K1);
        }

        public int sum() {
            for (Node c : cs) if (c != null) value += c.sum();
            return value;
        }

        public int calc(int y) {
            return 0;
        }

        public void add(long f0, int min, int l) {
            //To change body of created methods use File | Settings | File Templates.
        }
    }

    public static void main(String[] args) {
        PolynomialRevert instance = new PolynomialRevert();
        Random rnd = new Random(4102894L);
        int n = 10;
        int Q = 50;
        int L = 10;
        int numIteration = 100;
        for (int iteration = 0; iteration < numIteration; iteration++) {
            int[] as = new int[n];
            for (int i = 0; i < n; i++) as[i] = rnd.nextInt(1 << L);
//            int[] ys = new int[Q];
//            for (int i = 0; i < Q; i++) ys[i] = rnd.nextInt(1 << L);
            int[] ys = new int[1 << L];
            for (int i = 0; i < 1 << L; i++) ys[i] = i;
            int[] exp = solveStupid(as, ys, L);
            int sum = 0;
            for (int e : exp) sum += e;
            Assert.assertEquals(1 << L, sum);
//            System.err.println(Arrays.toString(exp));
            int[] res = instance.solve(as, ys, L);
            Assert.assertArrayEquals(exp, res);
        }
    }

    private static int[] solveStupid(int[] as, int[] ys, int L) {
        int[] res = new int[ys.length];
        int MOD = 1 << L;
        for (int i = 0; i < ys.length; i++) {
            for (int x = 0; x < 1 << L; x++) {
                long xk = 1;
                long Px = 0;
                for (int a : as) {
                    Px = (Px + a * xk) % MOD;
                    xk = (xk * x) % MOD;
                }
                if (Px == ys[i]) res[i]++;
            }
        }
        return res;
    }
}
