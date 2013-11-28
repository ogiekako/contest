package tmp;

import net.ogiekako.algorithm.geometry.Point;
import net.ogiekako.algorithm.geometry.Polygon_methods;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class G2 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long[] X = new long[N], Y = new long[N];
        for (int i = 0; i < N; i++) {
            X[i] = in.nextLong(); Y[i] = in.nextLong();
        }
        int res = 0;
        for (int i = 0; i < 1 << N; i++) {
            if (Integer.bitCount(i) == 5) {
                Point[] ps = new Point[5];
                int p = 0;
                for (int j = 0; j < N; j++)
                    if (i << 31 - j < 0) {
                        ps[p++] = new Point(X[j], Y[j]);
                    }
                ps = Polygon_methods.convexHull(ps);
                if (ps.length == 5) {
                    res++;
                }
            }
        }
        out.println(res);
    }
}
