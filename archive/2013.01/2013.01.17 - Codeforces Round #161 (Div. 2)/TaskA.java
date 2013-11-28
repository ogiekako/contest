package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (in.nextInt() == 1) {
                    out.println(Math.abs(2 - i) + Math.abs(2 - j));
                    return;
                }
    }
}
