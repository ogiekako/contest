package tmp;

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.PrimeDecomposition;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler174 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int max = in.nextInt();
        int from = in.nextInt(), to = in.nextInt();
        max /= 4;
        int[] ds = MathUtils.generateDivisorTable(max);
        long res = 0;
        for (int i = 1; i <= max; i++) {
            PrimeDecomposition f = PrimeDecomposition.factorize(i, ds);
            long divisorCount = f.divisorCount();
            if (from <= divisorCount / 2 && divisorCount / 2 <= to) {
                res++;
            }
        }
        out.println(res);
    }
}
