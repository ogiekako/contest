package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;
public interface BalancedBinarySearchTree<T> {
    BalancedBinarySearchTree<T> split(int k);
    void merge(BalancedBinarySearchTree<T> other);
    int indexOf(Node<T> target);
    int size();
    Node<T> get(int index);

    public static interface Node<T> {
        BalancedBinarySearchTree<T> getTree();
        T value();
    }
}