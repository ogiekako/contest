package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class H {
    int MOD = 314159265;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), D = in.nextInt();
        int[] left = new int[N], right = new int[N];
        for (int i = 0; i < N; i++) {
            left[i] = in.nextInt(); right[i] = in.nextInt();
        }
        int res = solve(N, D, left, right);
        out.println(res);
    }

    private int solve(int N, int D, int[] left, int[] right) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int l : left) set.add(l); for (int r : right) set.add(r);
        int[] xs = tois(set.toArray(new Integer[0]));
        for (int i = 0; i < left.length; i++) left[i] = Arrays.binarySearch(xs, left[i]);
        for (int i = 0; i < right.length; i++) right[i] = Arrays.binarySearch(xs, right[i]);
        long[] ls = new long[N];
        for (int i = 0; i < N; i++) ls[i] = (long) left[i] << 32 | right[i];
        ArrayUtils.sort(ls);
        for (int i = 0; i < N; i++) left[i] = (int) (ls[i] >> 32);
        for (int i = 0; i < N; i++) right[i] = (int) ls[i];

        BIT[] bits = new BIT[D + 1];
        int INF = N * 2 + 5;
        for (int i = 0; i < bits.length; i++) bits[i] = new BIT(N * 2 + 10);
        bits[0].add(INF - 1, 1);
        for (int i = 0; i < N; i++) {
            for (int d = D - 1; d >= 0; d--) {
                long way = bits[d].sum(right[i], INF);
                bits[d + 1].add(right[i], way);
            }
        }
        return (int) bits[D].sum(0, N * 2 + 10);
    }

    public class BIT {
        long[] is;
        public BIT(int n) {
            is = new long[n + 1];
        }
        public long sum(int s, int t) {// [s,t)
            if (s > 0) {
                long res = sum(0, t) - sum(0, s);
                if (res < 0) res += MOD;
                return res;
            }
            long res = 0;
            for (int i = t; i > 0; i -= i & -i) {
                res += is[i];
                if (res >= MOD) res -= MOD;
            }
            return res;
        }

        public void add(int id, long val) {
            for (int i = id + 1; i < is.length; i += i & -i) {
                is[i] += val;
                if (is[i] >= MOD) is[i] -= MOD;
            }
        }

        public void set(int id, long val) {
            val -= sum(id, id + 1);
            if (val < 0) val += MOD;
            add(id, val);
        }
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }
}
