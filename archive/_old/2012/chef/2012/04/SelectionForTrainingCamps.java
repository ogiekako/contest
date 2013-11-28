package tmp;

import net.ogiekako.algorithm.dataStructure.tree.RmqBit;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class SelectionForTrainingCamps {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] X = new int[N];
        int[] Y = new int[N];
        for (int i = 0; i < N; i++) {
            X[i] = in.nextInt();
            Y[i] = in.nextInt();
        }
        int[] res = solve(X, Y);
        out.println(res.length);
        for (int i = 0; i < res.length; i++) {
            if (i > 0) out.print(" ");
            out.print(res[i]);
        }
        out.println();
    }
    public int[] solve(int[] X, int[] Y) {
        X = X.clone();
        Y = Y.clone();
        int N = X.length;
        for (int i = 0; i < N; i++) X[i] = -X[i];
        for (int i = 0; i < N; i++) Y[i] = -Y[i];
        ArrayUtils.compress(X);
        ArrayUtils.compress(Y);
        P[] ps = new P[N];
        for (int i = 0; i < N; i++) ps[i] = new P(X[i], Y[i]);
        Arrays.sort(ps);

        RmqBit rmq = new RmqBit(N + 1);
        int maxRank = 0;
        for (P p : ps) {
            p.rank = (int) (-rmq.getMin(p.y + 1) + 1);
            rmq.update(p.y, -p.rank);
            maxRank = Math.max(maxRank, p.rank);
        }
        int[] res = new int[maxRank];
        for (P p : ps) res[p.rank - 1]++;
        return res;
    }
    class P implements Comparable<P> {
        int x, y;
        int rank;
        P(int x, int y) {
            this.x = x; this.y = y;
        }

        public int compareTo(P o) {
            if (x != o.x) return x - o.x;
            return y - o.y;
        }
    }
}
