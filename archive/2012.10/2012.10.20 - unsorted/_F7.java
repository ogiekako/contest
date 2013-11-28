package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;

public class _F7 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] as = new int[N];
        for (int i = 0; i < N; i++) as[i] = in.nextInt();
        ArrayUtils.sort(as);
        int left = -1, right = N;
        do {
            int mid = (right + left) / 2;
            int point = as[mid] + N;
            int p = N - 1;
            boolean ok = true;
            for (int i = 0; i < N; i++)
                if (i != mid) {
                    int cur = as[i] + p--;
                    if (cur > point) ok = false;
                }
            if (ok) right = mid;
            else left = mid;
        } while (right - left > 1);
        out.println(N - right);
    }
}
