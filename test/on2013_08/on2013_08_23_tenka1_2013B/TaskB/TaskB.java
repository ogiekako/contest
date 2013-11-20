package on2013_08.on2013_08_23_tenka1_2013B.TaskB;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Stack;

public class TaskB {

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int Q = in.nextInt();
        long L = in.nextInt();
        Stack<Long> stk = new Stack<Long>();
        long size = 0;
        for (int i = 0; i < Q; i++) {
            String s = in.next();
            if (s.equals("Push")) {
                long N = in.nextInt(), M = in.nextInt() + (int) 1e9;
                size += N;
                if (size > L) {
                    out.println("FULL"); return;
                }
                stk.push(N << 32 | M);
            } else if (s.equals("Pop")) {
                int N = in.nextInt();
                size -= N;
                if (size < 0) {
                    out.println("EMPTY"); return;
                }
                while (N > 0) {
                    long l = stk.pop();
                    long n = l >> 32;
                    long m = (int) (l & (1L << 32) - 1);
                    if (n <= N) {
                        N -= n;
                    } else {
                        n -= N;
                        N = 0;
                        stk.push(n << 32 | m);
                    }
                }
            } else if (s.charAt(0) == 'T') {
                if (size == 0) {
                    out.println("EMPTY"); return;
                }
                long l = stk.peek();
                long m = (int) (l & (1L << 32) - 1);
                out.println(m - (int) 1e9);
            } else {
                out.println(size);
            }
        }
        out.println("SAFE");
    }
}
