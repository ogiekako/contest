package src;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class ShufflingCardsDiv1 {
    public int shuffle(int[] permutation) {
        int[] in = permutation.clone();
        for (int i = 0; i < permutation.length; i++) {
            permutation[in[i] - 1] = i;
        }
        boolean zero = true;
        int n = permutation.length / 2;
        for (int i = 0; i < n * 2; i++) {
            if (permutation[i] != i) zero = false;
        }
        if (zero) return 0;
        int odd = 0;
        int goal = 0;
        for (int i = 0; i < n; i++) {
            if (permutation[i] % 2 == 1) goal++;
        }
        if (odd == goal) return 1;
        boolean[] possible = new boolean[n + 1];
        boolean[][] dp = new boolean[2][n + 1];
        dp[0][odd] = true;
        possible[odd] = true;
        int cur = 0, nxt = 1;
        for (int i = 0; i < 30; i++) {
            Arrays.fill(dp[nxt], false);
            int right = n / 2, left = n - right;
            for (int j = 0; j < n + 1; j++)
                if (dp[cur][j]) {
                    int min1 = Math.min(j, left);
                    int max1 = Math.max(0, left - (n - j));
                    int min2 = Math.min(n - j, right);
                    int max2 = Math.max(0, right - j);
                    for (int k = max1 + max2; k <= min1 + min2; k++)
                        if (!possible[k]) {
                            possible[k] = true;
                            dp[nxt][k] = true;
                        }
                }
            if (dp[nxt][goal]) return i + 2;
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        return -1;
    }

    public static void main(String[] args) {
        Random rnd = new Random(11241928L);
        for (; ; ) {
            int n = rnd.nextInt(998) * 2 + 4;
            int[] perm = new int[n];
            for (int i = 0; i < n; i++) {
                perm[i] = i % 2 == 0 ? n - i / 2 : n / 2 - i / 2;
//            perm[i] = i + 1;
            }
//        System.err.println(Arrays.toString(perm));
            ArrayUtils.shuffle(perm);
//        System.out.println(Arrays.toString(perm));

            int res = new ShufflingCardsDiv1().shuffle(perm.clone());
            if (res > 4) {
                System.out.println(res);
                System.out.println(Arrays.toString(perm));
                throw new AssertionError();
            }
        }
    }
}
