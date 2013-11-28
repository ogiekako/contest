package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class FoxAndPhotography {
    int MX = 10000;

    public int getMinimumSwaps(int[] heightsFront, int[] heightsBack) {
        memo = new int[1 << heightsBack.length];
        Arrays.fill(memo, -1);
        int res = dfs(0, heightsFront, heightsBack);
        return res >= MX ? -1 : res;
    }

    int[] memo;
    int dfs(int msk, int[] front, int[] back) {
        int i = Integer.bitCount(msk);
        if (i == front.length) return 0;
        if (memo[msk] >= 0) return memo[msk];
        int res = MX;
        for (int j = 0, k = 0; j < back.length; j++)
            if ((msk >> j & 1) == 0) {
                if (front[i] < back[j])
                    res = Math.min(res, k + dfs(msk | 1 << j, front, back));
                k++;
            }
        return memo[msk] = res;
    }
}

