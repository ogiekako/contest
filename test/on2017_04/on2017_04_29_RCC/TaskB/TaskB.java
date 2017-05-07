package on2017_04.on2017_04_29_RCC.TaskB;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskB {
    class Hero implements Comparable<Hero> {
        long h,a,b;

        public Hero(long h, long a, long b) {
            this.h=h;this.a=a;this.b=b;
        }

        @Override
        public int compareTo(Hero o) {
            return Long.compare(b, o.b);
        }
    }
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        long H = in.nextLong(), A = in.nextLong();
        long[] h = new long[n];
        long[] a = new long[n];
        long[] b = new long[n];
        Hero[] hs = new Hero[n];
        for (int i = 0; i < n; i++) {
            h[i] = in.nextInt();
            a[i] = in.nextInt();

            b[i] = (h[i] + A - 1) / A * a[i];

            hs[i] = new Hero(h[i], a[i], b[i]);
        }
        Arrays.sort(hs);

        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += hs[i].b;
        }
        if (sum < H) {
            out.println(-1);
            return;
        }
        int best = n - 1;
        for (int i = 0; i < n; i++) {
            sum -= hs[i].b;
            if (sum  >= H) {
                best = Math.min(best, n - 2 - i);
            }
        }
        out.println(best);
    }
}
