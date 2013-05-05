package on_2012.on2012_8_24.pizzatossing;


import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.CharArray_Uncopied;

import java.io.PrintWriter;

public class PizzaTossing {
    int MOD = (int) (1e9 + 7);

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        CharSequence obj = gen(in), seq = gen(in);
        int[] link = generateFailureLink(obj);
        int where = match(seq, obj, link);
        for (int k = 1; k < link.length; k++) {
            int p = link[k];
            if (obj.charAt(p) == obj.charAt(k)) link[k] = link[p];
        }
        if (where == -1) {
            out.println(0); return;
        }
        int n = obj.length();
        int[] b = new int[n + 1];
        b[1] = 2;
        for (int k = 1; k < n; k++) {
            int p = link[k] + 1;
            b[k + 1] = ((b[k] << 1) - b[p] + 2);
            if (b[k + 1] < 0) b[k + 1] += MOD;
            if (b[k + 1] >= MOD) b[k + 1] -= MOD;
        }
        int res = b[n] - b[where];
        if (res < 0) res += MOD;
        out.println(res);
    }

    private CharSequence gen(MyScanner in) {
        int n = in.nextInt();
        String s = in.next();
        char[] cs = new char[n];
        int p = 0;
        for (int i = 0; i < s.length() && p < n; i++) {
            char c = s.charAt(i);
            int t;
            if ('a' <= c && c <= 'z') t = c - 'a';
            else t = c - 'A' + 26;
            for (int j = 0; j < 5 && p < n; j++) {
                char d = (char) ('0' + (t >> 4 - j & 1));
                cs[p++] = d;
            }
        }
        return new CharArray_Uncopied(cs);
    }

    static int[] generateFailureLink(CharSequence pattern) {
        int[] next = new int[pattern.length()];
        for (int i = 0, j = -2; i < pattern.length(); i++) {
            while (j >= 0 && pattern.charAt(i - 1) != pattern.charAt(j)) {
                j = next[j];
            }
            next[i] = ++j;
        }
        return next;
    }

    static int match(CharSequence text, CharSequence pattern, int[] next) {
        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j)) {
                j = next[j];
            }
            j++;
            if (j == pattern.length()) return -1;
        }
        return j;
    }
}
