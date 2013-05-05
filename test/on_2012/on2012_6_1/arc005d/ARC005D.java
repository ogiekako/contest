package on_2012.on2012_6_1.arc005d;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class ARC005D {
    long[] ten = new long[20];
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (int i = 0; i < 20; i++) ten[i] = i == 0 ? 1 : ten[i - 1] * 10;
        String s = in.next();
        long N = in.nextLong();
        for (char c : s.toCharArray()) can[c - '0'] = true;
        for (int i = 0; i < 20; i++) {
            n[i] = (int) (N % 10); N /= 10;
        }

        int best = Integer.MAX_VALUE;
        long[] res2 = null;
        for (int i = 1; i < 10; i++) {
            solve(i);
            if (as != null) {
                int score = res + (i == 1 ? 0 : i);
                if (best > score) {
                    best = score;
                    res2 = as.clone();
                }
            }
        }
        if (res2.length == 1) {
            out.println(res2[0]);
        } else {
            boolean first = true;
            for (long l : res2) {
                if (!first) out.print("+");
                first = false;
                out.print(l);
            }
            out.println("=");
        }
    }

    private void solve(int c) {
        int[] need = new int[100];
        Arrays.fill(need, Integer.MAX_VALUE);
        need[0] = 0;
        for (int i = 0; i < c; i++) {
            for (int j = 99; j >= 0; j--)
                if (need[j] < Integer.MAX_VALUE) {
                    for (int k = 1; k < 10; k++)
                        if (can[k]) {
                            int nj = j + k;
                            need[nj] = Math.min(need[nj], need[j] + 1);
                        }
                }
        }
        int[][] need2 = new int[100][];
        need2[0] = new int[]{};
        for (int i = 1; i < 100; i++)
            if (need[i] < Integer.MAX_VALUE) {
                for (int k = 1; ; k++)
                    if (can[k] && need[i - k] + 1 == need[i]) {
                        need2[i] = new int[need[i]];
                        System.arraycopy(need2[i - k], 0, need2[i], 0, need[i - k]);
                        need2[i][need[i - k]] = k; break;
                    }
            }
//        System.err.println("B" +Arrays.deepToString(need2));
        int[][][] dp = new int[20][10][c + 1];
        for (int[][] ii : dp) for (int[] i : ii) Arrays.fill(i, Integer.MAX_VALUE);
        long[][][][] dp2 = new long[20][10][10][];
        dp[0][0][c] = 0;
        dp2[0][0][c] = new long[c];
        for (int i = 0; i < 19; i++)
            for (int j = 0; j < 10; j++)
                for (int k = 0; k <= c; k++)
                    if (dp[i][j][k] < Integer.MAX_VALUE) {
//            System.err.println("E "+ c+" "+i+" "+j+" "+k);
                        int lst = (n[i] - j + 10) % 10;
                        for (int snd = 0; snd < 10; snd++) {
                            int val = snd * 10 + lst;
                            if (need[val] > k) {
//                    System.err.println("D " + c+" "+i+" "+j+" "+k+" "+val+" "+need[val]+" "+Arrays.toString(need2[val]));
                                continue;
                            }
                            int nj = snd + (j + lst) / 10;
//                System.err.println("A " + c+" "+i+" "+j+" "+k+" "+val+" "+need[val]+" "+Arrays.toString(need2[val]));
                            for (int nk = need[val]; nk <= k; nk++) {
                                int nScore = dp[i][j][k] + nk;
//                    System.err.println("C " + dp[i+1][nj][nk]+" "+nScore);
                                if (dp[i + 1][nj][nk] > nScore) {
                                    dp[i + 1][nj][nk] = nScore;
                                    dp2[i + 1][nj][nk] = dp2[i][j][k].clone();
                                    for (int l = 0; l < need2[val].length; l++) {
                                        dp2[i + 1][nj][nk][l] += ten[i] * need2[val][l];
                                    }
                                }
                            }
                        }
                    }
        res = Integer.MAX_VALUE;
        as = null;
        for (int i = 0; i <= c; i++)
            if (dp[19][0][i] < Integer.MAX_VALUE) {
                if (dp[19][0][i] < res) {
                    res = dp[19][0][i];
                    as = dp2[19][0][i];
                }
            }
    }

    boolean[] can = new boolean[10];
    int[] n = new int[20];
    int res;
    long[] as;
}
