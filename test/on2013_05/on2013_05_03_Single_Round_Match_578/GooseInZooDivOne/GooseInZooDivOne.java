package on2013_05.on2013_05_03_Single_Round_Match_578.GooseInZooDivOne;


import net.ogiekako.algorithm.dataStructure.UnionFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class GooseInZooDivOne {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    int MOD = (int) 1000000007;
    public int count(String[] field, int dist) {
        int h = field.length, w = field[0].length();
        List<Integer> count = new ArrayList<Integer>();
        UnionFind uf = new UnionFind(h * w);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (int i2 = 0; i2 < h; i2++) {
                    for (int j2 = 0; j2 < w; j2++) {
                        int d = Math.abs(i - i2) + Math.abs(j - j2);
                        if (d <= dist && field[i].charAt(j) == 'v' && field[i2].charAt(j2) == 'v') {
                            uf.union(i * w + j, i2 * w + j2);
                        }
                    }
                }
            }
        }
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                if (field[i].charAt(j) == 'v' && uf.root(i * w + j) == i * w + j) {
                    count.add(uf.size(i * w + j));
                }
        int[][] dp = new int[count.size() + 1][2];
        dp[0][0] = 1;
        for (int i = 0; i < count.size(); i++)
            for (int j = 0; j < 2; j++) {
                if (count.get(i) % 2 == 0) {
                    dp[i + 1][j] += dp[i][j];
                    if (dp[i + 1][j] >= MOD) dp[i + 1][j] -= MOD;
                    dp[i + 1][j] += dp[i][j];
                    if (dp[i + 1][j] >= MOD) dp[i + 1][j] -= MOD;
                } else {
                    dp[i + 1][j] += dp[i][j];
                    if (dp[i + 1][j] >= MOD) dp[i + 1][j] -= MOD;
                    dp[i + 1][j ^ 1] += dp[i][j];
                    if (dp[i + 1][j ^ 1] >= MOD) dp[i + 1][j ^ 1] -= MOD;
                }
            }
        return (dp[count.size()][0] - 1 + MOD) % MOD;
    }

    public static void main(String[] args) {
        String[] ss = new String[50];
        Random rnd = new Random(124018924L);
        int d = 3;
        for (int i = 0; i < ss.length; i++) {
            ss[i] = "";
            for (int j = 0; j < 50; j++) {
                ss[i] += rnd.nextInt(10) == 0 ? 'v' : '.';
            }
        }
        int res = new GooseInZooDivOne().count(ss, d);
        debug(ss);

        System.out.println(res);
    }
}
