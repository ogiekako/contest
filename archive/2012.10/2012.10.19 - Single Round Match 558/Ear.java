package tmp;

import java.util.Arrays;

public class Ear {
    public long getCount(String[] _redX, String[] _blueX, String[] _blueY) {
        long[] rX = gen(_redX);
        long[] bX = gen(_blueX);
        long[] bY = gen(_blueY);
        Arrays.sort(rX);
        long res = 0;
        for (int i = 0; i < bX.length; i++)
            for (int j = 0; j < bX.length; j++) {
                long x1 = bX[i], y1 = bY[i], x2 = bX[j], y2 = bY[j];
                if (y1 >= y2) continue;
                // y1 < y2
                double X = (double) (y1 * x2 - y2 * x1) / (y1 - y2) + 1e-9;
                if (X < x1) X = x2 + 1e-9;
                long in = 0, out = 0;
                for (long x : rX) {
                    if (x1 < x) {
                        if (x < X) in++;
                        else out++;
                    }
                }
                debug(i, j, X, in, out);
                long way1 = in * out + out * (out - 1) / 2;

                X = (double) (y1 * x2 - y2 * x1) / (y1 - y2) - 1e-9;
                if (X > x1) X = x2 - 1e-9;
                in = 0; out = 0;
                for (long x : rX) {
                    if (x1 > x) {
                        if (x > X) in++;
                        else out++;
                    }
                }
                debug(i, j, X, in, out);
                long way2 = in * out + out * (out - 1) / 2;
                debug(i, j, way1, way2);
                res += way1 * way2;
            }
        return res;
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private long[] gen(String[] redX) {
        StringBuilder b = new StringBuilder();
        for (String s : redX) b.append(s);
        String[] ss = b.toString().split(" ");
        long[] res = new long[ss.length];
        for (int i = 0; i < res.length; i++) res[i] = Long.valueOf(ss[i]);
        return res;
    }

    public static void main(String[] args) {
        int[] R = new int[300];
        int[] Bx = new int[300];
        int[] By = new int[300];

    }
}
