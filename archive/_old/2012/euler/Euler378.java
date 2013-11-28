package tmp;

import net.ogiekako.algorithm.dataStructure.tree.IntSumBinaryIndexedTree;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MultiplicativeFunction;
import net.ogiekako.algorithm.utils.IntegerUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class Euler378 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        long[] divCounts = MultiplicativeFunction.DIVISOR_COUNT.calculateUpTo(N + 2);
        Entry[] es = new Entry[N];
        for (int i = 1; i <= N; i++) {
            es[i - 1] = new Entry(calc(i, divCounts), i);
        }
        Arrays.sort(es);
        IntSumBinaryIndexedTree bit = new IntSumBinaryIndexedTree(N + 10);
        long res = 0;
        long MOD = (long) 1e9 * (long) 1e9;
        for (Entry e : es) {
            bit.add(e.id, 1);
            long left = e.id - 1 - bit.sum(0, e.id);
            long right = bit.sum(e.id + 1, N + 10);
            res += left * right;
            if (res >= MOD) res -= MOD;
        }
        out.println(res);
    }

    private long calc(int n, long[] divCounts) {
        if (n % 2 == 0) {
            return divCounts[n / 2] * divCounts[n + 1];
        } else {
            return divCounts[(n + 1) / 2] * divCounts[n];
        }
    }

    class Entry implements Comparable<Entry> {
        long divCount;
        int id;

        Entry(long divCount, int id) {
            this.divCount = divCount;
            this.id = id;
        }

        public int compareTo(Entry o) {
            return IntegerUtils.compare(divCount, o.divCount);
        }
    }
}
