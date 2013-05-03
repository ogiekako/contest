package net.ogiekako.algorithm.dataStructure.tree;

public interface QueryableList<D> extends SimpleList<D> {
    D query(int from, int to);
}
