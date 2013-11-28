package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskB {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt(), d = in.nextInt(), l = in.nextInt();
        if (!can(n, d, l)) {
            out.println(-1); return;
        }
        int[] res = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; j <= l; j++) {
                int nd = d + j * (i % 2 == 1 ? 1 : -1);
                if (can(i, nd, l)) {
                    d = nd;
                    res[i] = j;
                    break;
                }
            }
        }
        for (int i = 0; i < n; i++) out.printf("%d%c", res[i], i == n - 1 ? '\n' : ' ');
    }

    private boolean can(int n, int d, int l) {
        int np = (n + 1) / 2;
        int nm = n / 2;
        int MP = np * l, mP = np * 1;
        int MM = nm * l, mM = nm * 1;
        int min = mP - MM;
        int max = MP - mM;
        if (d < min || d > max) return false;
        return true;
    }
}
