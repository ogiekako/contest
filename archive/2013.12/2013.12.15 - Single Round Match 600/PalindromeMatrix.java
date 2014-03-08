package src;

import java.util.Arrays;
public class PalindromeMatrix {
    int N, M;
    String[] A;
    int rowCount;
    int[][][][][][] f;
    public int minChange(String[] A, int rowCount, int columnCount) {
        N = A.length; M = A[0].length();
        this.rowCount = rowCount;
        this.A = A;
        f = new int[2][2][2][2][4][4];
        for (int a = 0; a < 2; a++)
            for (int b = 0; b < 2; b++)
                for (int c = 0; c < 2; c++)
                    for (int d = 0; d < 2; d++)
                        for (int e = 0; e < 4; e++) for (int g = 0; g < 4; g++) f[a][b][c][d][e][g] = Integer.MAX_VALUE;

        for (int a = 0; a < 2; a++)
            for (int b = 0; b < 2; b++)
                for (int c = 0; c < 2; c++)
                    for (int d = 0; d < 2; d++)
                        for (int na = 0; na < 2; na++)
                            for (int nb = 0; nb < 2; nb++)
                                for (int nc = 0; nc < 2; nc++)
                                    for (int nd = 0; nd < 2; nd++) {
                                        int val = 0;
                                        if (a != na) val++;
                                        if (b != nb) val++;
                                        if (c != nc) val++;
                                        if (d != nd) val++;
                                        int col = 0;
                                        int row = 0;
                                        if (na == nc) col |= 1;
                                        if (nb == nd) col |= 2;
                                        if (na == nb) row |= 1;
                                        if (nc == nd) row |= 2;
                                        for (int co = 0; co < 4; co++)
                                            if ((co & ~col) == 0) {
                                                for (int ro = 0; ro < 4; ro++)
                                                    if ((ro & ~row) == 0) {
                                                        f[a][b][c][d][co][ro] = Math.min(f[a][b][c][d][co][ro], val);
                                                    }
                                            }
                                    }


        int res = Integer.MAX_VALUE;
        for (int col = 0; col < 1 << M; col++)
            if ((Integer.bitCount(col)) >= columnCount) {
                int val = solve(col);
//                debug(val, Integer.toBinaryString(col));
                res = Math.min(res, val);
            }
        return res;
    }
//    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
//    }
    private int solve(int col) {
//        debug("col", Integer.toBinaryString(col));
        long[] dp = new long[N + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i * 2 < N; i++) {
//            debug("dp", dp);
            long[] nDp = new long[N + 1];
            Arrays.fill(nDp, Integer.MAX_VALUE);
            int[] cnt = N % 2 == 1 && i * 2 + 1 == N ? one(A[i]) : two(A[i], A[N - 1 - i], col);
            for (int j = 0; j <= N; j++)
                if (dp[j] < Integer.MAX_VALUE)
                    for (int k = 0; k < cnt.length; k++) nDp[j + k] = Math.min(nDp[j + k], dp[j] + cnt[k]);
            dp = nDp;
        }
//        debug("dp",dp);
        long res = Integer.MAX_VALUE;
        for (int i = rowCount; i <= N; i++) res = Math.min(res, dp[i]);
        return (int) res;
    }
    private int[] two(String a, String b, int col) {
        int[] res = new int[3];
        Arrays.fill(res, Integer.MAX_VALUE);
        for (int msk = 0; msk < 4; msk++) {
            int val = 0;
            for (int i = 0; i * 2 < a.length(); i++) {
                int j = a.length() - 1 - i;
                if (i == a.length() - 1 - i) {
                    val += col << 31 - i < 0 && a.charAt(i) != b.charAt(i) ? 1 : 0;
                } else {
                    int cur = ((col >> i & 1) << 1) | (col >> j & 1);
                    val += f[a.charAt(i) - '0'][a.charAt(j) - '0'][b.charAt(i) - '0'][b.charAt(j) - '0'][cur][msk];
                }
            }
            res[Integer.bitCount(msk)] = Math.min(res[Integer.bitCount(msk)], val);
        }
        return res;
    }
    private int[] one(String s) {
        int res = 0;
        for (int i = 0; i * 2 < s.length(); i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) res++;
        }
        return new int[]{0, res, Integer.MAX_VALUE};
    }
}
