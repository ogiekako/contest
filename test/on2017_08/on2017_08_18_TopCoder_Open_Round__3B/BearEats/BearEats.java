package on2017_08.on2017_08_18_TopCoder_Open_Round__3B.BearEats;



import net.ogiekako.algorithm.dataStructure.tree.LongRangeMinimumQuery;

import java.util.Arrays;

public class BearEats {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    class E implements Comparable<E> {
        int A, B;

        @Override
        public int compareTo(E o) {
            if (B == o.B) return -(A - o.A);
            return -(B - o.B);
        }
    }

    public long getDifference(int N, int R, int C, int D, int A_MAX, int B_MAX) {
        int MOD = (int) (1e9 + 7);
        int[] A = new int[N];
        int[] B = new int[N];
        E[] es = new E[N];
        for (int i = 0; i < N; i++) {
            R = (int) (((long) C * R + D) % MOD);
            A[i] = (int) ((long) R % A_MAX);
            R = (int) (((long) C * R + D) % MOD);
            B[i] = (int) ((long) R % B_MAX);
            es[i] = new E();
            es[i].A = A[i];
            es[i].B = B[i];
        }
        Arrays.sort(es);
        long res = 0;
        for (int b : B) res -= b;
        int turn = (N + 1) / 2;
        LongRangeMinimumQuery rmq = new LongRangeMinimumQuery(N);
        long MAX = (long) 1e7;
        for (int i = 0; i < N; i++) {
            int v = es[i].A + es[i].B;

            rmq.update(i, -(v * MAX + i));
        }
        for (int i = 0; i < turn; i++) {
            long v = -rmq.query(2 * (turn - 1 - i), N);

            res += v / MAX;
            int id = (int) (v % MAX);
            rmq.update(id, 0);
        }

        return res;
    }
}
