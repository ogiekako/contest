package net.ogiekako.algorithm.dataStructure.interval;

import junit.framework.Assert;
import org.junit.Test;

public class IntervalSetTest {
    int SIZE = 5;
    int LEN = SIZE * (SIZE + 1) / 2; // 10

    private Interval[] intervals() {
        Interval[] res = new Interval[LEN];
        int ptr = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j <= i; j++) {
                res[ptr++] = Interval.of(j, i);
            }
        return res;
    }

    private IntervalSet[] makeAllIntervalSet() {
        IntervalSet[] res = new IntervalSet[1 << SIZE];
        for (int mask = 0; mask < 1 << SIZE; mask++) {
            IntervalSet is = new IntervalSet();
            for (int from = 0; from < SIZE; ) {
                if (mask << 31 - from < 0) {
                    int to = from;
                    while (to < SIZE && mask << 31 - to < 0) to++;
                    is.add(Interval.of(from, to));
                    from = to;
                } else from++;
            }
            res[mask] = is;
        }
        return res;
    }

    private int toMask(IntervalSet set) {
        int res = 0;
        for (Interval i : set.toArray()) {
            for (int j = (int) i.left; j < i.right; j++) res |= 1 << j;
        }
        return res;
    }

    private IntervalSet toIntervalSet(int mask) {
        IntervalSet res = new IntervalSet();
        for (int i = 0; i < SIZE; ) {
            if (mask << 31 - i < 0) {
                int j = i;
                while (j < SIZE && mask << 31 - j < 0) j++;
                res.add(Interval.of(i, j));
                i = j;
            } else i++;
        }
        return res;
    }

    @Test
    public void testCardinality() throws Exception {// test union (private) at the same time.
        Interval[] is = intervals();
        for (int i = 0; i < 1 << SIZE; i++) {
            IntervalSet set = new IntervalSet();
            for (int j = 0; j < LEN; j++) if (i << 31 - j < 0) set.add(is[j]);
            long res = set.cardinality();
            boolean[] exists = new boolean[SIZE];
            for (int j = 0; j < LEN; j++)
                if (i << 31 - j < 0) {
                    Interval interval = is[j];
                    for (long k = interval.left; k < interval.right; k++) {
                        exists[(int) k] = true;
                    }
                }
            long exp = 0;
            for (boolean b : exists) if (b) exp++;
            Assert.assertEquals(exp, res);
        }
    }

    @Test
    public void testXored() {
        IntervalSet[] sets = makeAllIntervalSet();
        for (IntervalSet set1 : sets)
            for (IntervalSet set2 : sets) {
                int mask1 = toMask(set1);
                int mask2 = toMask(set2);
                int mask = mask1 ^ mask2;
                IntervalSet exp = toIntervalSet(mask);
                IntervalSet res = set1.xored(set2);
                Assert.assertEquals(set1 + " " + set2, exp, res);
            }
    }
}
