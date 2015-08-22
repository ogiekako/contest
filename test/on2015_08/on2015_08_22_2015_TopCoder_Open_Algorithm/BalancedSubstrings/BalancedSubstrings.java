package on2015_08.on2015_08_22_2015_TopCoder_Open_Algorithm.BalancedSubstrings;



import java.util.Arrays;
import java.util.Random;

public class BalancedSubstrings {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int countSubstrings(String s) {
        s = "1" + s + "1";
        int n = s.length();
        int m = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') m++;
        }
        int[] x = new int[m];
        m = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') x[m++] = i;
        }
        int res = 0;
        for (int i = 0; i < m - 1; i++) {
            int d = x[i + 1] - x[i] - 1;
            res += d * (d + 1) / 2;
        }
        for (int i = 1; i < n - 1; i++) {
            int l = 0, r = 0;
            for (int j = 0; j < m; j++) {
                if (x[j] <= i) l = j;
            }
            for (int j = m - 1; j >= 0; j--) {
                if (x[j] >= i) r = j;
            }
            int L = (i - x[l]), R = (x[r] - i);
            while (l > 0 && r < m - 1) {
                if (L == R) {
                    res += (x[l] - x[l - 1]) * (x[r + 1] - x[r]);
                }
                if (L <= R) {
                    l--;
                    L += i - x[l];
                } else {
                    r++;
                    R += x[r] - i;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        new BalancedSubstrings().test();
    }

    private void test() {
        int N = 2500;
        Random rnd = new Random(421L);
        String s = "";
        for (int i = 0; i < N; i++) {
            s += rnd.nextInt(2);
        }
//        System.out.println(s);

        N = 50;
        for (int iter = 0; iter < 100; iter++) {
            debug("iter", iter);
            s = "";
            for (int i = 0; i < N; i++) {
                s += rnd.nextInt(2);
                int res = countSubstrings(s);
                int exp = countNaive(s);
                if (res != exp) {
                    debug(s, res, exp);
                    throw new AssertionError();
                }
            }
        }
    }

    private int countNaive(String s) {
        int res = 0;
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                String t = s.substring(j, i);
                boolean ok = false;
                for (int k = 0; k < t.length(); k++) {
                    int d = 0;
                    for (int l = 0; l < t.length(); l++) {
                        if (t.charAt(l) == '1') {
                            d += k - l;
                        }
                    }
                    if (d == 0) {
                        ok = true;
                    }
                }
                if (ok) {
//                    debug(t);
                    res++;
                }
            }
        }
        return res;
    }
}
