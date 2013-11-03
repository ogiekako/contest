package net.ogiekako.algorithm.math.algebra;

public abstract class Field<V extends Field<V>> extends Ring<V> {
    abstract public V mulInv();
}
