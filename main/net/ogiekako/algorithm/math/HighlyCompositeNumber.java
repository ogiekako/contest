package net.ogiekako.algorithm.math;

import net.ogiekako.algorithm.dataStructure.intCollection.IntArray;
import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.interfaces.Function;

import java.util.HashSet;
import java.util.PriorityQueue;

public class HighlyCompositeNumber {
    /**
     * 高々 primeUpTo までの素数を使う,高度合成数の候補を,値の小さい方から順に
     * function.found が true になるまで
     * 列挙する.
     * exponents[i] >= exponents[i+1].
     * <p/>
     * Euler110
     *
     * @param primeUpTo
     * @param function
     */
    public static void iterateOverAllCandidateOfHighlyCompositeNumber(int primeUpTo, Function<Entry, Boolean> function) {
        int[] primes = MathUtils.generatePrimes(primeUpTo);
        PriorityQueue<Entry> que = new PriorityQueue<Entry>();
        que.offer(new Entry(1L, new int[0]));
        HashSet<IntArray> deja = new HashSet<IntArray>();
        while (!que.isEmpty()) {
            Entry cur = que.poll();
            IntArray ar = new IntArray(cur.exponents);
            if (deja.contains(ar)) continue;

            if (function.f(cur)) return;

            deja.add(ar);

            int[] nExponents = ArrayUtils.appended(cur.exponents, 1);
            long nNumber = cur.number * primes[cur.exponents.length];
            que.offer(new Entry(nNumber, nExponents));

            for (int i = 0; i < cur.exponents.length; i++) {
                if (i > 0 && nExponents[i - 1] == nExponents[i]) continue;
                nExponents = cur.exponents.clone();
                nExponents[i]++;
                nNumber = cur.number * primes[i];
                que.offer(new Entry(nNumber, nExponents));
            }
        }
    }

    public static class Entry implements Comparable<Entry> {
        public final long number;
        public final int[] exponents;

        Entry(long number, int[] exponents) {
            this.number = number;
            this.exponents = exponents;
        }

        public int compareTo(Entry o) {
            return Long.compare(number, o.number);
        }
    }
}
