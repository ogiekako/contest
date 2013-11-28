package tmp;

import java.util.ArrayList;
import java.util.List;

public class TheFrog {
    double EPS = 1e-9;
    public double getMinimum(int D, int[] L, int[] R) {
        int maxLen = 0;
        for (int i = 0; i < L.length; i++) maxLen = Math.max(maxLen, R[i] - L[i]);
        List<Integer> set = new ArrayList<Integer>();
        set.add(D);
        for (int l : L) set.add(l);
        for (int r : R) set.add(r);
        double res = Integer.MAX_VALUE;
        for (int s : set) {
            for (int i = 1; ; i++) {
                double d = (double) s / i;
                if (d + EPS < maxLen) break;
                if (valid(d, L, R)) res = Math.min(res, d);
            }
        }
        return res;
    }

    private boolean valid(double d, int[] L, int[] R) {
        for (int i = 0; i < L.length; i++) {
            int s = (int) (L[i] / d);
            while (d * s + d < L[i] + EPS) s++;
            s++;
            if (s * d < R[i] - EPS) return false;
        }
        return true;
    }
}
