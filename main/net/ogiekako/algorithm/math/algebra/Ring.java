package net.ogiekako.algorithm.math.algebra;

public abstract class Ring<V extends Ring<V>> extends Group<V> {
    public abstract V mul(V other);
}
