package tmp;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class Euler103 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int[] a = {11, 17, 20, 22, 23, 24};
        int[] b = new int[7];
        b[0] = a[7 / 2];
        for (int i = 0; i < 6; i++) {
            b[i + 1] = a[i] + b[0];
        }

        int D = 5;
        dfs(new int[7], 0, b, D);
        for (int i : res) out.print(i);
    }

    private void dfs(int[] is, int i, int[] base, int D) {
        if (i == is.length) {
            int S = 0;
            for (int j : is) S += j;
            if (S >= best) return;
            HashSet<Integer> set = new HashSet<Integer>();
            int[] sums = new int[1 << is.length];
            for (int j = 0; j < 1 << is.length; j++) {
                int sum = 0;
                for (int k = 0; k < is.length; k++)
                    if ((j >> k & 1) == 1) {
                        sum += is[k];
                    }
                if (set.contains(sum)) {
                    return;
                } else {
                    set.add(sum);
                }
                sums[j] = sum;
            }
            int mx = 0;
            for (int b = 1; b < is.length + 1; b++) {
                int tmp = 0;
                for (int j = 0; j < 1 << is.length; j++)
                    if (Integer.bitCount(j) == b) {
                        if (mx > sums[j]) {
                            return;
                        }
                        tmp = Math.max(tmp, sums[j]);
                    }
                mx = tmp;
            }
            if (best > sums[(1 << is.length) - 1]) {
                best = sums[(1 << is.length) - 1];
                res = is.clone();
            }
            return;
        }
        for (int j = -D; j <= D; j++) {
            is[i] = base[i] + j;
            dfs(is, i + 1, base, D);
        }
    }

    int best = Integer.MAX_VALUE;
    int[] res = null;
}
