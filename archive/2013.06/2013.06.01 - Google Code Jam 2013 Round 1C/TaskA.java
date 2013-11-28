package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        final int n = in.nextInt();
        int N = s.length();
        int sum = 0;
        long res = (long) N * (N + 1) / 2;
        for (int i = 0, j = 1; j <= N; j++) {
            if (! "aiueo".contains("" + s.charAt(j - 1))) {
                sum++;
            } else {
                sum = 0;
            }
            if (sum >= n) {
                i = j - n + 1;
            }
            res -= j - i;
        }
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
}
