package tmp;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.PrimeDecomposition;
import net.ogiekako.algorithm.math.TotientFuction;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler129 {
    int[] divTable;
    int[] tot;
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int MX = 10000000;
        long start = System.currentTimeMillis();
        divTable = MathUtils.generateDivisorTable(MX);
        tot = TotientFuction.generateTotientTable(MX);
        int N = in.nextInt();
        for (int i = 1; ; i++)
            if (MathUtils.gcd(10, i) == 1) {
                int r = solve(i);
                if (r > N) {
                    out.println(i);
                    return;
                }
            }
    }
    private int solve(int mod) {
        if (mod == 1) return 1;
        int K = tot[mod * 9];
        PrimeDecomposition pf = PrimeDecomposition.factorize(K, divTable);
        for (long k : pf.divisors()) {
            if (K % k == 0) {
                long t = MathUtils.powMod(10, k, mod * 9);
                if (t == 1) return (int) k;
            }
        }
        throw new RuntimeException();
    }
}
