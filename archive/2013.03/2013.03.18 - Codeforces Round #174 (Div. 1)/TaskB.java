package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    int n;
    int[] a;
    long[][] v;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        n = in.nextInt();
        a = new int[n];
        for (int i = 1; i < n; i++) a[i] = in.nextInt();

        v = new long[2][n];
        for (int i = 0; i < 2; i++) for (int j = 0; j < n; j++) v[i][j] = -1;
        for (int i = 0; i < 2; i++) for (int j = 1; j < n; j++) if (v[i][j] == -1) dfs(i, j);

        for (int i = 1; i < n; i++) {
            if (v[1][i] >= 0) {
                out.println(v[1][i] + i);
            } else {
                out.println(-1);
            }
        }
    }

    private void dfs(int mode, int val) {
        if (v[mode][val] != -1) return;
        v[mode][val] = -2;
        int nMode = 1 ^ mode;
        int nVal = val + (mode == 0 ? a[val] : -a[val]);
        if (nVal < 0 || nVal >= n) {
            v[mode][val] = a[val];
        } else {
            dfs(nMode, nVal);
            if (v[nMode][nVal] == -2) v[mode][val] = -2;
            else v[mode][val] = v[nMode][nVal] + a[val];
        }
    }

    enum PAT {
        OVER,
        LOOP,
    }
}
