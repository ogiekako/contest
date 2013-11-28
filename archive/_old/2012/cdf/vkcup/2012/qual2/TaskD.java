package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.Palindrome;

import java.io.PrintWriter;

public class TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        int n = s.length();
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        for (char c : s.toCharArray()) {
            sb.append(c);
            sb.append('$');
        }
        s = sb.toString();
        int[] rs = Palindrome.palindromeRadius(s);
        long[] left = new long[n], right = new long[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= 2 * i + 1; j++) {
                if (j + rs[j] >= 2 * i + 1) {
                    left[i]++;
                }
            }
            for (int j = s.length() - 1; j >= 2 * i + 1; j--) {
                if (j - rs[j] <= 2 * i + 1) {
                    right[i]++;
                }
            }
        }
        long res = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                res += left[i] * right[j];
            }
        out.println(res);
    }
}
