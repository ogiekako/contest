package on2017_04.on2017_04_29_RCC.Task;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class Task {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long n = in.nextInt();
        debug(testNumber, n);
        long k = 1;
        for(int i=1;;i++) {
            k *= 26;
            if (k >= n) {
                String res = solve(n-1, i);
                out.println(res);
                return;
            } else {
                n-=k;
            }
        }
    }

    private String solve(long n, int len) {
        String res = "";
        for(int i=0;i<len;i++) {
            res = ((char)('A' + n % 26)) + res;
            n /= 26;
        }
        return res;
    }

    void debug(Object...os) {
//        System.err.println(Arrays.deepToString(os));
    }
}
