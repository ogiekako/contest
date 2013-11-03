package net.ogiekako.algorithm.math.algebra;

public abstract class Group<V extends Group<V>> {
    abstract public V add(V other);
    abstract public V addInv();
    abstract public boolean isZero();
    abstract public V zero();
}
