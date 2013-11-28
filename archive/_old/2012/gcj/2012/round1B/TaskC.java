package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.misc.syakutori.Syakutori;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.IntegerUtils;
import net.ogiekako.algorithm.utils.Pair;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        System.err.println("test " + testNumber);
        int N = in.nextInt();
        long[] S = new long[N];
        for (int i = 0; i < N; i++) S[i] = in.nextLong();
        S = ArrayUtils.shuffledArray(S);
        Pair<long[], long[]> pair = solve(S);
        out.printf("Case #%d:\n", testNumber);
        for (long s : pair.first) out.print(s + " ");
        out.println();
        for (long s : pair.second) out.print(s + " ");
        out.println();
    }

    int X = 21;

    Pair<long[], long[]> solve(long[] S) {
        if (S.length < X * 2) X = 10;
        for (int iter = 1; ; iter++) {
            System.err.println("iter " + iter);
            ArrayUtils.shuffle(S);
            long[] A = ArrayUtils.subArray(S, 0, X);
            long[] B = ArrayUtils.subArray(S, X, X + X);
            long[] sumA = func(A);
            long[] sumB = func(B);
            long[] sortedA = sumA.clone();
            long[] sortedB = sumB.clone();
            Arrays.sort(sortedA);
            Arrays.sort(sortedB);
            Pair<Integer, Integer> pair = Syakutori.nearest(sortedA, sortedB, 1, 1);
            if (sortedA[pair.first] == sortedB[pair.second]) {
                int msk = ArrayUtils.indexOf(sumA, sortedA[pair.first], 1);
                long[] resA = ArrayUtils.masked(A, msk);
                msk = ArrayUtils.indexOf(sumB, sortedB[pair.second], 1);
                long[] resB = ArrayUtils.masked(B, msk);
                return new Pair<long[], long[]>(resA, resB);
            }
        }

    }

    private long[] func(long[] A) {
        long[] res = new long[1 << A.length];
        for (int i = 1; i < 1 << A.length; i++) {
            int l = Integer.lowestOneBit(i);
            int bMsk = i ^ l;
            int id = IntegerUtils.lg2(l);
            res[i] = res[bMsk] + A[id];
        }
        return res;
    }

}
