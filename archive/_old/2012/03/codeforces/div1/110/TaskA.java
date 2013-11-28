package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskA {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int MX = 2010;
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < MX; i++) b.append('.');
        String s = in.next();
        b.append(s);
        for (int i = 0; i < MX; i++) b.append('.');
        s = b.toString();
        String t = in.next();
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < s.length() - t.length() + 1; i++) {
            String sub = s.substring(i, i + t.length());
            int cnt = 0;
            for (int j = 0; j < t.length(); j++) {
                if (s.charAt(i + j) != t.charAt(j)) cnt++;
            }
            res = Math.min(res, cnt);
        }
        out.println(res);
    }
}
