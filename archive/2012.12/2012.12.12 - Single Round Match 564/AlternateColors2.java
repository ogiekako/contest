package tmp;

public class AlternateColors2 {
    public long countWays(int n, int k) {
        long res = 0;
        for (int r = 0; r <= k; r++) {
            debug(r);
            if (r * 3 == k + 2) {
                res += C(n - k + 2, 2);
                debug("A", res);
            }
            int to = Math.min(r - 2, k - r);
            int from = Math.max(0, k - 2 * r + 2);
            res += Math.max(0, to - from + 1);
            debug("B", res);
            int b = k - (2 * r - 1);
            if (0 <= b && b <= r - 2) {
                res += C(n - k + 1, 1) * 2;
                debug("C", res);
            }
        }
        return res;
    }

    static void debug(Object... os) {
        //        System.err.println(Arrays.deepToString(os));
    }

    private long C(int n, int k) {
        long res = 1;
        for (int i = 0; i < k; i++) {
            res *= n - i;
            res /= i + 1;
        }
        return res;
    }
}
