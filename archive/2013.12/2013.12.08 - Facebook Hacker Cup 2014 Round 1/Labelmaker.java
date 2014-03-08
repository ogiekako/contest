package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class Labelmaker {
    int MX = 60;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        long N = in.nextLong();
        out.printFormat("Case #%d: %s\n", testNumber, solve(s, N));
    }
    private String solve(String s, long n) {
        char[] cs = s.toCharArray();
        Arrays.sort(cs);
        int m = cs.length;
        long[] pow = new long[MX];
        for (int i = 0; i < MX; i++) {
            pow[i] = i == 0 ? 1 : pow[i - 1] <= Long.MAX_VALUE / m ? pow[i - 1] * m : Long.MAX_VALUE;
        }
        int len = 0;
        while (pow[len] <= n) n -= pow[len++];
        char[] res = new char[len];
        for (int i = 0; i < len; i++) {
            int j = 0;
            while (pow[len - 1 - i] <= n) {
                n -= pow[len - 1 - i];
                j++;
            }
            res[i] = cs[j];
        }
        return String.valueOf(res);
    }
}
