package on2012_7_5.taska;



import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskA {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long[] fib = new long[n+1];
        for (int i = 0; i < n + 1; i++) fib[i] = i<=1 ? 1 : fib[i-2]+fib[i-1];
        out.println(fib[n]);
    }
}
