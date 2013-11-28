package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long L = in.nextInt();
        double v1 = in.nextInt(), v2 = in.nextInt();
        double t = L / (v1 + v2);
        double len = t * v2;
        System.err.println(len);
        long[] _as = new long[n];
        for (int i = 0; i < n; i++) _as[i] = in.nextLong();
        {
            long min = Long.MAX_VALUE;
            for (long a : _as) min = Math.min(a, min);
            for (int i = 0; i < n; i++) _as[i] -= min;
        }
        Arrays.sort(_as);
        long[] as = new long[n * 2 + 1];
        for (int i = 0; i < n; i++) as[i] = _as[i];
        for (int i = 0; i < n; i++) as[i + n] = 2 * L + as[i];
        as[n * 2] = 4 * L;
        double[] res = new double[n + 1];
        int j = 0;
        while (as[j] < len) j++;
        double from = 0;
        for (int i = 0; ; ) {
            double d1 = Math.min(2 * L, as[i + 1]) - from;
            double d2 = Math.min(2 * L, as[j] - len) - from;
            int cnt = j - i - 1;
            if (d2 >= d1) {
                res[cnt] += d1;
                from += d1;
                i++;
            } else {
                res[cnt] += d2;
                from += d2;
                j++;
            }
            if (i == n || from >= 2 * L - 1e-9) break;
        }
        for (int i = 0; i < n + 1; i++) res[i] /= 2 * L;
        for (double d : res) {
            out.printf("%.18f\n", d);
        }
    }
}
