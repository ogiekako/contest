package src;

import java.util.Arrays;
import java.util.Random;
public class FoxAndFencing {
    public String WhoCanWin(int m1, int m2, int r1, int r2, int d) {
        int res = solve(m1, m2, r1, r2, d);
        return res == 1 ? "Ciel" : res == 0 ? "Draw" : "Liss";
    }
    private int solve(int m1, int m2, int r1, int r2, int d) {
        if (m1 + r1 >= d) {
            return 1;
        } else if (d + m1 <= m2 + r2) {
            return -1;
        }
        if (m1 == m2) {
            return 0;
        }
        if (m1 > m2) {
            return -solve(m2, m1, r2, r1, (int) 4e8);
        }
        if (d == m1 + r1 + 1) {
            return 0;
        }
        return solve(m1, m2, r1, r2, m1 + r1 + 1);
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
        Random rnd = new Random(12401248L);
        for (int o = 0; o < 10000; o++) {
            int M = (int) 4;
            int m1 = rnd.nextInt(M)+1, m2 = rnd.nextInt(M)+1;
            int r1 = rnd.nextInt(M)+1, r2 = rnd.nextInt(M)+1;
            int d = rnd.nextInt(M) + 1;
            String exp = new FoxAndFencing().WhoCanWin(m1, m2, r1, r2, d);
            String res = new FoxAndFencing().WhoCanWin2(m1, m2, r1, r2, d);
            if (!exp.equals(res)) {
                debug(m1, m2, r1, r2, d);
                throw new AssertionError();
            }
        }
    }
    private String WhoCanWin2(int m1, int m2, int r1, int r2, int d) {
        int res = solve2(m1, m2, r1, r2, d);
        return res == 1 ? "Ciel" : res == 0 ? "Draw" : "Liss";
    }
    private int solve2(int m1, int m2, int r1, int r2, int d) {
        if (m1 + r1 >= d) return 1;
        if (m2 + r2 >= m1 + d) return -1;
        if (m1 > m2 && m1 + r1 > 2 * m2 + r2) return 1;
        if (m2 > m1 && m2 + r2 > 2 * m1 + r1) return -1;
        return 0;
    }
}
