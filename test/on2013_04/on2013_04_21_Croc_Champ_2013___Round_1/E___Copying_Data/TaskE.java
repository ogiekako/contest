package on2013_04.on2013_04_21_Croc_Champ_2013___Round_1.E___Copying_Data;


import net.ogiekako.algorithm.dataStructure.segmentTree.SegTree;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;


public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        SegTree.Sum tree = new SegTree.Sum(n);
        int[] a = new int[n], b = new int[n];
        for (int i = 0; i < n; i++) a[i] = in.nextInt();
        for (int i = 0; i < n; i++) b[i] = in.nextInt();
        int[] x = new int[m];
        int[] y = new int[m];
        for (int i = 0; i < m; i++) {
            if (in.nextInt() == 1) {
                x[i] = in.nextInt() - 1;
                y[i] = in.nextInt() - 1;
                int k = in.nextInt();
                tree.set(y[i], y[i] + k, i + 1);
            } else {
                int j = in.nextInt() - 1;
                int p = (int) (long) tree.convolution(j, j + 1);
                if (p == 0) out.println(b[j]);
                else {
                    p--;
                    out.println(a[x[p] + (j - y[p])]);
                }
            }
        }
    }
}
