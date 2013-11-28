package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.math.BigInteger;

public class GCJ2011R3D {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        long res = solve(s);
        BigInteger b = BigInteger.valueOf(res).pow(2);
        out.printf("Case #%d: %s\n", testNumber, b.toString(2));
    }

    private long solve(String s) {
        if (s.length() == 1) return 1;
        int n = s.length();
        char last = s.charAt(n - 1);
        if (last != '0') {
            long res = odd(s.substring(0, n - 1) + '1');
            if (res >= 0) return res;
        }
        if (s.charAt(n - 2) == '1') throw new AssertionError();
        long res = solve(s.substring(0, n - 2)) << 1;
        return res;
    }

    String s;
    int n, n2;
    private long odd(String s) {
        this.s = s;
        n = s.length(); n2 = (n + 1) / 2;
        int first = 0, second = 0;
        for (int i = 0; i < n2; i++) if (s.charAt(i) == '?') first++;
        for (int i = 0; i < n2; i++) if (s.charAt(n - 1 - i) == '?') second++;
        if (first < second) return first();
        else return second();
    }

    private long second() {
        int numQ = 0;
        for (int i = 0; i <= n2; i++) if (s.charAt(n - 1 - i) == '?') numQ++;
        int[] q = new int[numQ];
        numQ = 0;
        for (int i = 0; i <= n2; i++) if (s.charAt(n - 1 - i) == '?') q[numQ++] = n - 1 - i;
        char[] cs = s.toCharArray();
        for (int i = 0; i < 1 << numQ; i++) {
            for (int j = 0; j < numQ; j++) {
                if (i << 31 - j < 0) cs[q[j]] = '1';
                else cs[q[j]] = '0';
            }
            long res = second(cs);
            if (res >= 0) return res;
        }
        return -1;
    }

    private long second(char[] cs) {
        for (int i = 0; i < 4; i += 2) {
            long r = i + 1;
            int d = 2;
            // 2^d A + r
            while (d < n2) {
                long r2 = r * r;
                if (cs[n - 1 - (d + 1)] == '?') throw new AssertionError();
                long a = cs[n - 1 - (d + 1)] - '0' ^ (r2 >>> d + 1 & 1);
                r |= a << d;
                d++;
            }
            if (isValid(r, cs)) return r;
        }
        return -1;
    }

    private boolean isValid(long res, char[] cs) {
        BigInteger b = BigInteger.valueOf(res).pow(2);
        if (b.bitLength() != cs.length) return false;
        for (int i = 0; i < n; i++) {
            if (b.testBit(i) && cs[n - 1 - i] == '0' || !b.testBit(i) && cs[n - 1 - i] == '1') return false;
        }
        return true;
    }

    private long first() {
        int numQ = 0;
        for (int i = 0; i < n2; i++) if (s.charAt(i) == '?') numQ++;
        int[] q = new int[numQ];
        numQ = 0;
        for (int i = 0; i < n2; i++) if (s.charAt(i) == '?') q[numQ++] = i;
        char[] cs = s.toCharArray();
        for (int i = 0; i < 1 << numQ; i++) {
            for (int j = 0; j < numQ; j++) {
                if (i << 31 - j < 0) cs[q[j]] = '1';
                else cs[q[j]] = '0';
            }
            long res = first(cs);
            if (res >= 0) return res;
        }
        return -1;
    }

    private long first(char[] cs) {
        char[] largest = cs.clone();
        for (int i = 0; i < largest.length; i++) if (largest[i] == '?') largest[i] = '1';
        BigInteger b = new BigInteger(String.valueOf(largest), 2);
        long r = MathUtils.sqrt(b).longValue();
        if (isValid(r, cs)) return r;
        return -1;
    }
}
