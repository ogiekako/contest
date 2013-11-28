package tmp;

import net.ogiekako.algorithm.dataStructure.segmentTree.SegTree;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskE {
    int MOD = 777777777;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        long[][] w = new long[3][3];
        for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) w[i][j] = in.nextInt();
        long[][][][] matrices = new long[4][4][3][3];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                for (int x = 0; x < 3; x++)
                    for (int y = 0; y < 3; y++) {
                        if (i > 0 && i != x + 1) continue;
                        if (j > 0 && j != y + 1) continue;
                        matrices[i][j][x][y] = w[x][y];
                    }
            }
        final long[][] identity = new long[3][3];
        for (int i = 0; i < 3; i++) identity[i][i] = 1;
        SegTree<long[][]> tree = new SegTree<long[][]>(n - 1) {
            protected long[][] identity() {
                return identity;
            }

            protected long[][] associativeOperation(long[][] first, long[][] second) {
                long[][] res = new long[3][3];
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++) for (int k = 0; k < 3; k++) res[i][j] += first[i][k] * second[k][j];
                for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) res[i][j] %= MOD;
                return res;
            }
        };
        for (int i = 0; i < n - 1; i++) tree.set(i, w);
        int[] a = new int[n];
        for (int i = 0; i < m; i++) {
            int v = in.nextInt() - 1;
            int t = in.nextInt();
            a[v] = t;
            if (v > 0) tree.set(v - 1, matrices[a[v - 1]][a[v]]);
            if (v < n - 1) tree.set(v, matrices[a[v]][a[v + 1]]);
            long[][] mat = tree.convolution(0, n - 1);
            long res = 0;
            for (long[] ls : mat) for (long l : ls) res += l;
            if (n == 1) out.println(a[0] == 0 ? 3 : 1);
            else out.println(res % MOD);
        }
    }
}
