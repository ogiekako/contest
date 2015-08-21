package on2015_08.on2015_08_21_TopCoder_Open_Round__2A.TheLargestString;



import java.util.Arrays;
import java.util.Random;

public class TheLargestString {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int n;

    public String find(String s, String t) {
        n = s.length();
        String best = "";
        for (int len = 1; len <= n; len++) {
            String cur = solve(s, t, len);
            if (best.compareTo(cur) < 0) {
                best = cur;
            }
        }
        return best;
    }

    private String solve(String s, String t, int len) {
        E[] dp = new E[len + 1];
        dp[0] = new E("", "");
        for (int i = 0; i < n; i++) {
            E[] nDp = new E[len + 1];
            for (int j = 0; j <= len; j++) {
                if (dp[j] == null) continue;
                if (nDp[j] == null || nDp[j].compareTo(dp[j]) < 0) {
                    nDp[j] = dp[j];
                }
                if (j == len) continue;
                E e = new E(dp[j].a, dp[j].b);
                e.a += s.charAt(i);
                e.b += t.charAt(i);
                if (nDp[j + 1] == null || nDp[j + 1].compareTo(e) < 0) {
                    nDp[j + 1] = e;
                }
            }
            dp = nDp;
        }
        return dp[len].a + dp[len].b;
    }

    class E implements Comparable<E> {
        String a, b;

        @Override
        public int compareTo(E o) {
            int cmp = a.compareTo(o.a);
            return cmp != 0 ? cmp : b.compareTo(o.b);
        }

        public E(String a, String b) {
            this.a = a;
            this.b = b;
        }
    }

    public static void main(String[] args) {
        new TheLargestString().test();
    }

    private void test() {
        Random rnd = new Random(42L);
        for (int i = 0; i < 100; i++) {
            debug(i);
            int n = 47;
            String s = "", t = "";
            for (int j = 0; j < n; j++) {
                s += (char) (rnd.nextInt(3) + 'a');
                t += (char) (rnd.nextInt(3) + 'a');
            }
            debug(s);
            debug(t);
            String res = find(s, t);
            String exp = findNaive(s, t);
            if (!res.equals(exp)) {
                debug(s, t, res, exp);
            }
        }
    }

    private String findNaive(String s, String t) {
        String best = "";
        for (int i = 0; i < 1 << s.length(); i++) {
            String cur = "";
            for (int j = 0; j < s.length(); j++) {
                if (i << 31 - j < 0) {
                    cur += s.charAt(j);
                }
            }
            for (int j = 0; j < t.length(); j++) {
                if (i << 31 - j < 0) {
                    cur += t.charAt(j);
                }
            }
            if (best.compareTo(cur) < 0) {
                best = cur;
            }
        }
        return best;
    }
}
