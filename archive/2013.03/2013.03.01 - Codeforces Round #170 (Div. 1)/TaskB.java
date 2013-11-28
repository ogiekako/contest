package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        if (m == 3) {
            if (n == 3) {
                String res = "0 0\n" +
                        "3 0\n" +
                        "0 3";
                out.println(res);
                return;
            } else if (n == 4) {
                String res = "0 0\n" +
                        "3 0\n" +
                        "0 3\n" +
                        "1 1";
                out.println(res);
                return;
            } else {
                out.println(-1);
                return;
            }
        } else if (m == 4) {
            int MAX = (int) 1e8 - 10;
            double t = 2 * Math.PI / 4;
            for (int i = 0; i < n; i++) {
                int x, y;
                if (i < m) {
                    x = (int) Math.round(Math.cos(t * i) * MAX);
                    y = (int) Math.round(Math.sin(t * i) * MAX);
                } else {
                    x = (int) Math.round(Math.cos(t * (i - m) + Math.PI / 100) * MAX / 2);
                    y = (int) Math.round(Math.sin(t * (i - m) + Math.PI / 100) * MAX / 2);
                }
                out.printFormat("%d %d\n", x, y);
            }
        } else {
            int MAX = (int) 1e8 - 10;
            double t = 2 * Math.PI / (m + 1 - m % 2);
            for (int i = 0; i < n; i++) {
                int x, y;
                if (i < m) {
                    x = (int) Math.round(Math.cos(t * i) * MAX);
                    y = (int) Math.round(Math.sin(t * i) * MAX);
                } else {
                    x = (int) Math.round(Math.cos(t * (i - m)) * MAX / 2);
                    y = (int) Math.round(Math.sin(t * (i - m)) * MAX / 2);
                }
                out.printFormat("%d %d\n", x, y);
            }
        }
    }
}
