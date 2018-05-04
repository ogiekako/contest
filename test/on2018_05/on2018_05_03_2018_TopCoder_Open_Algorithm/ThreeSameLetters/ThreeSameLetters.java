package on2018_05.on2018_05_03_2018_TopCoder_Open_Algorithm.ThreeSameLetters;



import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class ThreeSameLetters {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int countStrings(int L, int S) {
        Mint.set1e9_7();
        Mint[][][][] dp = new Mint[2][S+1][S+1][2];
        ArrayUtils.fill(dp, Mint.ZERO);
        dp[0][S][S][0] = Mint.ONE;
        int cur=0,nxt=1;
        for (int i = 0; i < L; i++) {
            ArrayUtils.fill(dp[nxt], Mint.ZERO);
            for (int j = 0; j < S + 1; j++) {
                for (int k = 0; k < S + 1; k++) {
                    for (int l = 0; l < 2; l++) {
                        for (int m = 0; m < S; m++) {
                            int nl = l + (j==k && k ==m ? 1 : 0);
                            if (nl > 1) continue;
                            dp[nxt][k][m][nl] = dp[nxt][k][m][nl].add(dp[cur][j][k][l]);
                        }
                    }
                }
            }
            int tmp=cur;cur=nxt;nxt=tmp;
        }
        Mint res = Mint.ZERO;
        for (int i = 0; i < S; i++) {
            for (int j = 0; j < S; j++) {
                res = res.add(dp[cur][i][j][1]);
            }
        }
        return res.get();
    }
}
