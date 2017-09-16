package on2017_09.on2017_09_12_Typical_DP_Contest.TaskK;



import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.dataStructure.tree.BinaryIndexedTree;
import net.ogiekako.algorithm.dataStructure.tree.IntRangeMinimumQuery;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskK {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt();
        E[] es = new E[N];
        int[] sortY = new int[N];
        for (int i = 0; i < N; i++) {
            es[i] = new E();
            int x = in.nextInt(), r = in.nextInt();
            es[i].x = r - x;
            es[i].y = x + r;
            sortY[i] = es[i].y;
        }
        Arrays.sort(sortY);
        for (int i = 0; i < N; i++) {
            es[i].y = Arrays.binarySearch(sortY, es[i].y);
        }
        Arrays.sort(es);
        IntRangeMinimumQuery rmq = new IntRangeMinimumQuery(N);
        for (int i = 0; i < N; i++) {
            rmq.update(es[i].y, -1);
            int min = rmq.query(0, es[i].y);
            if (min < Integer.MAX_VALUE) {
                rmq.update(es[i].y, min - 1);
            }
        }
        out.println(-rmq.query(0, N));
    }

    class E implements Comparable<E> {
        int x, y;

        @Override
        public int compareTo(E o) {
            if (x != o.x) return x - o.x;
            return -(y - o.y);
        }
    }
}
