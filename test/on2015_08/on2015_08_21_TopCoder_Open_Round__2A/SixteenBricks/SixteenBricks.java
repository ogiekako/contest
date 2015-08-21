package on2015_08.on2015_08_21_TopCoder_Open_Round__2A.SixteenBricks;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class SixteenBricks {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public int maximumSurface(int[] height) {
        Arrays.sort(height);
        ArrayUtils.reverse(height);
        int[] dp = new int[1 << 16];
        int INF = Integer.MAX_VALUE / 2;
        Arrays.fill(dp, -INF);
        dp[0] = 0;
        for (int i = 0; i < height.length; i++) {
            int[] nDp = new int[1 << 16];
            Arrays.fill(nDp, -INF);
            for (int j = 0; j < 1 << 16; j++) {
                if (dp[j] > -INF) {
                    for (int k = 0; k < 16; k++) {
                        if (j << 31 - k < 0) {
                            continue;
                        }
                        int val = dp[j] + 4 * height[i];
                        int x = k / 4, y = k % 4;
                        for (int d = 0; d < 4; d++) {
                            int nx = x + dx[d];
                            int ny = y + dy[d];
                            if (0 <= nx && nx < 4 && 0 <= ny && ny < 4) {
                                int pk = nx * 4 + ny;
                                if (j << 31 - pk >= 0) {
                                    continue;
                                }
                                val -= height[i] * 2;
                            }
                        }
                        nDp[j | 1 << k] = Math.max(nDp[j | 1 << k], val);
                    }
                }
            }
            dp = nDp;
        }
        return dp[(1 << 16) - 1] + 16;
    }
}
