package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class ProblemA {
    String[] from = {"ejp mysljylc kd kxveddknmc re jsicpdrysi", "rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd", "de kr kd eoya kw aej tysr re ujdr lkgc jv",
            "zq"};
    String[] to = {"our language is impossible to understand", "there are twenty six factorial possibilities", "so it is okay if you want to just give up",
            "qz"};
    static char[] map;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        if (map == null) {
            map = new char[256];
            for (int i = 0; i < from.length; i++) {
                for (int j = 0; j < from[i].length(); j++) {
                    map[from[i].charAt(j)] = to[i].charAt(j);
                }
            }
        }
        String s = in.nextLine();
        char[] res = new char[s.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = map[s.charAt(i)];
        }
        out.println("Case #" + testNumber + ": " + new String(res));
    }
}