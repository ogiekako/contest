package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class Krizaljka {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String a = in.next();
        String b = in.next();
        for (int i = 0; i < a.length(); i++)
            for (int j = 0; j < b.length(); j++)
                if (a.charAt(i) == b.charAt(j)) {
                    char[][] res = new char[b.length()][a.length()];
                    for (char[] cs : res) Arrays.fill(cs, '.');
                    for (int k = 0; k < a.length(); k++) res[j][k] = a.charAt(k);
                    for (int k = 0; k < b.length(); k++) res[k][i] = b.charAt(k);
                    for (char[] cs : res) out.println(cs);
                    return;
                }
    }
}
