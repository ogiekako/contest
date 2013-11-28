package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.dataStructure.interval.IntervalSet;

public class Over9000Rocks {
    public int countPossibilities(int[] lowerBound, int[] upperBound) {
        IntervalSet set = new IntervalSet();
        for (int i = 0; i < 1 << lowerBound.length; i++) {
            int lb = 0;
            int ub = 0;
            for (int j = 0; j < lowerBound.length; j++)
                if ((i >> j & 1) == 1) {
                    lb += lowerBound[j];
                    ub += upperBound[j];
                }
            lb = Math.max(9001, lb);
            if (lb <= ub) {
                set.add(new Interval(lb, ub + 1));
            }
        }
        return (int) set.cardinality();
    }
}

