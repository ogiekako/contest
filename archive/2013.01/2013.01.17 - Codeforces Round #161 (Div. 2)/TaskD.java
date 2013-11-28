package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
        List<Integer>[] es = new ArrayList[n];
        for (int i = 0; i < n; i++) es[i] = new ArrayList<Integer>();
        for (int i = 0; i < m; i++) {
            int x = in.nextInt() - 1, y = in.nextInt() - 1;
            es[x].add(y); es[y].add(x);
        }
        int[] d = new int[n];
        int[] e = new int[n * 2];
        Arrays.fill(d, -1);
        loop:
        for (int step = 0, x = 0; ; step++) {
            d[x] = step;
            e[step] = x;
            for (int y : es[x])
                if (d[y] == -1) {
                    x = y;
                    continue loop;
                }
            for (int y : es[x]) {
                if (d[y] >= 0 && step - d[y] >= k) {
                    out.println(step - d[y] + 1);
                    while (e[step] != y) {
                        out.printFormat("%d ", e[step] + 1);
                        step--;
                    }
                    out.println(e[step] + 1);
                    return;
                }
            }
            throw new AssertionError();
        }
    }
}
