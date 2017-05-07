package on2017_05.on2017_05_07_Single_Round_Match_714.NAddOdd;



public class NAddOdd {

    public long solve(long L, long R, int K) {

        L = Math.max(L, K + 1);
        if (L > R) return 0;

        if (R == L) {
            return g(R, K);
        }

        long res = R - L + 1;

        long a = (L + 1) / 2;
        long b = R / 2;

        long A = L / 2;
        long B = (R - 1) / 2;

        long c = A + (1 + K) / 2;
        long d = B + (1 + K) / 2;

        res += (B - A) + 1;

        res += solve(c, b, K) * 2;
        for (long i = a; i < Math.min(b + 1, c); i++) res += g(i, K);
        for (long i = Math.max(c, b + 1); i <= d; i++) res += g(i, K);
        return res;
    }

    private long g(long x, int K) {
        if (x <= K) return 0;
        x = (x & 1) == 1 ? x + K : x / 2;
        return 1 + g(x, K);
    }
}
