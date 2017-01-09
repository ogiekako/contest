package on2016_09.on2016_09_04_AGC004.TaskA;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long[] A = new long[]{in.nextInt(), in.nextInt(), in.nextInt()};
        Arrays.sort(A);
        if (A[0]%2 ==0 || A[1]%2==0 || A[2]%2==0) {
            out.println(0);
            return;
        }
        out.println(A[0] * A[1]);
    }
}
