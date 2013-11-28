package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.Permutation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class TheBrickTowerMediumDivOne {
    public int[] find(int[] heights) {
        int n = heights.length;
        int[] res = new int[n];
        res[0] = 0;
        boolean[] visited = new boolean[n];
        visited[0] = true;
        int i;
        for (i = 1; i < n; i++) {
            int j;
            for (j = 0; j < n; j++)
                if (!visited[j] && heights[res[i - 1]] >= heights[j]) {
                    visited[j] = true;
                    res[i] = j;
                    break;
                }
            if (j == n) break;
        }
        for (; i < n; i++) {
            int k = -1;
            for (int j = n - 1; j >= 0; j--)
                if (!visited[j] && (k == -1 || heights[j] <= heights[k])) {
                    k = j;
                }
            visited[k] = true;
            res[i] = k;
        }
        return res;
    }

    public static void main(String[] args) {
        TheBrickTowerMediumDivOne instance = new TheBrickTowerMediumDivOne();
        Random rnd = new Random(140192L);

        for (; ; ) {
            System.out.print(".");
            int[] as = new int[47];
            for (int i = 0; i < as.length; i++) as[i] = rnd.nextInt(47) + 1;
            System.err.println(Arrays.toString(as).replace(" ", ""));
            int[] res = instance.find(as.clone());
            int[] exp = solveStupid(as.clone());
            if (!Arrays.equals(exp, res))
                throw new AssertionError(Arrays.toString(as) + " " + Arrays.toString(res) + " " + Arrays.toString(exp));
        }
    }

    private static int[] solveStupid(int[] as) {
        Comparator<int[]> comp = new Comparator<int[]>() {
            public int compare(int[] o1, int[] o2) {
                for (int i = 0; i < o1.length; i++) if (o1[i] != o2[i]) return o1[i] - o2[i];
                return 0;
            }
        };
        int[] is = new int[as.length];
        for (int i = 0; i < is.length; i++) is[i] = i;
        int[] res = is.clone();
        do {
            int dist = calc(as, is);
            int curDist = calc(as, res);
            if (curDist > dist || curDist == dist && comp.compare(is, res) < 0) {
                res = is.clone();
            }
        } while (Permutation.nextPermutation(is));
        return res;
    }

    private static int calc(int[] as, int[] is) {
        int res = 0;
        for (int i = 0; i < is.length - 1; i++) {
            res += Math.max(as[is[i]], as[is[i + 1]]);
        }
        return res;
    }
}

