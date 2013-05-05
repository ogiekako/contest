package net.ogiekako.algorithm.dataStructure.heap;
public interface Heap<V extends Comparable<V>> {
    V poll();
    void meld(Heap<V> other);
    void add(V elem);
    int size();
}
