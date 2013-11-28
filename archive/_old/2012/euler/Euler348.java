package tmp;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.string.Palindrome;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler348 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long[] ps = Palindrome.generatePalindromicNumbers(1000000000L);
        long res = 0;
        int cnt = 0;
        for (long p : ps) {
            int way = 0;
            for (long z = 2; z * z * z <= p; z++) {
                long xx = p - z * z * z;
                if (xx > 3 && MathUtils.isSquare(xx)) {
                    way++;
                }
            }
            if (way == 4) {
                cnt++;
                res += p;
                if (cnt == 5) {
                    out.println(res);
                    return;
                }
            }
        }
    }

}
