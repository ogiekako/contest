package on2013_06.on2013_06_01_Google_Code_Jam_Round_2_2013.B___Many_Prizes;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        long P = in.nextLong();
        out.printFormat("Case #%d: %d %d\n", testNumber, must(N, P), may(N, P));
    }
    private long may(int N, long P) {
        if (P == (1L << N)) return P - 1;
        long R = 0;
        long A = 1L << N, B = 1;
        long res = 0;
        for (; ; ) {
            A /= 2;
            R += B;
            B *= 2;
            if (P <= R) return res;
            res += A;
        }
    }

    private long must(int N, long P) {
        if (P == (1L << N)) return P - 1;
        long R = 0;
        long A = 1L << N, B = 1;
        long res = 0;
        for (; ; ) {
            A /= 2;
            B *= 2;
            R += A;
            if (P <= R) return res;
            res += B;
        }
    }
}
