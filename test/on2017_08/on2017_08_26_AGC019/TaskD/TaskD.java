package on2017_08.on2017_08_26_AGC019.TaskD;



import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class TaskD {
    class E implements Comparable<E> {
        int l, r;

        @Override
        public int compareTo(E o) {
            return l - o.l;
        }

        @Override
        public String toString() {
            return l + " " + r;
        }
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String A = in.next(), B = in.next();
        int n = A.length();
        int m = 0;
        for (char b : B.toCharArray()) {
            if (b == '1') m++;
        }
        if (m == 0) {
            out.println(A.equals(B) ? 0 : -1);
            return;
        }

        B = B + B + B + B + B;
        int[] bs = new int[m * 5];
        m = 0;
        for (int i = 0; i < B.length(); i++) {
            if (B.charAt(i) == '1') {
                bs[m++] = i;
            }
        }
        int res = Integer.MAX_VALUE;
        for (int x = -n; x <= n; x++) {
            ArrayList<E> es = new ArrayList<E>();
            int add = 0;
            for (int i = 0; i < n; i++) {
                if (A.charAt(i) == '1' && B.charAt(2 * n + i + x) == '0') {
                    add++;
                    E e = new E();
                    int k = Arrays.binarySearch(bs, 2 * n + i + x);
                    int l = -k - 2;
                    int r = -k - 1;
                    e.l = 2 * n + i - bs[l];
                    e.r = bs[r] - (2 * n + i);
                    if (bs[l] < Math.min(2 * n + i, 2 * n + i + x) && Math.max(2 * n + i, 2 * n + i + x) < bs[r]) {
                        es.add(e);
                    }
                } else if (A.charAt(i) == '0' && B.charAt(2 * n + i + x) == '1') {
                    add++;
                }
            }
            E zero = new E();
            zero.l = 0;
            zero.r = 0;
            es.add(zero);
            Collections.sort(es, Comparator.reverseOrder());
            int r = 0;
            for (E e : es) {
                int l = e.l;
                res = Math.min(res, add + Math.min(2 * l + r + Math.abs(r - x), 2 * r + l + Math.abs(l + x)));
                r = Math.max(r, e.r);
            }
        }
        out.println(res);
    }
}
