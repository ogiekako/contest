package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;

import net.ogiekako.algorithm.utils.Pair;

import java.util.ArrayList;
import java.util.List;
//http://hos.ac/slides/20120323_joi_copypaste.pdf
public class PersistentRedBlackTree<E> {

    static enum Color {RED, BLACK}

    static Color B = Color.BLACK, R = Color.RED;
    final Color color;
    final PersistentRedBlackTree<E> l;
    final PersistentRedBlackTree<E> r;
    final int rank;
    final int size;
    final E entry;

    public int size() {
        return size;
    }

    static <E> PersistentRedBlackTree<E> node(PersistentRedBlackTree<E> left, PersistentRedBlackTree<E> right, Color color) {
        return new PersistentRedBlackTree<E>(left, right, color, null);
    }

    PersistentRedBlackTree(PersistentRedBlackTree<E> l, PersistentRedBlackTree<E> r, Color color, E entry) {
        this.l = l;
        this.r = r;
        this.color = color;
        if (l != null) {
            if (l.rank != r.rank) throw new AssertionError();
            rank = l.rank + (color == B ? 1 : 0);
            size = l.size + r.size;
        } else {
            rank = 0;
            size = 1;
        }
        this.entry = entry;
    }

    public static <E> PersistentRedBlackTree<E> singleton(E e) {
        return new PersistentRedBlackTree<E>(null, null, B, e);
    }

    /*
   O( | a.rank - b.rank | )
    */
    public static <E> PersistentRedBlackTree<E> merge(PersistentRedBlackTree<E> a, PersistentRedBlackTree<E> b) {
        if (a == null) return b;
        if (b == null) return a;
        PersistentRedBlackTree<E> c = mergeSub(a, b);
        if (c.color == R) c = node(c.l, c.r, B);
        return c;
    }

    private static <E> PersistentRedBlackTree<E> mergeSub(PersistentRedBlackTree<E> a, PersistentRedBlackTree<E> b) {
        if (a.rank < b.rank) {
            PersistentRedBlackTree<E> c = mergeSub(a, b.l);
            if (c.color == R && c.l.color == R) {
                if (b.color == R) throw new AssertionError();
                if (b.r.color == B) {
                    return node(c.l, node(c.r, b.r, R), B);
                } else {
                    return node(node(c.l, c.r, B), node(b.r.l, b.r.r, B), R);
                }
            }
            return node(c, b.r, b.color);
        } else if (a.rank > b.rank) {
            PersistentRedBlackTree<E> c = mergeSub(a.r, b);
            if (c.color == R && c.r.color == R) {
                if (a.color == R) throw new AssertionError();
                if (a.l.color == B) {
                    return node(node(a.l, c.l, R), c.r, B);
                } else {
                    return node(node(a.l.l, a.l.r, B), node(c.l, c.r, B), R);
                }
            }
            return node(a.l, c, a.color);
        } else {
            return node(a, b, R);
        }
    }

    /*
    左側が葉をleafCount個含むように2つに分ける．
   O(log n)
    */
    public static <E> Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> split(PersistentRedBlackTree<E> a, int leafCount) {
        if (leafCount <= 0) return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(null, a);
        if (leafCount >= a.size) return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(a, null);
        if (a.l.size >= leafCount) {
            Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> p = split(a.l, leafCount);
            return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(p.first, merge(p.second, a.r));
        } else {
            Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>> p = split(a.r, leafCount - a.l.size);
            return new Pair<PersistentRedBlackTree<E>, PersistentRedBlackTree<E>>(merge(a.l, p.first), p.second);
        }
    }

    public static <E> PersistentRedBlackTree<E> fromList(List<E> es) {
        return fromListSub(es, 0, es.size());
    }
    private static <E> PersistentRedBlackTree<E> fromListSub(List<E> es, int from, int to) {
        if (to - from == 1) return singleton(es.get(from));
        int mid = (from + to) / 2;
        return merge(fromListSub(es, from, mid), fromListSub(es, mid, to));
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
}
