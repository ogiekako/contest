package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;
import net.ogiekako.algorithm.utils.Pair;

import java.util.*;
public class RBTreeSet<E extends Comparable<E>> extends AbstractSet<E> implements Set<E> {
    PersistentRedBlackTree<E> root;
    private RBTreeSet(PersistentRedBlackTree<E> root) {
        this.root = root;
    }
    public RBTreeSet() {
        this(null);
    }

    public RBTreeSet<E> sub(int from, int to) {
        int size = size();
        if (from < 0 || from > size) throw new IllegalArgumentException(size + " " + from);
        if (to < 0 || to > size) throw new IllegalArgumentException(size + " " + to);
        PersistentRedBlackTree<E> left = PersistentRedBlackTree.split(root, to).first;
        return new RBTreeSet<E>(PersistentRedBlackTree.split(left, from).second);
    }

    @Override
    public Iterator<E> iterator() {
        if (root == null) return Collections.emptyIterator();
        return new MyIterator<E>(root);
    }

    @SuppressWarnings({"unchecked", "SuspiciousToArrayCall", "NullableProblems"})
    @Override
    public <T> T[] toArray(T[] a) {
        if (root == null) return new ArrayList<T>().toArray(a);
        return root.toList().toArray(a);
    }

    @Override
    public int size() {
        return PersistentRedBlackTree.size(root);
    }
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
    @Override
    public boolean contains(Object o) {   // O(log n)
        //noinspection unchecked
        return PersistentRedBlackTree.binarySearch(root, (E) o) >= 0;
    }
    @Override
    public boolean add(E e) {
        int k = PersistentRedBlackTree.binarySearch(root, e);
        if (k >= 0) {
            return false;
        } else {
            k = -k - 1;
//            System.err.println("k = " +k);
            Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> pair = PersistentRedBlackTree.split(root, k);
            PersistentRedBlackTree<E> left = pair.first;
            PersistentRedBlackTree<E> right = pair.second;
            PersistentRedBlackTree<E> left2 = PersistentRedBlackTree.mergeAsList(left, PersistentRedBlackTree.singleton(e));
            PersistentRedBlackTree<E> res = PersistentRedBlackTree.mergeAsList(left2, right);
            root = res;
            return true;
        }
    }
    @Override
    public boolean remove(Object o) {
        int k = PersistentRedBlackTree.binarySearch(root, (E) o);
        if (k < 0) {
            return false;
        } else {
//            System.err.println("k = " + k);
            PersistentRedBlackTree<E> left = PersistentRedBlackTree.split(root, k).first;

            PersistentRedBlackTree<E> right = PersistentRedBlackTree.split(root, k + 1).second;
//            System.err.println("left: " + PersistentRedBlackTree.size(left));
//            System.err.println("right: " + PersistentRedBlackTree.size(right));
            root = PersistentRedBlackTree.mergeAsList(left, right);
//            System.err.println("root: " + PersistentRedBlackTree.size(root));
            return true;
        }
    }
    @Override
    public void clear() {
        root = null;
    }
    @Override
    public int hashCode() {
        return root.hashCode();
    }
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object o) {
        TreeSet<E> set = new TreeSet<E>();
        for (E e : this) set.add(e);
        return set.equals(o);
    }
    public E first() {
        return PersistentRedBlackTree.split(root, 1).first.entry;
    }
    public E last() {
        int size = PersistentRedBlackTree.size(root);
        return PersistentRedBlackTree.split(root, size - 1).second.entry;
    }
    public RBTreeSet<E> copy() {
        return new RBTreeSet<E>(root);
    }

    private static class MyIterator<E> implements Iterator<E> {
        Stack<PersistentRedBlackTree<E>> ancestors;
        PersistentRedBlackTree<E> current;
        E next;
        private MyIterator(PersistentRedBlackTree<E> root) {
            current = root;
            ancestors = new Stack<PersistentRedBlackTree<E>>();
            while (current.l != null) {
                ancestors.push(current);
                current = current.l;
            }
            next = current.entry;
        }

        @Override
        public boolean hasNext() {
            if (next != null) return true;
            next = nextSub();
            return next != null;
        }
        @Override
        public E next() {
            if (next != null) {
                E res = next;
                next = null;
                return res;
            }
            return nextSub();
        }
        private E nextSub() {
            for (; ; ) {
                if (ancestors.isEmpty()) return null;
                PersistentRedBlackTree<E> parent = ancestors.peek();
                if (parent.l == current) {
                    current = parent.r;
                    while (current.l != null) {
                        ancestors.push(current);
                        current = current.l;
                    }
                    return current.entry;
                } else {
                    ancestors.pop();
                    current = parent;
                }
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
