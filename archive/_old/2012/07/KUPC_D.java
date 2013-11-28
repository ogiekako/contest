package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class KUPC_D {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        long[] a = new long[M];
        for (int i = 0; i < M; i++) {
            a[i] = in.nextLong() << 32 | in.nextInt();
        }
        Arrays.sort(a);
        int max = 0;
        int res = 0;
        for (int i = 0; i < a.length; ) {
            int nmax = -1;
            while (i < M && (int) (a[i] >> 32) <= max + 1) nmax = Math.max(nmax, (int) a[i++]);
            if (nmax == -1) {
                out.println("Impossible"); return;
            }
            res++;
            max = nmax;
            if (max == N) break;
        }
        if (max < N) {
            out.println("Impossible"); return;
        }
        out.println(res);
    }
}
