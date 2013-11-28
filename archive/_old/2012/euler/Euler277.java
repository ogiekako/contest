package tmp;

import net.ogiekako.algorithm.utils.StringUtils;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler277 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong();
        String s = StringUtils.reverse(in.next());
        loop:
        for (int k = 1; ; k++) {
            long tmp = k;
            for (char c : s.toCharArray()) {
                if (c == 'D') {
                    tmp *= 3;
                } else if (c == 'U') {
                    if (tmp % 4 != 2) continue loop;
                    tmp = 3 * (tmp / 4) + 1;
                } else if (c == 'd') {
                    if (tmp % 2 != 1) continue loop;
                    tmp = 3 * (tmp / 2) + 2;
                }
            }
            if (tmp > N) {
                out.println(tmp); return;
            }
        }
    }
}
