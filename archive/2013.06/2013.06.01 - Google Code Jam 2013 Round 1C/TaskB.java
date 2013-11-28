package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int X = in.nextInt(), Y = in.nextInt();
        int len = Math.abs(X) + Math.abs(Y);
        int sum = 0;
        int n;
        for (n = 1; ; n++) {
            sum += n;
            if (sum >= len && sum % 2 == len % 2) break;
        }
        StringBuilder b = new StringBuilder();
        for (; n > 0; n--) {
            if (Math.abs(X) > Math.abs(Y)) {
                if (X > 0) {
                    b.append("E");
                    X -= n;
                } else {
                    b.append("W");
                    X += n;
                }
            } else {
                if (Y > 0) {
                    b.append("N");
                    Y -= n;
                } else {
                    b.append("S");
                    Y += n;
                }
            }
        }
        b.reverse();
        out.printFormat("Case #%d: %s\n",testNumber,b.toString());
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
}
