package on2017_09.on2017_09_10_2017_TopCoder_Open_Algorithm.ChromosomalCrossover;



import java.util.Arrays;
import java.util.Random;

public class ChromosomalCrossover {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maximalLength(String A, String B) {
        int n = A.length();
        int res = 0;
        for (int len = 1; len < n + 1; len++) {
            for (int i = 0; i + len <= n; i++) {
                for (int j = i; j + len <= n; j++) {
                    int k = j - i;
                    boolean ok;
                    if (k == 0) {
                        if (A.substring(i, i + len).equals(B.substring(j, j + len))) {
                            ok = true;
                        } else {
                            ok = false;
                        }
                    } else {
                        ok = true;
                        for (int l = 0; l < k; l++) {
                            boolean ok2 = false;
                            for (int d = 0; d < 2; d++) {
                                char c;
                                if (d == 0) {
                                    c = A.charAt(i + l);
                                } else {
                                    c = B.charAt(i + l);
                                }
                                boolean ok3 = true;
                                for (int m = l; m < len; m += k) {
                                    if (A.charAt(j + m) == c) {
                                        c = B.charAt(j + m);
                                    } else if (B.charAt(j + m) == c) {
                                        c = A.charAt(j + m);
                                    } else {
                                        ok3 = false;
                                    }
                                }
                                if (ok3) {
                                    ok2 = true;
                                }
                            }
                            if (!ok2) {
                                ok = false;
                            }
                        }
                    }
                    if (ok) {
                        res = Math.max(res, len);
                    }
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(1203981029);
        for (int iter = 0; iter < 1000; iter++) {
            debug(iter);
            int n = rnd.nextInt(10) + 1;
            String A = "";
            String B = "";
            for (int i = 0; i < n; i++) {
                A += (char) ('A' + rnd.nextInt(4));
                B += (char) ('A' + rnd.nextInt(4));
            }
            int res = new ChromosomalCrossover().maximalLength(A, B);
            int exp = 0;
            for (int i = 0; i < 1 << n; i++) {
                String C = "";
                String D = "";
                for (int j = 0; j < n; j++) {
                    if (i << 31 - j < 0) {
                        C += A.charAt(j);
                        D += B.charAt(j);
                    } else {
                        C += B.charAt(j);
                        D += A.charAt(j);
                    }
                }
                for (int len = 1; len <= n; len++) {
                    for (int j = 0; j + len <= n; j++) {
                        for (int k = 0; k + len <= n; k++) {
                            if (C.substring(j, j + len).equals(D.substring(k, k + len))) {
                                exp = Math.max(exp, len);
                            }
                        }
                    }
                }
            }
            if (res != exp) {
                debug(A, B, res, exp);
            }
        }
    }
}
