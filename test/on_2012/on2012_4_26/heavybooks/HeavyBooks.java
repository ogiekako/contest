package on_2012.on2012_4_26.heavybooks;


// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;

public class HeavyBooks {
    public int[] findWeight(int[] books, int[] moves) {
        int S = moves[0];
        long T = 0, W = (1L << S) - 1;
        for (int i = 1; i < moves.length; i++) {
            int cnt = moves[i];
            if (i % 2 == 0) {
                for (int j = S - 1; j >= 0 && cnt > 0; j--) {
                    if ((T >> j & 1) == 1) {
                        cnt--;
                        T ^= 1L << j;
                        W ^= 1L << j;
                    }
                }
            } else {
                for (int j = S - 1; j >= 0 && cnt > 0; j--) {
                    if ((W >> j & 1) == 1) {
                        cnt--;
                        W ^= 1L << j;
                        T ^= 1L << j;
                    }
                }
            }
        }

        Arrays.sort(books);
        int n = books.length;
        Pair<Integer, Integer>[][] dp = new Pair[n + 1][S + 1];
        for (int i = 0; i < n + 1; i++)
            for (int j = 0; j < S + 1; j++) {
                dp[i][j] = new Pair<Integer, Integer>(Integer.MIN_VALUE, 0);
            }
        dp[0][0] = new Pair<Integer, Integer>(0, 0);
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= S; j++)
                if (dp[i][j].first > Integer.MIN_VALUE) {
                    dp[i + 1][j] = max(dp[i + 1][j], dp[i][j]);
                    if (j < S) {
                        if ((T >> j & 1) == 0) {
                            dp[i + 1][j + 1] = max(dp[i + 1][j + 1], new Pair<Integer, Integer>(dp[i][j].first + books[i], dp[i][j].second + books[i]));
                        } else {
                            dp[i + 1][j + 1] = max(dp[i + 1][j + 1], new Pair<Integer, Integer>(dp[i][j].first - books[i], dp[i][j].second + books[i]));
                        }
                    }
                }

        int WmT = dp[n][S].first;
        System.err.println(dp[n][S].first);
        int WpT = dp[n][S].second;
        int r1 = (WpT - WmT) / 2;
        int r2 = (WpT + WmT) / 2;
        return new int[]{r1, r2};
    }

    private Pair<Integer, Integer> max(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        return a.compareTo(b) > 0 ? a : b;
    }


}

