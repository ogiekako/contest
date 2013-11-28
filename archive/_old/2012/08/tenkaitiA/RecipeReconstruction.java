package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class RecipeReconstruction {
    int MOD = 10000009;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        int res = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int j = s.length() - 1 - i;
            char d = s.charAt(j);
            if (c != '?' && d != '?' && c != d) {
                res = 0; break;
            } else if (c == '?' && d == '?' && i <= j) {
                res *= 26;
                if (res > MOD) res %= MOD;
            }
        }
        out.println(res);
    }
}
