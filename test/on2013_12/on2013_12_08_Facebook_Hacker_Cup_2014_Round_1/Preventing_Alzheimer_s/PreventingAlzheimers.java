package on2013_12.on2013_12_08_Facebook_Hacker_Cup_2014_Round_1.Preventing_Alzheimer_s;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class PreventingAlzheimers {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        System.err.println(testNumber);
        int N = in.nextInt(), K = in.nextInt();
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = in.nextInt();
        }
        int res = f(A, K);
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }
    private int f(int[] A, int K) {
        int N = A.length;
        if (K > 1) {
            int res = 0;
            for (int i = 0; i < A.length; i++) {
                if (A[i] % K > 0) {
                    res += K - A[i] % K;
                    A[i] += K - A[i] % K;
                }
                A[i] /= K;
            }
            return res + f(A, 1) * K;
        }
        // K = 1
        int res = Integer.MAX_VALUE;
        Arrays.sort(A);
        if (A[0] == 0 && A[N - 1] <= K) {
            int val = 0;
            for (int i = 1; i < N; i++) val += K - A[i];
            res = Math.min(res, val);
        }
        int MX = 150;
        int[] small = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        int[] large = {53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149};
        int[] S = new int[MX];
        for (int i = 1; i < MX; i++) {
            int v = i;
            for (int p = 2; p <= v; p++) {
                if (v % p == 0) {
                    if (p >= 50) {
                        S[i] = -1;
                        break;
                    }
                    S[i] |= 1 << Arrays.binarySearch(small, p);
                    while (v % p == 0) {
                        v /= p;
                    }
                }
            }
        }
        int[][] dp = new int[1 << small.length][large.length + 1];
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < N; i++) {
            int[][] nDp = new int[dp.length][dp[0].length];
            for (int j = 0; j < dp.length; j++) Arrays.fill(nDp[j], Integer.MAX_VALUE);
            for (int s = 0; s < dp.length; s++)
                for (int k = 0; k < dp[0].length; k++)
                    if (dp[s][k] < Integer.MAX_VALUE) {
                        for (int a = Math.max(A[i], 1); a < MX; a++) {
                            if (S[a] >= 0) {
                                if ((s & S[a]) > 0) continue;
                                nDp[s | S[a]][k] = Math.min(nDp[s | S[a]][k], dp[s][k] + a - A[i]);
                            }
                        }
                        nDp[s][k + 1] = Math.min(nDp[s][k + 1], dp[s][k] + large[k] - A[i]);
                    }
            dp = nDp;
        }
        for (int[] i : dp) for (int j : i) res = Math.min(res, j);
        return res;
    }
}
