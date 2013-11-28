package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.PrimeDecomposition;
import net.ogiekako.algorithm.math.PrimePower;

import java.io.PrintWriter;

public class CarefulCalculation {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        PrimeDecomposition pf = new PrimeDecomposition();
        for (int i = 0; i < n; i++) {
            PrimePower f = new PrimePower(in.nextInt(), in.nextInt());
            pf.add(f);
        }
        long res = pf.totientChainLength();
        out.println(res);
    }
}
