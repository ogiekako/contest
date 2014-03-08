package on2014_03.on2014_03_04_Single_Round_Match_611.Egalitarianism2;



import net.ogiekako.algorithm.dataStructure.UnionFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class Egalitarianism2 {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    double[][] D;
    List<E> dists;

    public double minStdev(int[] x, int[] y) {
        int n = x.length;
        D = new double[n][n];
        dists = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                D[i][j] = D[j][i] = dist(x, y, i, j);
                dists.add(new E(i, j, D[i][j]));
            }
        Collections.sort(dists);
//        debug(dists);
        double res = Double.POSITIVE_INFINITY;
        for (int i = 0; i < dists.size(); i++) {
            for (int j = 0; j < i; j++) {
                double d1 = dists.get(j).d;
                double d2 = dists.get(i).d;
                double mid = (d1 + d2) / 2;
                res = Math.min(res, calc(x, y, mid - 1e-7));
                res = Math.min(res, calc(x, y, mid + 1e-7));
            }
        }
        return res;
    }
    private double calc(int[] x, int[] y, double ave) {
        int n = x.length;
        List<E> dists = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) dists.add(new E(i, j, Math.abs(D[i][j] - ave)));
        Collections.sort(dists);
        UnionFind uf = new UnionFind(n);
        double[] a = new double[n - 1];
        int m = 0;
        for (E e : dists) {
            if (uf.find(e.i, e.j)) continue;
            uf.union(e.i, e.j);
            a[m++] = D[e.i][e.j];
        }
        if (m != n - 1) throw new AssertionError();
        ave = 0;
        for (double b : a) ave += b;
        ave /= (n - 1);
        double c = 0;
        for (double b : a) c += (ave - b) * (ave - b);
//        if (c < 0) c = 0;
        double res = Math.sqrt(c / (n - 1));

        if (Double.isNaN(res)) {
            debug(a, res);
        }
        return res;
    }
    private double dist(int[] x, int[] y, int i, int j) {
        double d2 = (long) (x[i] - x[j]) * (x[i] - x[j]) + (long) (y[i] - y[j]) * (y[i] - y[j]);
        if (d2 < 0) throw new AssertionError();
        return Math.sqrt(d2);
    }

    class E implements Comparable<E> {
        int i, j;
        double d;
        E(int i, int j, double d) {
            this.i = i;
            this.j = j;
            this.d = d;
        }
        @Override
        public int compareTo(E o) {
            return Double.compare(d, o.d);
        }
        @Override
        public String toString() {
            return "E{" +
                    "i=" + i +
                    ", j=" + j +
                    ", d=" + d +
                    '}';
        }
    }
}
