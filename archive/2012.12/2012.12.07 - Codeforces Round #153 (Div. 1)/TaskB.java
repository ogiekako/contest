package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        int[] s = new int[n], q = new int[n];
        for (int i = 0; i < n; i++) q[i] = in.nextInt() - 1;
        for (int i = 0; i < n; i++) s[i] = in.nextInt() - 1;
        int from = -1, to = -1;
        int[] cur = new int[n];
        for (int i = 0; i < n; i++) cur[i] = i;
        for (int i = 0; i < k + 1; i++) {
            if (Arrays.equals(cur, s)) {
                to = i; break;
            }
            cur = ArrayUtils.multiplyReversePermutations(cur, q);
        } cur = new int[n];
        for (int i = 0; i < n; i++) cur[i] = i;
        for (int i = 0; i < k + 1; i++) {
            if (Arrays.equals(cur, s)) {
                from = i; break;
            }
            cur = ArrayUtils.multiplyPermutations(cur, q);
        }
        if (from == -1 && to == -1 || from == 0) {
            out.println("NO"); return;
        }
        if (from == -1) {
            if (k % 2 == to % 2) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        } else if (to == -1) {
            if (k % 2 == from % 2) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        } else {
            if (from == 1 && to == 1 && k != 1) {
                out.println("NO");
            } else if (from % 2 == k % 2 || to % 2 == k % 2) {
                out.println("YES");
            } else {
                out.println("NO");
            }
        }
    }

    class Array {
        int[] is;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Array array = (Array) o;

            if (!Arrays.equals(is, array.is)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return is != null ? Arrays.hashCode(is) : 0;
        }
    }
}
