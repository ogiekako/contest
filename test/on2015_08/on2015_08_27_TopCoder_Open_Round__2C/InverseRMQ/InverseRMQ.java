package on2015_08.on2015_08_27_TopCoder_Open_Round__2C.InverseRMQ;



import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.*;

public class InverseRMQ {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    /**
     * [0,n) の
     * permutation P に対して
     * 区間 [Ai, Bi] の最大値が ans_i という条件をすべて満たすものが存在するか。
     * <p/>
     * N <= 10^9
     * |A| <= 50.
     * <p/>
     * 区間がかぶっていたら、ans が小さいほうに、上側の区間を下げる。
     * これで完全になくなる区間があったら不可能。
     * また、同じ ans で distinct な区間があったら不可能。
     * <p/>
     * この前処理を行うと、連続する区間の例えば一番左端 を ans にすることにしても問題ない。その上で他の条件をちゃんと満たせるか確認する。
     * 一番下側の区間から順番に埋めていけるかを判定すれば良い。
     */
    String possible = "Possible", impossible = "Impossible";

    public String possible(int n, int[] A, int[] B, int[] ans) {
        ArrayUtils.decreaseByOne(A);
        HashMap<Integer, Interval> ansToInterval = new HashMap<Integer, Interval>();
        for (int i = 0; i < A.length; i++) {
            Interval iv = new Interval(A[i], B[i]);
            if (ansToInterval.containsKey(ans[i])) {
                iv = iv.intersection(ansToInterval.get(ans[i]));
            }
            if (iv.isEmpty()) return impossible;
            ansToInterval.put(ans[i], iv);
        }
        TreeSet<Integer> xSet = new TreeSet<Integer>();
        for (int x : A) xSet.add(x);
        for (int x : B) xSet.add(x);
        int[] xs = tois(xSet.toArray(new Integer[0]));
        int[] min = new int[xs.length - 1];// [i, i+1)
        for (int i = 0; i < min.length; i++) {
            min[i] = Integer.MAX_VALUE;
            for (int j = 0; j < A.length; j++) {
                if (A[j] <= xs[i] && xs[i + 1] <= B[j]) min[i] = Math.min(min[i], ans[j]);
            }
        }
        for (int a : ans) {
            boolean ok = false;
            for (int b : min) if (a == b) ok = true;
            if (!ok) return impossible;
        }
        TreeSet<Integer> minSet = new TreeSet<Integer>();
        for (int m : min) minSet.add(m);
        int cur = 0;
        for (int m : minSet) {
            // Necessary to check.
            if (m == Integer.MAX_VALUE) continue;
            int len = 0;
            boolean possible = false;
            for (int i = 0; i < min.length; i++) {
                if (min[i] != m) continue;
                len += xs[i + 1] - xs[i];

                // Necessary to check.
                if (ansToInterval.get(m).contains(xs[i])) possible = true;
            }
            cur += len;
            if (cur > m || !possible) return impossible;
        }
        return possible;
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) {
            is[i] = Is[i];
        }
        return is;
    }

}
