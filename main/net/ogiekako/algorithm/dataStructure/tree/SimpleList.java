package net.ogiekako.algorithm.dataStructure.tree;

public interface SimpleList<V> {
    void set(int i, V value);

    SimpleList<V> cut(int from);

    void append(SimpleList<V> other);

    V get(int index);

    int size();

    void add(V value);
}
