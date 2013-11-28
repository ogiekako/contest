package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class Test extends ArrayUtils {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int i;
        while ((i = in.nextInt()) != 42) {
            out.println(i);
        }
    }
}