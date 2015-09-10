package net.ogiekako.algorithm.math.algebra;

public abstract class Group<V extends Group<V>> {
    abstract public V add(V other);
    abstract public V addInv();
    abstract public boolean isZero();
    abstract public V zero();

    public boolean equals(Object obj) {
        V v = (V)obj;
        return addInv().add(v).isZero();
    }
}
