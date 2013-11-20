package on2013_08.on2013_08_03_tenka1_2013_quala.TaskA;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long a = 42;
        while (a <= 130000000) a *= 2;
        out.println(a);
    }
}
