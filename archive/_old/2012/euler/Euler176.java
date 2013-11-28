package tmp;

import net.ogiekako.algorithm.math.HighlyCompositeNumber;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler176 {
    public void solve(int testNumber, Scanner in, final PrintWriter out) {
        final int N = in.nextInt();
        HighlyCompositeNumber.iterateOverAllCandidateOfHighlyCompositeNumber(1000, new HighlyCompositeNumber.Function() {
            public boolean found(HighlyCompositeNumber.Entry entry) {
                long cnt1 = 1;
                for (int i = 1; i < entry.exponents.length; i++) {
                    cnt1 *= 1 + 2 * entry.exponents[i];
                }
                cnt1--;
                cnt1 /= 2;

                long cnt2 = 0;
                if (entry.exponents.length > 0 && entry.exponents[0] >= 2) {
                    cnt2 = 1;
                    cnt2 *= entry.exponents[0] - 1;
                    for (int i = 1; i < entry.exponents.length; i++) {
                        cnt2 *= (1 + 2 * entry.exponents[i]);
                    }
                }

                long sum = cnt1 + cnt2;
                if (sum == N) {
                    out.println(entry.number);
                    return true;
                }
                return false;
            }
        });
    }
}
