package net.ogiekako.algorithm.dataStructure.heap;
import java.util.ArrayList;
import java.util.List;
/**
 * Binomial heap is an implementation of a meldable heap.
 * It is implemented as a collection of binomial trees.
 * <ul>
 * <li>A binomial tree of order 0 is a single node</li>
 * <li>A binomial tree of order k has a root node whose children are binomial trees of orders k-1,k-2,...,1,0.</li>
 * </ul>
 * A binomial heap of order k has 2^k nodes. Each node in a binomial heap is smaller than or equal to each of its children.
 * <p/>
 * Melding two order k binomial trees is O(1) operation.
 * We can meld two heaps using only O(log n) tree meld operations.
 * <p/>
 * Ref: <a href="http://en.wikipedia.org/wiki/Binomial_heap">wikipedia:Binomial heap</a>
 *
 * @param <V> Type of the elements
 */
public class BinomialHeap<V extends Comparable<V>> implements Heap<V> {
    private List<Tree> trees = new ArrayList<Tree>(4);// Orders are 0,1,... in this order.
    private int size = 0;

    /**
     * O(log n)
     *
     * @return Minimum element
     */
    public V poll() {
        int min = -1;
        for (int i = 0; i < trees.size(); i++)
            if (trees.get(i) != null) {
                if (min == -1 || trees.get(i).value.compareTo(trees.get(min).value) < 0) {
                    min = i;
                }
            }
        if (min == -1) return null; // empty
        size--;
        BinomialHeap<V> tmp = new BinomialHeap<V>();
        V res = trees.get(min).value;
        tmp.trees = trees.get(min).children;
        trees.set(min, null);
        meld(tmp);
        return res;
    }

    /**
     * Merge two heaps.
     * Note that the given heap 'other' might be modified and broken.
     *
     * @param other The heap to be merged with.
     */
    public void meld(Heap<V> other) {
        if (!(other instanceof BinomialHeap)) throw new UnsupportedOperationException();
        BinomialHeap<V> o = (BinomialHeap<V>) other;
        size += o.size();
        Tree carry = null;
        int order;
        for (order = 0; order < trees.size() || order < o.trees.size(); order++) {
            Tree x = order < trees.size() ? trees.get(order) : null;
            Tree y = order < o.trees.size() ? o.trees.get(order) : null;
            if (y != null && carry != null) {
                carry = merge(y, carry);
            } else {
                if (y != null) carry = y;
                if (carry != null) {
                    if (x != null) {
                        carry = merge(carry, x);
                        set(order, null);
                    } else {
                        set(order, carry);
                        carry = null;
                    }
                }
            }
        }
        if (carry != null) set(order, carry);
    }

    private void set(int index, Tree tree) {
        while (trees.size() <= index) trees.add(null);
        trees.set(index, tree);
    }

    public void add(V elem) {
        BinomialHeap<V> tmp = new BinomialHeap<V>();
        tmp.trees.add(new Tree(elem));
        tmp.size = 1;
        meld(tmp);
    }

    public int size() {
        return size;
    }

    private Tree merge(Tree a, Tree b) {
        if (a == b) throw new AssertionError();
        if (a.children.size() != b.children.size()) throw new AssertionError();
        if (a.value.compareTo(b.value) < 0) {
            a.children.add(b);
            return a;
        } else {
            b.children.add(a);
            return b;
        }
    }

    class Tree {
        List<Tree> children = new ArrayList<Tree>(4);// Orders are 0,1,... in this order.
        V value;
        public Tree(V value) {
            this.value = value;
        }
    }
}
