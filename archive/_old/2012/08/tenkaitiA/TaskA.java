package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] as = new int[n];
        for (int i = 0; i < n; i++) as[i] = in.nextInt();
        int[] sorted = as.clone();
        ArrayUtils.sort(sorted);
        int diff = 0;
        for (int i = 0; i < n; i++) if (as[i] != sorted[i]) diff++;
        if (diff <= 2) out.println("YES");
        else out.println("NO");
    }
}
