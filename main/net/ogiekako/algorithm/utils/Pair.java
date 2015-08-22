package net.ogiekako.algorithm.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

public class Pair<F, S> implements Comparable<Pair<F, S>> {
    public final F first;
    public final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return first.equals(pair.first) && second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }

    @Override
    public int compareTo(Pair<F, S> o) {
        @SuppressWarnings("unchecked")
        int i = ((Comparable<F>) first).compareTo(o.first);
        if (i != 0) return i;
        @SuppressWarnings("unchecked")
        Comparable<S> tmp = (Comparable<S>) second;
        return tmp.compareTo(o.second);

        // Use lines below to avoid unchecked warning.

//        TreeSet<Object> A = new TreeSet<Object>();
//        A.add(first);
//        if (!A.contains(o.first)) {
//            return A.headSet(o.first).isEmpty() ? 1 : -1;
//        }
//        TreeSet<Object> B = new TreeSet<Object>();
//        B.add(second);
//        if (!B.contains(o.second)) {
//            return B.headSet(o.second).isEmpty() ? 1 : -1;
//        }
//        return 0;
    }

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<F, S>(first, second);
    }

    @Override
    public String toString() {
        return first + " " + second;
    }
}
