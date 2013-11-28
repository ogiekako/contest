package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class Facebook13Round1A {
    static final int MOD = 1000000007;
    static final int MAX = 10010;
    static final long[][] C = MathUtils.combinationMod(MAX, MOD);

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int N = in.nextInt(), K = in.nextInt();
        long[] a = new long[N];
        for (int i = 0; i < N; i++) a[i] = in.nextLong();
        Arrays.sort(a);
        long res = 0;
        for (int i = K - 1; i < N; i++) res = (res + C[i][K - 1] * (a[i] % MOD)) % MOD;
        out.println(res);
    }

}
