package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.CombinationSystem;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class Euler364 {
    int MODULUS = 100000007;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long res = 0;
        CombinationSystem combinationSystem = new CombinationSystem(N + 10, MODULUS);
        for (int left = 0; left < 2; left++)
            for (int right = 0; right < 2; right++) {
                int n = N - 1 - left - right;
                for (int m = 0; m < n; m++) {
                    int a = 3 * m + 3 - n;
                    int b = m + 1 - a;
                    if (0 <= a && a <= m + 1) {
                        long value =
                                combinationSystem.choose(m + 1, a) *
                                        combinationSystem.facts[m + 2] % MODULUS *
                                        MathUtils.powMod(2, b, MODULUS) % MODULUS *
                                        combinationSystem.facts[b + left + right] % MODULUS *
                                        combinationSystem.facts[a + b] % MODULUS;
                        res += value;
                        if (res >= MODULUS) res -= MODULUS;
                    }
                }
            }
        out.println(res);
    }
}
