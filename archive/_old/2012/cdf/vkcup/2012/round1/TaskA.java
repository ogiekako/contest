package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        int x = in.nextInt(), y = in.nextInt();
        int[] as = new int[n];
        int[] bs = new int[m];
        for (int i = 0; i < n; i++) as[i] = in.nextInt();
        for (int i = 0; i < m; i++) bs[i] = in.nextInt();
        ArrayList<Long> list = new ArrayList<Long>();
        for (int i = 0, j = 0; i < n && j < m; ) {
            if (as[i] - x <= bs[j] && bs[j] <= as[i] + y) {
                list.add((long) (i + 1) << 32 | j + 1);
                i++; j++;
            } else if (bs[j] < as[i] - x) j++;
            else i++;
        }
        out.println(list.size());
        for (long l : list) {
            long a = l >> 32;
            long b = (int) l;
            out.println(a + " " + b);
        }
    }
}
