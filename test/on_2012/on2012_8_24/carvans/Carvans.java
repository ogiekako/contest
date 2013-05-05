package on_2012.on2012_8_24.carvans;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class Carvans {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int min = Integer.MAX_VALUE;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int p = in.nextInt();
            if (min >= p) res++;
            min = Math.min(min, p);
        }
        out.println(res);
    }
}
