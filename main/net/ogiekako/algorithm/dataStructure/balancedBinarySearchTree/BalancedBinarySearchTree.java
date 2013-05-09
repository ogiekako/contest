package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;
public interface BalancedBinarySearchTree<T extends Comparable<T>> {
    BalancedBinarySearchTree<T> split(int k);
    void merge(BalancedBinarySearchTree<T> other);
    int indexOf(T target);
    int size();
    T get(int index);
}