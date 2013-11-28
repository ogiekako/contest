package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class KONCERT {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        boolean[] guy = gen(in);
        boolean[] girl = gen(in);
        for (int i = 0, j = 0; ; ) {
            while (i < guy.length && guy[i]) i++;
            while (j < girl.length && !girl[j]) j++;
            if (i < guy.length && j < girl.length) {
                girl[j] = false; guy[i] = true;
                out.printf("GIVE GIRL %d GUY %d\n", j + 1, i + 1);
            } else break;
        }
        for (int i = 0; i < guy.length; i++) if (guy[i]) out.printf("ENTER GUY %d\n", i + 1);
    }

    private boolean[] gen(MyScanner in) {
        int N = in.nextInt(), n = in.nextInt();
        boolean[] bs = new boolean[N];
        for (int i = 0; i < n; i++) bs[in.nextInt() - 1] = true;
        return bs;
    }
}
