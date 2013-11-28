package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.Fibonacci;

import java.io.PrintWriter;

public class Euler297 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long N = in.nextLong();
        long res = Fibonacci.sumOfTermCountOverAllZeckendorfRepresentationLessThan(N);
        out.println(res);
    }
}
