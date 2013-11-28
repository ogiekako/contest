package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class E {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] left = new int[N];
        int[] right = new int[N];
        for (int i = 0; i < N - 1; i++) {
            left[i] = in.nextInt() - 1;
            right[i] = in.nextInt() - 1;
        }
        long[] l = new long[N];
        long[] d = new long[N];
        long[] a = new long[N];
        long[] b = new long[N];
        for (int i = 0; i < N - 1; i++) {
            d[left[i]] = d[i] + 1;
            d[right[i]] = d[i] + 1;
        }
        for (int i = N - 1; i >= 0; i--) {
            l[i] = i == N - 1 ? 1 : l[left[i]] + l[right[i]];
            a[i] = i == N - 1 ? 0 : a[left[i]] + a[right[i]] + l[i];
            b[i] = i == N - 1 ? 0 : Math.min(Math.min(
                    a[left[i]] + l[left[i]] + 1 + b[right[i]],
                    a[right[i]] + l[right[i]] + 1 + b[left[i]]),
                    b[left[i]] + 1 + d[i] + 1 + b[right[i]]
            );
        }
        out.println(b[0]);
    }
}
