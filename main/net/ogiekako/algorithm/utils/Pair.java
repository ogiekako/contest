package net.ogiekako.algorithm.utils;

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

    public int compareTo(Pair<F, S> o) {
        @SuppressWarnings("unchecked")
        int i = ((Comparable<F>) first).compareTo(o.first);
        if (i != 0) return i;
        @SuppressWarnings("unchecked")
        Comparable<S> tmp = (Comparable<S>) second;
        return tmp.compareTo(o.second);
    }

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<F, S>(first, second);
    }
    @Override
    public String toString() {
        return first + " " + second;
    }
}
