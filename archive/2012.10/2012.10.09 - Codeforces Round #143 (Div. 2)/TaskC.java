package tmp;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskC {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        long[] as = new long[n];
        for (int i = 0; i < n; i++) as[i] = in.nextInt();
        ArrayUtils.sort(as);
        long val = 0;
        int res = -1;
        int res2 = -1;
        for (int left = 0, right = 0; right < n; ) {
            if (val <= k) {
                if (res < right - left + 1) {
                    res = right - left + 1;
                    res2 = (int) as[right];
                }
                right++;
                if (right < n) {
                    val += (as[right] - as[right - 1]) * (right - left);
                }
            } else {
                val -= as[right] - as[left];
                left++;
            }
        }
        out.println(res + " " + res2);
    }
}
