package on_2012.on2012_6_7.dom2012b;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;

public class Dom2012B {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (; ; ) {
            int n = in.nextInt();
            int L = in.nextInt();
            if ((n | L) == 0) return;
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int i = 0; ; i++) {
                if (map.containsKey(n)) {
                    out.printf("%d %d %d\n", map.get(n), n, i - map.get(n)); break;
                }
                map.put(n, i);
                n = next(n, L);
            }
        }
    }

    private int next(int n, int L) {
        String s = "" + n;
        while (s.length() < L) s = "0" + s;
        char[] cs = s.toCharArray();
        Arrays.sort(cs);
        int small = Integer.valueOf(new String(cs));
        int large = Integer.valueOf(new StringBuilder(new String(cs)).reverse().toString());
        return large - small;
    }
}
