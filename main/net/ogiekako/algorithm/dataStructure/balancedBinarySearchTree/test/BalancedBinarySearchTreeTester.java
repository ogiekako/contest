package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.test;
/*
    BalancedBinarySearchTree<T> split(int k);
    void mergeAsList(BalancedBinarySearchTree<T> other);
    int binarySearch(Node<T> target);
    int size();
    Node<T> get(int index);

    public static interface Node<T>{
        BalancedBinarySearchTree<T> getTree();
        T value();
    }
 */

import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.BalancedBinarySearchTree;

import java.util.Random;
public class BalancedBinarySearchTreeTester {
    public static interface Generator {
        BalancedBinarySearchTree<Integer> create();
        BalancedBinarySearchTree<Integer> create(Integer element);
    }
    public static void test(Generator gen) {
        Random rnd = new Random(41028941028L);
        int Q = 100000;
        BalancedBinarySearchTree<Integer> tree = gen.create();
        for (int i = 0; i < Q; i++) {
            int type = rnd.nextInt();
        }
    }
}
