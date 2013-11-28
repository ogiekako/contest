package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.HashSet;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        int[] is = new int[n];
        for (int i = 0; i < n; i++) {
            is[i] = toi(in.next());
        }
        for (int i = 0, mask = 0; i < 31; i++) {
            mask = mask | 1 << 31 - i;
            HashSet<Integer> set = new HashSet<Integer>();
            for (int j : is) set.add(j & mask);
            if (set.size() == k) {
                out.println(tos(mask));
                return;
            }
        }
        out.println(-1); return;
    }

    private String tos(int mask) {
        String res = "";
        for (int i = 0; i < 4; i++) {
            if (i > 0) res += '.';
            res += (mask >>> 32 - 8);
            mask <<= 8;
        }
        return res;
    }

    private int toi(String s) {
        String[] ss = s.split("\\.");
        int res = 0;
        for (String t : ss) {
            res <<= 8;
            res |= Integer.valueOf(t);
        }
        return res;
    }
}
