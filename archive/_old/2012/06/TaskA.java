package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskA {
    //    A(0,n)=n+1(n≥0)
//A(m,0)=A(m−1,1)(m≥1)
//A(m,n)=A(m−1,A(m,n−1))(m,n≥1)
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int m = in.nextInt();
        int n = in.nextInt();
        long res = solve(m, n);
        out.println(res);
    }

    private long solve(int m, int n) {
        if (m == 0) return n + 1;
        if (m == 1) return n + 2;
        if (m == 2) return 2 * n + 3;
        return (1L << n + 3) - 3;
    }
}
