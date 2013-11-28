package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int a = in.nextInt();
        String s = in.next();
        int n = s.length();
        long[] count = new long[n * 9 + 1];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += s.charAt(j) - '0';
                count[sum]++;
            }
        }
        if (a == 0) {
            out.println(count[0] * n * (n + 1) - count[0] * count[0]);
            return;
        }
        long res = 0;
        for (int i = 1; i * i <= a; i++)
            if (a % i == 0) {
                if (i * i == a) {
                    if (i < count.length)
                        res += count[i] * count[i];
                } else {
                    if (a / i < count.length)
                        res += count[i] * count[a / i] * 2;
                }
            }
        out.println(res);
    }
}
