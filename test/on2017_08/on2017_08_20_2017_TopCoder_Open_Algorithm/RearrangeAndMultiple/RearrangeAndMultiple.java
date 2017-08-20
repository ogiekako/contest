package on2017_08.on2017_08_20_2017_TopCoder_Open_Algorithm.RearrangeAndMultiple;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class RearrangeAndMultiple {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    long B;
    long L;
    long[] res;

    public String construct(int _B, int _L) {
        B = _B;
        L = _L;
        if (B == 2) return "";
        res = new long[]{1, 0, B - 2, B - 1};
        // (p,q) = 1, p > q
        // (k-1)(pB+q) = (qB+p)   # 3 <= k <= B
        // k(pB+q) = (p+q)(B+1)
        // q / p = (kB - B - 1) / (B + 1 - k)
        for (int k = 3; k <= B; k++) {
            long q = k * B - B - 1;
            long p = B + 1 - k;
            long g = MathUtils.gcd(q, p);
            q /= g;
            p /= g;
            check(p * B + q, k - 1);
        }
        // Needed when B is e.g. 98176.
        if (L >= 2 && res.length == 2) return asStr(res);
        if (L < 3) return "";

        debug("1");
        // (1) pqr -> qpr
        // (k+1)(pBB+qB+r) = qBB+pB+r  # 1 <= k < B-1
        // k(pBB+qB+r) = (q-p)(BB-B)
        // g := (k, BB-B)
        // d := k / g
        // x := q - p
        // pBB+qB+r = (x / d)(BB-B / g)
        for (int k = 1; k < B - 1; k++) {
            long g = MathUtils.gcd(k, B * B - B);
            long d = k / g;
            for (long x = d; x < B; x += d) {
                check(x * (B * B - B) / k, k + 1);
                // For 65052, 4
                if (L >= 3 && res.length == 3) return asStr(res);
            }
        }
        debug("2");
        // (2) pqr -> rqp
        // (k+1)(pBB+qB+r) = rBB+qB+p  # 1 <= k < B-1
        // k(pBB+qB+r) = (r-p)(BB-1)
        // g := (k, BB-1)
        // d := k / g
        // x := r - p
        // pBB+qB+r = (x / d)(BB-1 / g)
        for (int k = 1; k < B - 1; k++) {
            long g = MathUtils.gcd(k, B * B - 1);
            long d = k / g;
            for (long x = d; x < B; x += d) {
                check(x * (B * B - 1) / k, k + 1);
            }
        }
        debug("3");
        // (3) pqr -> qrp
        // k(pBB+qB+r) = qBB+rB+p  # 2 <= k < B
        // x:=qB+r
        // k(pBB+x) = xB+p
        // x/p = (kBB-1)/(B-k)
        for (int k = 2; k < B; k++) {
            long x = k * B * B - 1;
            long p = B - k;
            long g = MathUtils.gcd(x, p);
            x /= g;
            p /= g;
            for (int d = 1; x * d < B * B; d++) {
                check(p * d * B * B + x * d, k);
            }
        }
        debug("4");
        // (4) pqr -> rpq
        // k(pBB+qB+r) = rBB+pB+q  # 2 <= k < B
        // x:=pB+q
        // k(xB+r) = rBB+x
        // x/r = (BB-k)/(kB-1)
        for (int k = 2; k < B; k++) {
            long x = B * B - k;
            long r = k * B - 1;
            long g = MathUtils.gcd(x, r);
            x /= g;
            r /= g;
            for (int d = 1; r * d < B; d++) {
                check(x * d * B + r * d, k);
            }
        }
        if (res.length > L) return "";
        return asStr(res);
    }

    private void check(long v, int t) {
        if (t < 2 || t >= B) throw new AssertionError();
        if (v == 0) throw new AssertionError();
        long[] a = asArr(v);
        long[] aSort = a.clone();
        long[] b = asArr(v * t);
        Arrays.sort(aSort);
        Arrays.sort(b);
        if (Arrays.equals(aSort, b)) {
            if (res.length > a.length) {
                res = a;
            }
        }
    }

    private long[] asArr(long v) {
        long tmp = v;
        int len = 0;
        while (tmp > 0) {
            len++;
            tmp /= B;
        }
        long[] res = new long[len];
        for (int i = len - 1; i >= 0; i--) {
            res[i] = v % B;
            v /= B;
        }
        return res;
    }

    private String asStr(long[] a) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) b.append(" ");
            b.append(a[i]);
        }
        for (int i = a.length; i < L; i++) {
            if (i > 0) b.append(" ");
            b.append(0);
        }
        return b.toString();
    }
}
