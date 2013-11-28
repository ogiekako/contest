package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class ARC005A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String[] Ts = {"TAKAHASHIKUN", "Takahashikun", "takahashikun"};
        int N = in.nextInt();
        int res = 0;
        for (int i = 0; i < N; i++) {
            String s = in.next();
            for (String T : Ts) {
                if (T.equals(s) || (T + ".").equals(s)) res++;
            }
        }
        out.println(res);
    }
}
