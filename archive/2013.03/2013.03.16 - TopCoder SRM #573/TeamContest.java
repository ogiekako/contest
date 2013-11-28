package tmp;

import java.util.Arrays;

public class TeamContest {
    public int worstRank(int[] strength) {
        int pnt = calc(strength[0], strength[1], strength[2]);
        int[] a = new int[strength.length - 3];
        for (int i = 0; i < a.length; i++) a[i] = strength[3 + i];
        Arrays.sort(a);
        for (int i = a.length / 3; i >= 0; i--) {
            boolean ok = true;
            int f = a.length - i * 3;
            for (int j = 0; j < i; j++) {
                int p = calc(a[a.length - 1 - j], a[f + 2 * j + 0], a[f + 2 * j + 1]);
                if (p <= pnt) ok = false;
            }
            if (ok)
                return i + 1;
        }
        throw new AssertionError();
    }

    private int calc(int a, int b, int c) {
        return Math.max(a, Math.max(b, c)) + Math.min(a, Math.min(b, c));
    }
}
