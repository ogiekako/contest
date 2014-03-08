package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class CoinsGame {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int N = in.nextInt(), K = in.nextInt(), C = in.nextInt();
        int res = Integer.MAX_VALUE;
        for (int n = 1; n <= N; n++) {
            res = Math.min(res, solve(n, K, C) + (N - n));
        }
        out.println(res);
    }
    private int solve(int N, int K, int C) {
        int A = K / N * N;
        int B = K % N;
        int res;
        if (C <= A) res = C;
        else res = N - B + C;
        return res;
    }
}
