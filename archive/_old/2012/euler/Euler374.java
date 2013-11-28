package tmp;

import net.ogiekako.algorithm.utils.numbers.IntegerUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler374 {
    int MOD = 982451653;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong() + 1;
        long from = 2;
        long mul = 2;
        long div = inv(2);
        long res = 1;
        long ext = 3;
        long m = 0;
        for (m = 1; from < N; m++) {
            mul = mul * (m + 2) % MOD;
            long to = from + m + 1;
            if (to >= N) {
                to = N;
                div = 0;
                for (long i = 0; i < to - from; i++) {
                    div += inv(m + 2 - i);
                    if (div >= MOD) div -= MOD;
                }
                res += mul * div % MOD * m % MOD;
                if (res >= MOD) res -= MOD;
            } else {
                div += inv(m + 2);
                if (div >= MOD) div -= MOD;
                res += mul * div % MOD * m % MOD;
                if (res >= MOD) res -= MOD;
            }
            from = to;
            if (from < N) {
                ext = ext * (m + 3) % MOD;
                long val = ext * inv(m + 2) % MOD;
                res += val * m % MOD;
                if (res >= MOD) res -= MOD;
                from++;
            }
        }
        System.err.println(m);
        out.println(res);
    }
    long inv(long i) {
        return IntegerUtils.modPow(i, MOD - 2, MOD);
    }
}
