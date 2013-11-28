package utpc;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.counter.Counter;
import net.ogiekako.algorithm.utils.counter.HashCounter;
import net.ogiekako.algorithm.utils.Permutation;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MostProbableString {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        out.println(solveStupid(n, k));
    }

    String solve(int n, int k) {
        if (k == 1) return "a";
        if (k == 2) return "ab";
        k--;
        BigInteger max = BigInteger.ZERO;
        char res = 'a';
        for (int m = 0; m < n; m++) {
            BigInteger[][][] dp = new BigInteger[2][m + 1][k + 1];
            for (int j = 0; j < m + 1; j++) Arrays.fill(dp[0][j], BigInteger.ZERO);
            dp[0][0][0] = BigInteger.ONE;
            int cur = 0, nxt = 1;
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < m + 1; j++) Arrays.fill(dp[nxt][j], BigInteger.ZERO);
                for (int j = 0; j < m + 1; j++)
                    for (int curK = 0; curK < k + 1; curK++)
                        if (dp[cur][j][curK].compareTo(BigInteger.ZERO) > 0) {
                            dp[nxt][j][curK] = dp[nxt][j][curK].add(dp[cur][j][curK]);
                            if (j + 1 < m + 1 && curK + i < k + 1)
                                dp[nxt][j + 1][curK + i] = dp[nxt][j + 1][curK + i].add(dp[cur][j][curK]);
                        }
                int tmp = cur; cur = nxt; nxt = tmp;
            }
            BigInteger count = dp[cur][m][k];
            for (int i = 0; i < m; i++) count = count.multiply(BigInteger.valueOf(i + 1));
            for (int i = m; i < n; i++) count = count.multiply(BigInteger.valueOf(n - i));
            if (max.compareTo(count) < 0) {
                max = count;
                res = (char) (m + 'a');
            }
        }
        return "" + res;
    }

    String solveStupid(int n, int k) {
        k--;
        char[] cs = new char[n];
        for (int i = 0; i < n; i++) cs[i] = (char) ('a' + i);
        Counter<String> counter = new HashCounter<String>();
        long max = 0;
        String res = null;
        do {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i <= n; i++) for (int j = 0; j < i; j++) list.add(String.valueOf(cs, j, i - j));
            Collections.sort(list);
            String s = list.get(k);
            counter.add(s, 1);
            long val = counter.get(s);
            if (max < val || max == val && s.compareTo(res) < 0) {
                max = val;
                res = s;
            }
        } while (Permutation.nextPermutation(cs));
        return res;
    }

    public static void main(String[] args) {
        for (int n = 1; n < 26 + 1; n++) {
            int m = n * (n + 1) / 2;
            for (int k = 1; k <= m; k++) {
//                String exp = new MostProbableString().solveStupid(n, k);
                String res = new MostProbableString().solve(n, k);
                System.out.printf("%d %d -> %s\n", n, k, res);
//                Assert.assertEquals(exp, res);
            }
            System.out.println();
        }
    }
}
