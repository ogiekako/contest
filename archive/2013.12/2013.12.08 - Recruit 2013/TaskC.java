package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.math.BigInteger;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            int n = in.nextInt();
            String s = in.next();
            int i = 0;
            BigInteger[] S = new BigInteger[n];
            for (int j = 0; j < n; j++) {
                S[j] = BigInteger.ZERO;
            }
            int p = 0;
            while (i < s.length()) {
                if (s.charAt(i) == 'X') {
                    while (i < s.length() && !Character.isDigit(s.charAt(i))) i++;
                    if(i < s.length()) S[p] = S[p].multiply(BigInteger.valueOf(s.charAt(i) - '0'));
                    i++;
                } else if (s.charAt(i) == 'D') {
                    while (i < s.length() && !Character.isDigit(s.charAt(i))) i++;
                    if(i < s.length()) S[p] = S[p].divide(BigInteger.valueOf(s.charAt(i) - '0'));
                    i++;
                } else if (s.charAt(i) == 'S') {
                    while (i < s.length() && !Character.isDigit(s.charAt(i))) i++;
                    if(i < s.length()) S[p] = S[p].subtract(BigInteger.valueOf(s.charAt(i) - '0'));
                    i++;
                } else {
                    S[p] = S[p].add(BigInteger.valueOf(s.charAt(i) - '0'));
                    i++;
                }
                p = p + 1;
                p %= n;
            }
            BigInteger res = BigInteger.ZERO;
            for (BigInteger b : S) res = res.max(b);
            out.println(res);
        }
    }
}
