package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
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

public class Treap<T> implements BalancedBinarySearchTree<T> {
    private final static Random random = new Random(124018024801204L);
    private static boolean log = !true;
    Node root;
    public Treap() { }
    public Treap(T element) {
        this.root = new Node(element);
    }
    public static <T> Treap<T> of(T element) {
        return new Treap<T>(element);
    }
    private Treap(Node root) {
        this.root = root;
    }
    public Treap<T> split(int k) {// Split into [0,k) and [k,n). Returns [k,n).
        if (k < 0 || k > size(root)) throw new IndexOutOfBoundsException(k + " " + size(root));
        Pair<Node, Node> pair = split(root, k);
        this.root = pair.first;
        if (root != null) root.parent = null;
        if (pair.second != null) pair.second.parent = null;
        return new Treap<T>(pair.second);
    }

    private Pair<Node, Node> split(Node root, int k) {// [0,k), [k,n)
        if (root == null) return Pair.of(null, null);
        if (k <= size(root.left)) {// split left
            Pair<Node, Node> pair = split(root.left, k);
            setLeft(root, pair.second);
            return Pair.of(pair.first, root.update());
        } else {
            Pair<Node, Node> pair = split(root.right, k - size(root.left) - 1);
            setRight(root, pair.first);
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
        root.parent = null;
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
        debug("mergeAsList", left, right);
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.priority > right.priority) {// left.root becomes a new root
            debug("left", left, right);
            setRight(left, merge(left.right, right));
            debug("left2", left);
            return left.update();
        } else {
            debug("right", left, right);
            setLeft(right, merge(left, right.left));
            debug("right2", right);
            return right.update();
        }
    }
    static void debug(Object... os) {
        if (log)
            System.out.println(Arrays.deepToString(os));
    }
    private void setRight(Node root, Node right) {
//        if (root.right != null) root.right.parent = null;
        root.right = right;
        if (right != null)
            right.parent = root;
    }
    private void setLeft(Node root, Node left) {
//        if (root.left != null) root.left.parent = null;
        root.left = left;
        if (left != null)
            left.parent = root;
    }

    public int size() {
        return root == null ? 0 : root.size;
    }
    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    public int indexOf(BalancedBinarySearchTree.Node<T> target) {
        return indexOf(cast(target));
    }

    public int indexOf(Node target) {
        if (target.parent == null) return size(target.left);
        if (target.parent.left == target) {
            return indexOf(target.parent) - 1 - size(target.right);
        } else {
            return indexOf(target.parent) + size(target.left) + 1;
        }
    }

    private Node cast(BalancedBinarySearchTree.Node<T> target) {
        return (Node) target;
    }

    public Node get(int k) {
        if (k < 0 || k >= size()) throw new IndexOutOfBoundsException(k + " " + size());
        Treap<T> mid = split(k);
        Treap<T> right = mid.split(1);
        Node res = mid.root;
        merge(mid);
        merge(right);
        return res;
    }
    public T indexOf(T value) {
        throw new UnsupportedOperationException();
    }
    class Node implements BalancedBinarySearchTree.Node<T> {
        final double priority;
        final T element;
        Node left, right;
        Node parent;
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
            show();
            if (parent != null && parent.left != this && parent.right != this) {
                parent.show();
                throw new AssertionError();
            }
            if (left != null && left.parent != this) {
                debug(elem(left));
                debug(elem(left.parent));
                throw new AssertionError();
            }
            if (right != null && right.parent != this) throw new AssertionError();
            return "(" + toString(left) + element + "," + size + toString(right) + ")";
        }
        void show() {
            debug(elem(this), elem(left), elem(right), elem(parent));
        }

        private String elem(Node node) {
            return node == null ? "" : ("" + node.element + "-" + node.size);
        }
        private String toString(Node node) {
            if (node == null) return "";
            return node.toString();
        }
        public T value() {
            return element;
        }
        public int size() {
            return size;
        }
        private int size(Node node) {
            return node == null ? 0 : node.size;
        }
        public BalancedBinarySearchTree<T> getTree() {
            if (parent == null) {
                return new Treap<T>(this);
            }
            return parent.getTree();
        }
    }
    @Override
    public String toString() {
        if (root == null) return "";
        return root.toString();
    }
}
