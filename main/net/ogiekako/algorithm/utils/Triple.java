package net.ogiekako.algorithm.utils;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 5:07
 * To change this template use File | Settings | File Templates.
 */
public class Triple<F, S, T> implements Comparable<Triple<F,S,T>>{
    public final F first;
    public final S second;
    public final T third;

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int compareTo(Triple<F, S, T> o) {
        @SuppressWarnings("unchecked")
        int i = ((Comparable<F>) first).compareTo(o.first);
        if (i != 0) return i;
        @SuppressWarnings("unchecked")
        int j = ((Comparable<S>) second).compareTo(o.second);
        if (j != 0) return j;
        @SuppressWarnings("unchecked")
        Comparable<T> tmp = (Comparable<T>) third;
        return tmp.compareTo(o.third);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Triple triple = (Triple) o;

        if (first != null ? !first.equals(triple.first) : triple.first != null) return false;
        if (second != null ? !second.equals(triple.second) : triple.second != null) return false;
        if (third != null ? !third.equals(triple.third) : triple.third != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        result = 31 * result + (third != null ? third.hashCode() : 0);
        return result;
    }
}
