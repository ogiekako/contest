package tmp;

import net.ogiekako.algorithm.math.HighlyCompositeNumber;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler110 {
    public void solve(int testNumber, Scanner in, final PrintWriter out) {
        final long M = in.nextLong();
        HighlyCompositeNumber.iterateOverAllCandidateOfHighlyCompositeNumber(50, new HighlyCompositeNumber.Function() {
            public boolean found(HighlyCompositeNumber.Entry entry) {
                long numSolution = 1;
                for (int e : entry.exponents) numSolution *= e * 2 + 1;
                if ((numSolution + 1) / 2 >= M) {
                    out.println(entry.number);
                    return true;
                }
                return false;
            }
        });
    }
}
