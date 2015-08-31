package on2015_08.on2015_08_29_TopCoder_Open_Round__2C.ThreePoints;



import net.ogiekako.algorithm.dataStructure.tree.BinaryIndexedTree;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class ThreePoints {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int N;
    int MX = 300010;

    public long countColoring(int N, int xzero, int xmul, int xadd, int xmod, int yzero, int ymul, int yadd, int ymod) {
        this.N = N;
        int[] xs = f(xzero, xmul, xadd, xmod);
        int[] ys = f(yzero, ymul, yadd, ymod);
        int[] sortX = xs.clone(), sortY = ys.clone();
        Arrays.sort(sortX);
        Arrays.sort(sortY);
        P[] ps = new P[N];
        for (int i = 0; i < N; i++) {
            ps[i] = new P(Arrays.binarySearch(sortX, xs[i]), Arrays.binarySearch(sortY, ys[i]));
        }
        Arrays.sort(ps);
        long[] left = new long[N], right = new long[N];
        BinaryIndexedTree<Integer> bit = new BinaryIndexedTree.INT(MX);
        for (int i = 0; i < ps.length; i++) {
            left[i] = bit.sum(ps[i].y);
            bit.add(ps[i].y, 1);
        }
        long res = 0;
        bit = new BinaryIndexedTree.INT(MX);
        for (int i = ps.length - 1; i >= 0; i--) {
            right[i] = bit.sum(MX - 5 - ps[i].y);
            bit.add(MX - 5 - ps[i].y, 1);
            res += right[i] * (right[i] - 1) / 2;
        }
        for (int i = 0; i < ps.length; i++) {
            res -= left[i] * right[i];
        }
        return res;
    }

    class P implements Comparable<P> {
        int x, y;

        public P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(P o) {
            return x - o.x;
        }
    }

    private int[] f(int xzero, int xmul, int xadd, int xmod) {
        int[] x = new int[N];
        for (int i = 0; i < N; i++) {
            x[i] = i == 0 ? xzero : (int) (((long) x[i - 1] * xmul + xadd) % xmod);
        }
        return x;
    }
}
