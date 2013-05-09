package net.ogiekako.algorithm.dataStructure.interval;

import net.ogiekako.algorithm.utils.IntegerUtils;

public class Interval implements Comparable<Interval> {
    public final long left, right;

    /**
     * if left > right, right and left are swapped.
     *
     * @param left  - left (inclusive)
     * @param right - right (exclusive)
     */
    public Interval(long left, long right) {// [left, right)
        if (left > right) {long tmp = left; left = right; right = tmp;}
        this.left = left;
        this.right = right;
    }

    public static Interval getEmptyInterval() {
        return new Interval(0, 0);
    }

    public int compareTo(Interval o) {
        if (left != o.left) return IntegerUtils.compare(left, o.left);
        return IntegerUtils.compare(right, o.right);
    }

    public long length() {
        return right - left;
    }

    /**
     * @param interval The interval to be checked
     * @return whether the given interval has a (maybe empty) intersection.
     */
    public boolean canUnion(Interval interval) {
        long minRight = Math.min(right, interval.right);
        long maxLeft = Math.max(left, interval.left);
        return minRight >= maxLeft;
    }

    /*
     * throws exception when two intervals are distinct.
     */
    public Interval union(Interval interval) {
        if (!canUnion(interval)) throw new IllegalArgumentException();
        return new Interval(Math.min(left, interval.left), Math.max(right, interval.right));
    }

    /*
     * returns an empty(0-length) interval when the intersection is empty.
     */
    public Interval intersection(Interval interval) {
        long nLeft = Math.max(left, interval.left);
        long nRight = Math.min(right, interval.right);
        if (nLeft < nRight) return new Interval(nLeft, nRight);
        return new Interval(nLeft, nLeft);
    }

    public boolean contains(long x) {
        return left <= x && x < right;
    }

    public static Interval of(long left, long right) {
        return new Interval(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Interval interval = (Interval) o;

        return left == interval.left && right == interval.right;

    }

    @Override
    public int hashCode() {
        int result = (int) (left ^ (left >>> 32));
        result = 31 * result + (int) (right ^ (right >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "[" + left + ", " + right + ")";
    }
}
