package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.Random;

public class Facebook13QualC {
    /*
    m[0] = a
    m[i] = (b * m[i - 1] + c) % r, 0 < i < k
     */
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int n = in.nextInt(), k = in.nextInt();
        int a = in.nextInt(), b = in.nextInt(), c = in.nextInt(), r = in.nextInt();
        int[] m = new int[k];
        for (int i = 0; i < k; i++) {
            m[i] = i == 0 ? a : (int) (((long) b * m[i - 1] + c) % r);
        }
        debug(testNumber);
        int res = solve(m, n);
        out.println(res);
    }

    private int solve(int[] m, int n) {
        int k = m.length;
        int[] used = new int[k + 1];
        for (int a : m) if (a <= k) used[a]++;
        int ptr = 0;
        int[] order = new int[k + 1];
        for (int i = 0; i <= k; i++) {
            while (ptr <= k && used[ptr] > 0) ptr++;
            int forgotten = i == 0 ? Integer.MAX_VALUE : m[i - 1];
            if (forgotten <= k) used[forgotten]--;
            if (forgotten < ptr && used[forgotten] == 0) {
                used[forgotten]++;
                order[i] = forgotten;
            } else {
                used[ptr]++;
                order[i] = ptr;
            }
        }
        return order[(n - k - 1) % (k + 1)];
    }

    public static void main(String[] args) {
        Facebook13QualC instance = new Facebook13QualC();
        Random rnd = new Random(1204124908L);
        for (int iteration = 0; ; iteration++) {
            System.err.println(iteration);
            int n = rnd.nextInt(1000) + 11, k = rnd.nextInt(10) + 1;
            int r = rnd.nextInt(10) + 1;
            int[] m = new int[k];
            for (int i = 0; i < k; i++) m[i] = rnd.nextInt(r);
            debug(k, n);
            int res = instance.solve(m, n);
            int exp = instance.solveStupid(m, n);
            if (res != exp) {
                throw new AssertionError("" + res + " " + exp);
            }
        }
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private int solveStupid(int[] m, int n) {
        int k = m.length;
        int[] order = new int[n];
        System.arraycopy(m, 0, order, 0, k);
        for (int i = k; i < n; i++) {
            boolean[] used = new boolean[k + 1];
            for (int j = 0; j < k; j++) if (order[i - j - 1] <= k) used[order[i - j - 1]] = true;
            int ptr = 0;
            while (used[ptr]) ptr++;
            order[i] = ptr;
        }
        return order[n - 1];
    }
}
