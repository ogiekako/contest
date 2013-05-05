package on_2012.on2012_6_1.taskb;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        String[] ss = new String[n];
        for (int i = 0; i < n; i++) ss[i] = in.next();
        Arrays.sort(ss, Collections.reverseOrder());
        StringBuilder b = new StringBuilder();
        for (String s : ss) b.append(s);
        out.println(b);
    }
}
