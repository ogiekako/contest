package net.ogiekako.algorithm.dataStructure.dynamic.dyncon;
import net.ogiekako.algorithm.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
class Treap {
    public static final boolean log = !true;
    private final static Random random = new Random(124018024801204L);
    int vertex;
    int size;
    int incidents, sumIncidents;
    int incidentTreeEdges, sumIncidentTreeEdges;
    Treap left, right, parent;
    int priority;
    public Treap(int vertex, int incidents) {
        this.vertex = vertex;
        this.incidents = incidents;
        this.size = 1;
        priority = random.nextInt();
    }
    public static Treap of(int vertex, int incidents) {
        return new Treap(vertex, incidents);
    }

    public Pair<Treap, Treap> split(int k) {// [0,k), [k,n)
        if (k < 0 || k > size()) throw new IndexOutOfBoundsException(k + " " + size());
        if (parent != null) throw new AssertionError();
        Pair<Treap, Treap> pair = split(this, k);
        if (pair.first != null) pair.first.parent = null;
        if (pair.second != null) pair.second.parent = null;
        return pair;
    }

    private static Pair<Treap, Treap> split(Treap root, int k) {// [0,k), [k,n)
        if (root == null) return Pair.of(null, null);
        if (k <= size(root.left)) {// split left
            Pair<Treap, Treap> pair = split(root.left, k);
            setLeft(root, pair.second);
            return Pair.of(pair.first, root.update());
        } else {
            Pair<Treap, Treap> pair = split(root.right, k - size(root.left) - 1);
            setRight(root, pair.first);
            return Pair.of(root.update(), pair.second);
        }
    }
    private static int size(Treap node) {
        return node == null ? 0 : node.size();
    }

    public Treap update() {
        size = size(left) + size(right) + 1;
        sumIncidents = sumIncidents(left) + sumIncidents(right) + incidents;
        sumIncidentTreeEdges = sumIncidentTreeEdges(left) + sumIncidentTreeEdges(right) + incidentTreeEdges;
        return this;
    }
    private int sumIncidentTreeEdges(Treap node) {
        return node == null ? 0 : node.sumIncidentTreeEdges;
    }
    private static int sumIncidents(Treap node) {
        return node == null ? 0 : node.sumIncidents;
    }

    public void addIncident(int d) {
        incidents += d;
        updateFromLeaf();
    }
    public void addIncidentTreeEdges(int d) {
        incidentTreeEdges += d;
        updateFromLeaf();
    }

    private void updateFromLeaf() {
        update();
        if (parent != null) parent.updateFromLeaf();
    }

    /**
     * Merge this tree with the tree 'right'.
     * Treap that the largest element of this tree should be smaller than or equal to the smallest element of 'right'.
     *
     * @param right The tree to be merged with this tree.
     * @return the root node of the merged tree.
     */
    public Treap merge(Treap right) {
        if (this.parent != null) throw new AssertionError();
        if (right != null && right.parent != null) throw new AssertionError();
        return merge(this, right);
    }

    /**
     * Merge tree a and b.
     * Treap that the largest element of 'left' should be smaller than or equal to the smallest element of 'right'.
     *
     * @param left  - left
     * @param right - right
     * @return The root of the merged tree
     */
    public static Treap merge(Treap left, Treap right) {
        debug("merge", left, right);
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
    private static void setRight(Treap root, Treap right) {
        root.right = right;
        if (right != null)
            right.parent = root;
    }
    private static void setLeft(Treap root, Treap left) {
        root.left = left;
        if (left != null)
            left.parent = root;
    }

    public int size() {
        return size;
    }

    public static int indexOf(Treap target) {
        if (target.parent == null) return size(target.left);
        if (target.parent.left == target) {
            return indexOf(target.parent) - 1 - size(target.right);
        } else {
            return indexOf(target.parent) + size(target.left) + 1;
        }
    }

    public static Treap get(Treap root, int k) {
        if (k < 0 || k >= root.size()) throw new IndexOutOfBoundsException(k + " " + root.size());
        Pair<Treap, Treap> pair = root.split(k);
        root = pair.first;
        Treap mid = pair.second;
        pair = mid.split(1);
        mid = pair.first;
        Treap right = pair.second;
        root = merge(root, mid);
        merge(root, right);
        return mid;
    }

    public Treap get(int k) {
        if (this.parent != null) throw new AssertionError();
        if (k < 0 || k >= this.size()) throw new IndexOutOfBoundsException(k + " " + this.size());
        return Treap.get(this, k);
    }

    public Treap root() {
        if (parent == null) return this;
        return parent.root();
    }

    public void candidates(List<Integer> list) {
        if (incidents > 0) list.add(vertex);
        if (left != null && left.sumIncidents > 0) left.candidates(list);
        if (right != null && right.sumIncidents > 0) right.candidates(list);
    }
}
