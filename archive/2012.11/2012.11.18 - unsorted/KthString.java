package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Permutation;

import java.io.PrintWriter;
import java.util.*;

// 19:12 -
public class KthString {
    int MOD = (int) (1e9 + 7);
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), K = in.nextInt();
        String s = in.next();
        int res = solve(n, K, s);
        out.println(res);
    }
    private int solve(int n, int K, String s) {
        K -= s.length();
        long res = 0;
        char head = s.charAt(0);
        int numBefore = 0;
        for (char c = 'a'; c < head; c++) if (s.indexOf(c) < 0) numBefore++;
        int numOther = n - s.length();
        int numAfter = numOther - numBefore;
        long[] fact = fact(n + 1);
        for (int start = 0; start + s.length() <= n; start++) {
            int L = K;
            for (int i = 0; i < s.length(); i++) if (s.charAt(i) < head) L -= n - (start + i);
            if (L < 0) continue;
            int[] values = new int[n - s.length()];
            for (int j = 0, p = 0; j < n; j++) if (j < start || j >= start + s.length()) values[p++] = n - j;
            long[][][] dp = new long[values.length + 1][L + 1][numBefore + 1];
            dp[0][0][0] = 1;
            for (int i = 0; i < values.length; i++)
                for (int j = 0; j < L + 1; j++)
                    for (int k = 0; k < numBefore + 1; k++)
                        if (dp[i][j][k] > 0) {
                            int ni = i + 1;
                            dp[ni][j][k] = (dp[ni][j][k] + dp[i][j][k]) % MOD;
                            int nj = j + values[i];
                            int nk = k + 1;
                            if (nj < L + 1 && nk < numBefore + 1) dp[ni][nj][nk] = (dp[ni][nj][nk] + dp[i][j][k]) % MOD;
                        }
            res = (res + dp[values.length][L][numBefore] * fact[numBefore] % MOD * fact[numAfter]) % MOD;
        }
        return (int) res;
    }

    private long[] fact(int n) {
        long[] fact = new long[n];
        for (int i = 0; i < n; i++) fact[i] = (i == 0 ? 1 : fact[i - 1] * i) % MOD;
        return fact;
    }

    public static void main(String[] args) {
        KthString instance = new KthString();
        Random rnd = new Random(140912840L);
        for (int i = 0; i < 500; i++) {
            int n = 8;
            int k = rnd.nextInt(n * (n + 1) / 2) + 1;
            int len = rnd.nextInt(n) + 1;
            char[] cs = new char[n];
            for (int j = 0; j < n; j++) cs[j] = (char) ('a' + j);
            ArrayUtils.shuffle(cs);
            String s = String.valueOf(cs, 0, len);
            int res = instance.solve(n, k, s);
            int exp = instance.solveStupid(n, k, s);
            System.err.println(res);
            if (res != exp) {
                debug(n, k, s);
                debug(res);
                debug(exp);
                throw new AssertionError();
            }
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int solveStupid(int n, int k, String s) {
        k--;
        char[] cs = new char[n];
        for (int i = 0; i < n; i++) cs[i] = (char) ('a' + i);
        int res = 0;
        do {
            String t = String.valueOf(cs);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i <= n; i++) for (int j = 0; j < i; j++) list.add(t.substring(j, i));
            Collections.sort(list);
            int l = Collections.binarySearch(list, s);
            if (l == k) res++;
        } while (Permutation.nextPermutation(cs));
        return res;
    }
}
