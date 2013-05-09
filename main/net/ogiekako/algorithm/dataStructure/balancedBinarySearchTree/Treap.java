package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Random;
/**
 * Elements a and b are retained even if a.compareTo(b) == 0.
 * <p/>
 * Invariant:
 * priority of a node > priority of its children.
 * <p/>
 * Ref: http://www.slideshare.net/iwiwi/2-12188757
 * Problems: http://www.codeforces.com/blog/entry/3767
 *
 * @param <T> Type of the elements
 */

public class Treap<T extends Comparable<T>> implements BalancedBinarySearchTree<T> {
    private final static Random random = new Random(124018024801204L);
    Node root;
    public Treap() { }
    public Treap(T element) {
        this.root = new Node(element);
    }
    private Treap(Node root) {
        this.root = root;
    }
    public Treap<T> split(int k) {// Split into [0,k) and [k,n). Returns [k,n).
        if (k < 0 || k > size(root)) throw new IndexOutOfBoundsException(k + " " + size(root));
        Pair<Node, Node> pair = split(root, k);
        this.root = pair.first;
        return new Treap<T>(pair.second);
    }

    public int indexOf(T target) {
        return indexOf(root, target);
    }

    private int indexOf(Node root, T target) {
        if (root == null) return -1;
        else if (root.element.compareTo(target) < 0) {
            int p = indexOf(root.right, target);
            if (p < 0) p = p - size(root.left) - 1;
            else p = p + size(root.left) + 1;
            return p;
        } else if (target.compareTo(root.element) < 0) {
            return indexOf(root.left, target);
        } else {
            return size(root.left);
        }
    }

    private Pair<Node, Node> split(Node root, int k) {// [0,k), [k,n)
        if (root == null) return Pair.of(null, null);
        if (k <= size(root.left)) {// split left
            Pair<Node, Node> pair = split(root.left, k);
            root.left = pair.second;
            return Pair.of(pair.first, root.update());
        } else {
            Pair<Node, Node> pair = split(root.right, k - size(root.left) - 1);
            root.right = pair.first;
            return Pair.of(root.update(), pair.second);
        }
    }

    /**
     * Merge this tree with the tree 'right'.
     * Node that the largest element of this tree should be smaller than or equal to the smallest element of 'right'.
     *
     * @param right The tree to be merged with this tree.
     */
    public void merge(BalancedBinarySearchTree<T> right) {
        Treap<T> o = (Treap<T>) right;
        root = merge(root, o.root);
    }

    /**
     * Merge tree a and b.
     * Node that the largest element of 'left' should be smaller than or equal to the smallest element of 'right'.
     *
     * @param left  - left
     * @param right - right
     * @return The root of the merged tree
     */
    private Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;
        if (left.priority > right.priority) {// left.root becomes a new root.
            left.right = merge(left.right, right);
            return left.update();
        } else {
            right.left = merge(left, right.left);
            return right.update();
        }
    }

    public int size() {
        return root == null ? 0 : root.size;
    }
    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    public T get(int k) {
        if (k < 0 || k >= size()) throw new IndexOutOfBoundsException(k + " " + size());
        Treap<T> mid = split(k);
        Treap<T> right = mid.split(1);
        T res = mid.root.element;
        merge(mid);
        merge(right);
        return res;
    }
    class Node {
        final double priority;
        final T element;
        Node left, right;
        int size = 1;
        Node(T element, double priority) {
            this.element = element;
            this.priority = priority;
        }
        Node(T element) {
            this(element, random.nextDouble());
        }
        /**
         * Call whenever its left or right node is changed.
         *
         * @return update this instance
         */
        public Node update() {
            size = size(left) + size(right) + 1;
            return this;
        }
        @Override
        public String toString() {
            if (left != null && left.element.compareTo(element) > 0) throw new AssertionError();
            if (right != null && element.compareTo(right.element) > 0) throw new AssertionError();
            return "(" + toString(left) + element + "," + size + toString(right) + ")";
        }
        private String toString(Node node) {
            if (node == null) return "";
            return node.toString();
        }
    }
    @Override
    public String toString() {
        if (root == null) return "";
        return root.toString();
    }
}
