package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Permutation;

import java.util.ArrayList;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        ArrayList<Integer>[] es = new ArrayList[n];
        for (int i = 0; i < n; i++)
            es[i] = new ArrayList<Integer>();
        for (int i = 0; i < n * 2; i++) {
            int x = in.nextInt() - 1, y = in.nextInt() - 1;
            es[x].add(y); es[y].add(x);
        }
        for (ArrayList<Integer> e : es)
            if (e.size() != 4) {
                out.println(-1); return;
            }
        if (n <= 6) {
            int[] is = new int[n];
            for (int i = 0; i < n; i++)
                is[i] = i;
            do {
                boolean ok = true;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++) {
                        int d = (i - j + n) % n;
                        if (0 < d && d <= 2 && !es[is[i]].contains(is[j])) ok = false;
                    }
                if (ok) {
                    for (int i = 0; i < n; i++) out.printFormat("%d%c", is[i] + 1, i == n - 1 ? '\n' : ' ');
                    return;
                }
            } while (Permutation.nextPermutation(is));
            out.println(-1); return;
        }
        int cur = 0;
        int nxt = -1;
        int bef = -1;
        for (int x : es[0]) {
            int count = 0;
            for (int y : es[0]) if (x != y && es[x].contains(y)) count++;
            if (count == 2) {
                if (bef == -1) bef = x;
                else nxt = x;
            }
        }
        boolean[] used = new boolean[n];
        if (nxt == -1 || bef == -1) {
            out.println(-1); return;
        }
        used[cur] = used[nxt] = true;
        int[] res = new int[n];
        int resPtr = 0;
        res[resPtr++] = cur;
        res[resPtr++] = nxt;
        for (int i = 0; i < n - 2; i++) {
            int nxt2 = -1;
            for (int x : es[cur])
                if (x != bef && !used[x] && es[x].contains(cur) && es[x].contains(nxt)) nxt2 = x;
            if (nxt2 == -1) {
                out.println(-1); return;
            }
            res[resPtr++] = nxt2;
            used[nxt2] = true;
            bef = cur;
            cur = nxt;
            nxt = nxt2;
        }
        for (int i = 0; i < n; i++) {
            out.printFormat("%d%c", res[i] + 1, i == n - 1 ? '\n' : ' ');
        }
    }
}
