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
        for (int i = 0; i < n; i++) es[i] = new E(in.nextInt(), in.nextInt());
        Arrays.sort(es);
        System.err.println(Arrays.toString(es));
        double res = 0;
        for (int i = 0; i < n; i++) {
            double val = i < k - 1 && es[i].kind == 1 ? es[i].price / 2.0 : es[i].price;
            res += val;
        }
        if (es[k - 1].kind == 1) {
            long d = Integer.MAX_VALUE;
            for (int i = k - 1; i < n; i++) {
                d = Math.min(d, es[i].price);
            }

        }

        out.printf("%.1f\n", res);
        for (int i = 0; i < k - 1; i++) {
            out.println(1 + " " + (i + 1));
        }
        out.print(n - k);
        for (int i = k - 1; i < n; i++) {
            out.print(" " + (i + 1));
        }
        out.println();
    }
    private class E implements Comparable<E> {
        long price;
        int kind;

        private E(long price, int kind) {
            this.kind = kind;
            this.price = price;
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
