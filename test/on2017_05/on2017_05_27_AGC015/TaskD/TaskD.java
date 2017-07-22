package on2017_05.on2017_05_27_AGC015.TaskD;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Random;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long A = in.nextLong(), B = in.nextLong();
        long res = solve(A, B);
        out.println(res);
    }

    private long solve(long A, long B) {
        if (A == B) return 1;
        if (A == 0) {
            return B + 1;
        }
        if (A > B) throw new RuntimeException();

        long a = Long.highestOneBit(A);
        long b = Long.highestOneBit(B);

        if (a == b) return solve(A^a, B^b);

        long res = b - A;

        long b2 = Long.highestOneBit(B ^ b) << 1;
        if (b2 == 0) return res + b - A + 1;
        A = Math.max(A, b2);
        return res + b - A + b2;
    }
}
