package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;

import net.ogiekako.algorithm.utils.Pair;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
//http://hos.ac/slides/20120323_joi_copypaste.pdf

/**
 * This tree holds the following red-black properties:
 * 1. Every node is Red of Black.
 * 2. The root node is Black.
 * 3. All leaves are Black.
 * 4. If a node is Red, then its both children are Black.
 * 5. For every fixed node v, all paths from v to descendant leaves contain same number of black nodes.
 *
 * @param <E>
 */
public class PersistentRedBlackTree<E> {
    static final Color B = Color.BLACK, R = Color.RED;
    final Color color;
    final PersistentRedBlackTree<E> l;
    final PersistentRedBlackTree<E> r;
    final int rank;
    final int size;
    /**
     * If this node is a leaf, entry is the given value, otherwise, entry = r.entry.
     */
    final E entry;

    private PersistentRedBlackTree(PersistentRedBlackTree<E> l, PersistentRedBlackTree<E> r, Color color, E entry) {
        this.l = l;
        this.r = r;
        this.color = color;
        if (l != null) {
//            if (l.rank + (l.color == B ? 1 : 0) != r.rank + (r.color == B ? 1 : 0)) throw new AssertionError();
//            rank = l.rank + (l.color == B ? 1 : 0);
//            if(numBlackWithoutMe(l) != numBlackWithoutMe(r)) throw new AssertionError();
            if (l.rank != r.rank) throw new AssertionError();
            rank = l.rank + (color == B ? 1 : 0);
            size = l.size + r.size;
        } else {
            rank = color == B ? 1 : 0;
            size = 1;
        }
        this.entry = entry;
    }

    public static int size(PersistentRedBlackTree<?> root) {
        return root == null ? 0 : root.size();
    }

    static <E> PersistentRedBlackTree<E> node(PersistentRedBlackTree<E> left, PersistentRedBlackTree<E> right, Color color) {
        return new PersistentRedBlackTree<E>(left, right, color, right.entry);
    }

    public static <E> PersistentRedBlackTree<E> singleton(E e) {
        return new PersistentRedBlackTree<E>(null, null, B, e);
    }

    /*
   O( | a.rank - b.rank | )
    */
    public static <E> PersistentRedBlackTree<E> mergeAsList(PersistentRedBlackTree<E> a, PersistentRedBlackTree<E> b) {
        if (a == null) return b;
        if (b == null) return a;
        PersistentRedBlackTree<E> c = mergeSub(a, b);
        if (c.color == R) c = node(c.l, c.r, B);
        return c;
    }

    private static <E> PersistentRedBlackTree<E> mergeSub(PersistentRedBlackTree<E> a, PersistentRedBlackTree<E> b) {
        if (a.rank < b.rank) {
            PersistentRedBlackTree<E> c = mergeSub(a, b.l);
            if (b.color == R || c.color == B || (c.color == R && c.l.color == B && c.r.color == B))
                return node(c, b.r, b.color);// 1
            if (b.r.color == R) return node(node(c.l, c.r, B), node(b.r.l, b.r.r, B), R); // 2
            if (c.l.color == R) {
                if (c.r.color == B) { // 3
                    return node(c.l, node(c.r, b.r, R), B);
                } else { // 4
                    return node(node(c.l.l, c.l.r, B), node(c.r, b.r, B), R);
                }
            } else { // 5
                return node(node(c.l, c.r.l, R), node(c.r.r, b.r, R), B);
            }
        } else if (a.rank > b.rank) {
            PersistentRedBlackTree<E> c = mergeSub(a.r, b);
            if (a.color == R || c.color == B || (c.color == R && c.r.color == B && c.l.color == B))
                return node(a.l, c, a.color);  // 1
            if (a.l.color == R) return node(node(a.l.l, a.l.r, B), node(c.l, c.r, B), R); // 2
            if (c.r.color == R) {
                if (c.l.color == B) {
                    return node(node(a.l, c.l, R), c.r, B);// 3
                } else {
                    return node(node(a.l, c.l, B), node(c.r.l, c.r.r, B), R);    // 4
                }
            } else {
                return node(node(a.l, c.l.l, R), node(c.l.r, c.r, R), B); // 5
            }
        } else {
            return node(a, b, R);
        }
    }

    private static <E> PersistentRedBlackTree<E> rotL(PersistentRedBlackTree<E> c) {
        return node(node(c.l, c.r.l, c.color), c.r.r, c.r.color);
    }

    private static <E> PersistentRedBlackTree<E> asRoot(PersistentRedBlackTree<E> c) {
        if (c == null || c.color == B) return c;
        return node(c.l, c.r, B);
    }

    /*
    左側が葉をleftLeafCount個含むように2つに分ける．
   O(log n)
    */
    public static <E> Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> split
    (PersistentRedBlackTree<E> a, int leftLeafCount) {
        if (size(a) < leftLeafCount) throw new IllegalArgumentException(size(a) + " " + leftLeafCount);
        if (leftLeafCount < 0) throw new IllegalArgumentException(leftLeafCount + "");
        Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> c = splitSub(a, leftLeafCount);
        return Pair.of(asRoot(c.first), asRoot(c.second));
    }

    private static <E> Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> splitSub(PersistentRedBlackTree<E> a, int k) {
        if (k <= 0) return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(null, a);
        if (k >= a.size) return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(a, null);
        if (a.l.size >= k) {
            Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> p = splitSub(a.l, k);
            return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(p.first, mergeAsList(p.second, a.r));
        } else {
            Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> p = split(a.r, k - a.l.size);
            return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(mergeAsList(a.l, p.first), p.second);
        }
    }

    public static <E> PersistentRedBlackTree<E> fromList(List<E> es) {
        return fromListSub(es, 0, es.size());
    }

    private static <E> PersistentRedBlackTree<E> fromListSub(List<E> es, int from, int to) {
        if (to - from == 1) return singleton(es.get(from));
        int mid = (from + to) / 2;
        return mergeAsList(fromListSub(es, from, mid), fromListSub(es, mid, to));
    }

    /**
     * Search the specified tree for the specified element using the binary search algorithm.
     * If it is not sorted, the results are undefined.  If the list
     * contains multiple elements equal to the specified object, there is no
     * guarantee which one will be found.
     *
     * @param root the list to be searched.
     * @param key  the key to be searched for.
     * @return the index of the search key, if it is contained in the list;
     * otherwise, <tt>(-(<i>insertion point</i>) - 1)</tt>.  The
     * <i>insertion point</i> is defined as the point at which the
     * key would be inserted into the list: the index of the first
     * element greater than the key, or <tt>list.size()</tt> if all
     * elements in the list are less than the specified key.  Note
     * that this guarantees that the return value will be &gt;= 0 if
     * and only if the key is found.
     * @throws ClassCastException if the list contains elements that are not
     *                            <i>mutually comparable</i> (for example, strings and
     *                            integers), or the search key is not mutually comparable
     *                            with the elements of the list.
     */
    public static <E extends Comparable<E>> int binarySearch(PersistentRedBlackTree<E> root, E key) {
        if (root == null) return -1;
        if (root.l == null) {
            E t = root.entry;
            int c = key.compareTo(t);
            return c < 0 ? -1 : c == 0 ? 0 : -2;
        }
        E t = root.l.entry;
//        System.err.println("t = " + t);
        int c = key.compareTo(t);
        if (c < 0) {
            return binarySearch(root.l, key);
        } else if (c == 0) {
            return size(root.l) - 1;
        } else {
            int k = binarySearch(root.r, key);
            if (k >= 0) return k + size(root.l);
            else return k - size(root.l);
        }
    }

    public static <E> void assertValidCondition(PersistentRedBlackTree<E> root) {
        if (root == null) return;
        Assert.assertEquals(B, root.color);  // 2
        assertValidConditionSub(root);
    }

    private static <E> void assertValidConditionSub(PersistentRedBlackTree<E> node) {
        if (node.l == null) {
            Assert.assertEquals(node.color == B ? 1 : 0, node.rank); // 5
            Assert.assertEquals(B, node.color); // 3
        } else if (node.color.equals(R)) {
            Assert.assertEquals(node.toString(), B, node.l.color); // 4
            Assert.assertEquals(B, node.r.color);
            Assert.assertEquals(node.rank, node.l.rank); // 5
            Assert.assertEquals(node.rank, node.r.rank);
        } else {
            Assert.assertEquals(node.rank, node.l.rank + 1); // 5
            Assert.assertEquals(node.rank, node.r.rank + 1);
        }
        if (node.l != null) {
            assertValidConditionSub(node.l);
            assertValidConditionSub(node.r);
        }
    }

    public int size() {
        return size;
    }

    public List<E> toList() {
        ArrayList<E> list = new ArrayList<E>();
        toListSub(list);
        return list;
    }

    private void toListSub(ArrayList<E> list) {
        if (l == null) list.add(entry);
        else {
            l.toListSub(list);
            r.toListSub(list);
        }
    }

    @Override
    public String toString() {
        return "{" +
                color + " " + entry +
                ", " + l +
                ", " + r +
                '}';
    }

    static enum Color {RED, BLACK}
}
