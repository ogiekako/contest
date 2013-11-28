package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.List;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        if (n > 6) {
            out.println("0");
            return;
        }
        int[] pow = new int[7];
        for (int i = 0; i < 7; i++) pow[i] = i == 0 ? 1 : pow[i - 1] * n;
        List<String> result = new ArrayList<String>();
        for (int len = 1; len <= 12; len++) {
            List<Integer> masks = new ArrayList<Integer>();
            for (int i = 0; i < 1 << len + 3; i++) {
                if (Integer.bitCount(i) != 3) continue;
                if ((i & 1) == 1) continue;
                if (i << 31 - (len + 2) < 0) continue;
                if ((i & (i >>> 1)) != 0) continue;
                masks.add(i);
            }
            for (int i = 0; i < pow[(len + 1) / 2]; i++) {
                char[] cs = new char[len];
                for (int j = 0, k = i; j * 2 < len; j++) {
                    cs[j] = cs[len - 1 - j] = (char) ('0' + a[k % n]);
                    k /= n;
                }
                boolean[] vis = new boolean[10];
                int cnt = 0;
                for (char c : cs) {
                    if (!vis[c - '0']) cnt++;
                    vis[c - '0'] = true;
                }
                if (cnt != n) continue;
                for (int mask : masks) {
                    char[] ds = new char[len + 3];
                    for (int j = 0, k = 0; j < len + 3; j++) {
                        if (mask << 31 - j < 0) ds[j] = '.';
                        else ds[j] = cs[k++];
                    }
                    String s = String.valueOf(ds);
                    if (valid(s)) result.add(s);
                }
            }
        }
        out.println(result.size());
        for (String s : result) out.println(s);
    }

    private boolean valid(String s) {
        for (String t : s.split("\\.")) {
            if (t.equals((""))) return false;
            if (!t.equals("0") && t.startsWith("0")) return false;
            if (Long.valueOf(t) >= 256) return false;
        }
        return true;
    }
}
