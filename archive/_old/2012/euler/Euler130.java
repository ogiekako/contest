package tmp;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.PrimeDecomposition;
import net.ogiekako.algorithm.math.TotientFuction;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler130 {
    int MX = 10000000;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        long res = 0;
        int cnt = 0;
        int[] divTable = MathUtils.generateDivisorTable(MX);
        int[] totTable = TotientFuction.generateTotientTable(divTable);
        for (int i = 2; ; i++)
            if (MathUtils.gcd(i, 10) == 1) {
                if (divTable[i] < i) {
                    int K = totTable[i];
                    PrimeDecomposition pf = PrimeDecomposition.factorize(K, divTable);
                    for (long k : pf.divisors()) {
                        if (MathUtils.powMod(10, k, 9 * i) == 1) {
                            if ((i - 1) % k == 0) {
                                cnt++;
                                res += i;
                                if (cnt == N) {
                                    out.println(res);
                                    return;
                                }
                            }
                            break;
                        }
                    }
                }
            }
    }
}
