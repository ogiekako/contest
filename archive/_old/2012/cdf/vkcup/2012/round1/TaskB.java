package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.IntegerUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();
        E[] es = new E[n];
        for (int i = 0; i < n; i++) es[i] = new E(in.nextInt(), in.nextInt(), i + 1);
        Arrays.sort(es);
        double res = 0;
        for (int i = 0; i < n; i++) {
            double val = i < k - 1 && es[i].kind == 1 ? es[i].price / 2.0 : es[i].price;
            res += val;
        }
        if (es[k - 1].kind == 1) {
            long min = Integer.MAX_VALUE;
            for (int i = k - 1; i < n; i++) {
                min = Math.min(min, es[i].price);
            }
            res -= min / 2.0;
        }
        out.printf("%.1f\n", res);
        for (int i = 0; i < k - 1; i++) {
            out.println(1 + " " + es[i].index);
        }
        out.print(n - k + 1);
        for (int i = k - 1; i < n; i++) {
            out.print(" " + es[i].index);
        }
        out.println();
    }
    private class E implements Comparable<E> {
        long price;
        int kind;
        int index;

        private E(long price, int kind, int index) {
            this.kind = kind;
            this.price = price;
            this.index = index;
        }

        public int compareTo(E o) {
            if (kind != o.kind) {
                return kind - o.kind;
            } else {
                return -IntegerUtils.compare(price, o.price);
            }
        }

        @Override
        public String toString() {
            return "E{" +
                    "kind=" + kind +
                    ", price=" + price +
                    '}';
        }
    }
}
