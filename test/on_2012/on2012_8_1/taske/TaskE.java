package on_2012.on2012_8_1.taske;


import net.ogiekako.algorithm.dataStructure.tree.IntSumBinaryIndexedTree;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class TaskE {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long k = in.nextLong();
        int[] as = new int[n];
        for (int i = 0; i < n; i++) as[i] = in.nextInt();
        long res = solve(n, k, as);
        out.println(res);
    }

    public long solve(int n, long k, int[] as) {
        ArrayUtils.compress(as);
        IntSumBinaryIndexedTree left = new IntSumBinaryIndexedTree(n + 10);
        IntSumBinaryIndexedTree right = new IntSumBinaryIndexedTree(n + 10);
        long allInv = 0;
        for (int i = 0; i < n; i++) {
            long inv = right.sum(as[i] + 1, n + 5);
            allInv += inv;
            right.add(as[i], 1);
        }
        if (allInv <= k) {
            return (long) n * (n - 1) / 2;
        }
        int L = 0, R = 1;
        left.add(as[0], 1); right.add(as[0], -1);
        long res = 0;
//        debug("allInv",allInv);
        while (L < n) {
            while (R == L || R < n && allInv > k) {
                allInv -= left.sum(as[R] + 1, n + 5) + right.sum(0, as[R]);
                right.add(as[R], -1);
                R++;
            }
//            debug(L,R,allInv);
            res += R - L - 1;
            L++;
            if (L < n) {
                allInv += left.sum(as[L] + 1, n + 5) + right.sum(0, as[L]);
                left.add(as[L], 1);
            }
        }
        return (long) n * (n - 1) / 2 - res;
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
        TaskE instance = new TaskE();
        Random rnd = new Random(1012084L);
        for (; ; ) {
            System.out.print(".");
            int n = 100000;
            long k = rnd.nextInt(1000);
            int[] as = new int[n];
            for (int i = 0; i < n; i++) as[i] = rnd.nextInt(100);
            long res = instance.solve(n, k, as);
            System.err.println(res);
//            long exp = instance.solveStupid(n,k,as);
//            Assert.assertEquals(exp,res);
        }

    }

    private long solveStupid(int n, long k, int[] as) {
        long res = 0;
        for (int L = 0; L < n; L++)
            for (int R = L + 1; R < n; R++) {
                int[] bs = new int[L + 1 + n - R];
                for (int i = 0; i < L + 1; i++) bs[i] = as[i];
                for (int i = R; i < n; i++) bs[L + 1 + i - R] = as[i];
                if (k >= count(bs)) res++;
            }
        return res;
    }

    private long count(int[] bs) {
        int res = 0;
        for (int i = 0; i < bs.length; i++) for (int j = i + 1; j < bs.length; j++) if (bs[i] > bs[j]) res++;
        return res;
    }
}
