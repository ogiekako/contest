package net.ogiekako.algorithm.utils;

import java.util.Arrays;

public class Permutation {
    /**
     * permutationの巡回置換の数を返す.
     *
     * @param permutation - the given array
     * @return - the number of cycles.
     */
    public static int numCyclesInPermutation(int[] permutation) {
        int n = permutation.length;
        boolean[] visited = new boolean[n];
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                res++;
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    j = permutation[j];
                }
            }
        }
        return res;
    }

    public static boolean nextPermutation(int[] is) {
        int n = is.length;
        for (int i = n - 1; i > 0; i--) {
            if (is[i - 1] < is[i]) {
                int j = n;
                //noinspection StatementWithEmptyBody
                while (is[i - 1] >= is[--j])
                    ;
                ArrayUtils.swap(is, i - 1, j);
                ArrayUtils.rev(is, i, n);
                return true;
            }
        }
        ArrayUtils.rev(is, 0, n);
        return false;
    }

    public static boolean nextPermutation(char[] is) {
        int n = is.length;
        for (int i = n - 1; i > 0; i--) {
            if (is[i - 1] < is[i]) {
                int j = n;
                //noinspection StatementWithEmptyBody
                while (is[i - 1] >= is[--j])
                    ;
                ArrayUtils.swap(is, i - 1, j);
                ArrayUtils.rev(is, i, n);
                return true;
            }
        }
        ArrayUtils.rev(is, 0, n);
        return false;
    }

    /**
     * permutationを巡回置換の積で表したときの,各巡回置換の位数をソートした並べたものを返す.
     * これが等しいものは,共役である.
     *
     * @param permutation - the given array
     * @return - the sorted orders for each permutation.
     */
    public static int[] topology(int[] permutation) {
        boolean[] vis = new boolean[permutation.length];
        int[] res = new int[permutation.length];
        int m = 0;
        for (int i = 0; i < permutation.length; i++)
            if (!vis[i]) {
                int cnt = 0;
                int j = i;
                do {
                    cnt++;
                    vis[j] = true;
                    j = permutation[j];
                } while (j != i);
                res[m++] = cnt;
            }
        int[] res2 = new int[m];
        System.arraycopy(res, 0, res2, 0, m);
        Arrays.sort(res2);
        return res2;
    }

    /**
     * 置換が最小何回の互換で表せるかを返す.
     * それは,要素数 - 巡回置換の数 である.
     *
     * @param permutation - the given array.
     * @return - the number of minimum swaps to make the permutation
     */
    public static int numMinSwap(int[] permutation) {
        return permutation.length - numCyclesInPermutation(permutation);
    }

    public static int sign(int[] permutation) {
        int res = 1;
        for(int j=0;j<permutation.length;j++)for(int i=0;i<j;i++)if(permutation[i] > permutation[j])res *= -1;
        return res;
    }

    public static int[] reversed(int[] permutation) {
        int[] res = new int[permutation.length];
        for (int i = 0; i < res.length; i++)res[permutation[i]] = i;
        return res;
    }
}
