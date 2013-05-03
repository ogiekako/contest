package net.ogiekako.algorithm.math.algebra;

public interface Ring<V> extends Group<V>{
    V mul(V other);
}
