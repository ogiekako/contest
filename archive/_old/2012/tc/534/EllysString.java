package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.StringUtils;
import net.ogiekako.algorithm.utils.TestUtils;

import java.util.Arrays;

public class EllysString {
    int[] s, t;
    int len;
    private static final int INF = 10000;

    public int theMin(String[] ss, String[] ts) {
        s = StringUtils.encodeLowerCaseStringToIntArray(StringUtils.concat(ss));
        t = StringUtils.encodeLowerCaseStringToIntArray(StringUtils.concat(ts));
        len = t.length;
        int[][] nextPositionOf = generateNextPositionArray(s, 26);
        int[] minTransposition = new int[len];
        for (int i = 0; i < len; i++) {
            minTransposition[i] = calcMinTransposition(nextPositionOf, i);
        }
        int[] dp = new int[len + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 0; i < len; i++) {
            dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1);
            if (minTransposition[i] < INF) {
                dp[i + minTransposition[i] + 1] = Math.min(dp[i + minTransposition[i] + 1], dp[i] + minTransposition[i]);
            }
        }
        return dp[len];
    }

    private int[][] generateNextPositionArray(int[] numbers, int maxValue) {
        int[][] nextPositionOf = new int[len + 1][maxValue + 1];
        ArrayUtils.fill(nextPositionOf, -1);
        for (int i = len - 1; i >= 0; i--) {
            System.arraycopy(nextPositionOf[i + 1], 0, nextPositionOf[i], 0, maxValue + 1);
            nextPositionOf[i][numbers[i]] = i;
        }
        return nextPositionOf;
    }

    private int calcMinTransposition(int[][] nextPositionOf, int from) {
        int[] ns = s.clone();
        int nFrom = from;
        for (; ; ) {
            int to = nextPositionOf[from == nFrom ? from : nFrom + 1][t[nFrom]];
            if (to == -1) return INF;
            for (int i = to - 1; i >= nFrom; i--) {
                ArrayUtils.swap(ns, i, i + 1);
            }
            for (int i = nFrom; i < to; i++) if (ns[i] != t[i]) return INF;
            if (ns[to] == t[to]) return to - from;
            nFrom = to;
        }
    }

    public static void main(String[] args) {
        EllysString es = new EllysString();
        String s = TestUtils.generateRandomLowerCaseString(2500);
        String[] ss = StringUtils.divideString(s, 50);
        String t = TestUtils.generateRandomLowerCaseString(2500);
        String[] ts = StringUtils.divideString(t, 50);
        long start = System.currentTimeMillis();
        es.theMin(ss, ts);
        System.out.println(System.currentTimeMillis() - start);
    }
}
