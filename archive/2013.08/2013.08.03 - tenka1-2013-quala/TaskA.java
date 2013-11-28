package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import java.util.*;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long a = 42;
        while(a <= 130000000)a*=2;
        out.println(a);
    }
}
