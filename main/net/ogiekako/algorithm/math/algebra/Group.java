package net.ogiekako.algorithm.math.algebra;

public interface Group<V> {
    V add(V other);
    V addInv();
    boolean isZero();
    V zero();
}
