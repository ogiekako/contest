package tmp;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class Euler105 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        long res = 0;
        for (int i = 0; i < n; i++) {
            String[] ss = in.next().split(",");
            int[] is = new int[ss.length];
            for (int j = 0; j < ss.length; j++) {
                is[j] = Integer.valueOf(ss[j]);
            }
            if (isSpecialSumSet(is)) {
                res += ArrayUtils.sum(is);
            }
        }
        out.println(res);
    }

    boolean isSpecialSumSet(int[] is) {
        HashSet<Integer> set = new HashSet<Integer>();
        int[] sums = new int[1 << is.length];
        for (int j = 0; j < 1 << is.length; j++) {
            int sum = 0;
            for (int k = 0; k < is.length; k++)
                if ((j >> k & 1) == 1) {
                    sum += is[k];
                }
            if (set.contains(sum)) {
                return false;
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
                        return false;
                    }
                    tmp = Math.max(tmp, sums[j]);
                }
            mx = tmp;
        }
        return true;
    }
}
