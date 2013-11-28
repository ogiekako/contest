package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.AhoCorasick;
import net.ogiekako.algorithm.string.AutomatonState;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.StringUtils;

import java.io.PrintWriter;

public class FavouriteNumbers {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long L = in.nextLong(), R = in.nextLong(), K = in.nextLong();
        int N = in.nextInt();
        String[] good = new String[N];
        for (int i = 0; i < N; i++) good[i] = in.next();
        AhoCorasick ac = new AhoCorasick(StringUtils.digits);
        ac.construct(good);
        states = ac.allStates().toArray(new AutomatonState[0]);
        nextStates = new int[states.length][10];
        isGoodState = new boolean[states.length];
        for (int i = 0; i < states.length; i++)
            for (int j = 0; j < 10; j++) nextStates[i][j] = states[i].next(j).getIndex();
        for (int i = 0; i < states.length; i++) isGoodState[i] = states[i].getMatchedFromLonger() != null;
        ten = new long[19];
        for (int i = 0; i < 19; i++) ten[i] = i == 0 ? 1 : ten[i - 1] * 10;
        lower = new long[19];
        memo = new long[19][states.length][2];
        rs = new int[19];
        long v1 = solve(L);
        long res = 0;
        for (long b = Long.highestOneBit((long) 1e18); b > 0; b >>>= 1) {
            if (solve(res + b) - v1 < K) {
                res += b;
            }
        }
        out.println(res < L || res > R ? "no such number" : res);
    }
    AutomatonState[] states;
    int[][] nextStates;
    boolean[] isGoodState;

    long[] ten;
    int[] rs;
    long[] lower;
    long[][][] memo;// pos, state, less

    long solve(long R) {// # numbers less than R and contains good
        // from larger digits
        for (int i = 0; i < 19; i++) lower[i] = R % ten[i];
        for (int i = 0; i < 19; i++) {
            rs[i] = (int) (R % 10); R /= 10;
        }
        ArrayUtils.fill(memo, -1);
        return recur(18, 0, false);
    }

    private long recur(int pos, int stateId, boolean less) {
        if (pos < 0) return 0;
        long res = memo[pos][stateId][b2i(less)];
        if (res >= 0) return res;
        res = 0;
        for (int d = 0; d < 10; d++) {
            if (!less && d > rs[pos]) continue;
            boolean nLess = less | (d < rs[pos]);
            int nStateId = nextStates[stateId][d];
            if (isGoodState[nStateId]) {
                if (nLess) res += ten[pos];
                else res += lower[pos];
            } else
                res += recur(pos - 1, nStateId, nLess);
        }
        return memo[pos][stateId][b2i(less)] = res;
    }

    private int b2i(boolean b) {
        return b ? 1 : 0;
    }
}
