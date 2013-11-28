package tmp;

import net.ogiekako.algorithm.dataStructure.segmentTree.SegTree;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.Cast;
import net.ogiekako.algorithm.utils.Pair;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class ARC008D {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        in.nextLong();
        int M = in.nextInt();
        long[] is = new long[M];
        double[] as = new double[M], bs = new double[M];
        TreeSet<Long> set = new TreeSet<Long>();
        for (int i = 0; i < M; i++) {
            is[i] = in.nextLong();
            as[i] = in.nextDouble(); bs[i] = in.nextDouble();
            set.add(is[i]);
        }
        int N = set.size();
        SegTree<Pair<Double, Double>> seg = new SegTree<Pair<Double, Double>>(N) {
            @Override
            protected Pair<Double, Double> identity() {
                return new Pair<Double, Double>(1.0, 0.0);
            }

            /*
           (c d) (a b)   (ca cb + d)
           (0 1) (0 1) = (0  1     )
            */
            @Override
            protected Pair<Double, Double> associativeOperation(Pair<Double, Double> first, Pair<Double, Double> second) {
                double a = first.first, b = first.second;
                double c = second.first, d = second.second;
                return new Pair<Double, Double>(c * a, c * b + d);
            }
        };
        long[] appeared = Cast.toLong(set);
        double min = 1, max = 1;
        for (int i = 0; i < M; i++) {
            int p = Arrays.binarySearch(appeared, is[i]);
            seg.set(p, new Pair<Double, Double>(as[i], bs[i]));
            Pair<Double, Double> ab = seg.convolution(0, N);
            double value = ab.first + ab.second;
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        out.printf("%.6f\n%.6f\n", min, max);
    }

}
