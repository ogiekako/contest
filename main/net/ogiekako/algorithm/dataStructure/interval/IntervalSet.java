package net.ogiekako.algorithm.dataStructure.interval;

import java.util.TreeSet;

/**
 * A set of intervals.
 * Overlapped sections are automatically merged.
 */
public class IntervalSet {
    TreeSet<Interval> set = new TreeSet<Interval>();

    public void add(Interval interval) {
        if (interval.length() > 0)
            set.add(interval);
    }

    public long cardinality() {
        union();
        long res = 0;
        for (Interval i : set) {
            res += i.length();
        }
        return res;
    }

    /**
     * O(n)
     */
    private void union() {
        if (set.isEmpty()) return;
        Interval[] is = set.toArray(new Interval[set.size()]);
        set.clear();
        Interval current = is[0];
        for (int i = 1; i < is.length; i++) {
            if (current.canUnion(is[i])) {
                current = current.union(is[i]);
            } else {
                set.add(current);
                current = is[i];
            }
        }
        set.add(current);
    }

    @Override
    public String toString() {
        return set.toString();
    }

    public Interval[] toArray() {
        union();
        return set.toArray(new Interval[set.size()]);
    }

    /**
     * O(n log n)
     */
    public IntervalSet xored(IntervalSet other) {
        Interval[] is = toArray(), js = other.toArray();
        int lenI = is.length, lenJ = js.length;
        long from = 0, to = 0;
        int type = 0;
        int iPtr = 0, jPtr = 0;
        IntervalSet res = new IntervalSet();
        while (iPtr < lenI || jPtr < lenJ) {
            if (type == 0) {
                if (jPtr >= lenJ || iPtr < lenI && is[iPtr].left < js[jPtr].right) {
                    type = 1;
                    from = is[iPtr].left;
                    to = is[iPtr++].right;
                } else {
                    type = 2;
                    from = js[jPtr].left;
                    to = js[jPtr++].right;
                }
            } else if (type == 1) {// i
                if (jPtr < lenJ && js[jPtr].left < to) {
                    res.add(new Interval(from, js[jPtr].left));
                    if (js[jPtr].right < to) {
                        from = js[jPtr].right;
                    } else {
                        from = to;
                        to = js[jPtr].right;
                        type = 2;
                    }
                    jPtr++;
                } else {
                    res.add(new Interval(from, to));
                    type = 0;
                }
            } else {// j
                if (iPtr < lenI && is[iPtr].left < to) {
                    res.add(new Interval(from, is[iPtr].left));
                    if (is[iPtr].right < to) {
                        from = is[iPtr].right;
                    } else {
                        from = to;
                        to = is[iPtr].right;
                        type = 1;
                    }
                    iPtr++;
                } else {
                    res.add(new Interval(from, to));
                    type = 0;
                }
            }
        }
        res.add(new Interval(from, to));
        res.union();
        return res;

//        TreeSet<Long> pointSet = new TreeSet<Long>();
//        for(Interval i : is){
//            pointSet.add(i.left);
//            pointSet.add(i.right);
//        }
//        for(Interval j : js){
//            pointSet.add(j.left);
//            pointSet.add(j.right);
//        }
//        long[] points = Cast.toLongArray(pointSet);
//        boolean[] xor = new boolean[points.length];
//        for(Interval i : is){
//            int l = Arrays.binarySearch(points, i.left);
//            int r = Arrays.binarySearch(points, i.right);
//            for (int j = l; j < r; j++)xor[j] ^= true;
//        }
//        for(Interval i : js) {
//            int l = Arrays.binarySearch(points, i.left);
//            int r = Arrays.binarySearch(points, i.right);
//            for (int j = l; j < r; j++) xor[j] ^= true;
//        }
//        IntervalSet res = new IntervalSet();
//        for (int i = 0; i < points.length; ){
//            if(xor[i]){
//                int j = i;
//                while(xor[j])j++;
//                res.add(Interval.of(points[i], points[j]));
//                i=j;
//            }else i++;
//        }
//        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntervalSet that = (IntervalSet) o;

        return set.equals(that.set);

    }

    @Override
    public int hashCode() {
        return set.hashCode();
    }
}
