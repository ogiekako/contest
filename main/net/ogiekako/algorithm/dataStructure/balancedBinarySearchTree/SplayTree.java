package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考:
 * [1]http://www.cs.unc.edu/~plaisted/comp750/08-splay.ppt ... ならし計算量の意味が Stackを例にわかりやすい.
 * [2]http://sd.is.uec.ac.jp/koga/lecture/IF2/lec5.pdf ... ポテンシャルで解説.
 * [3]http://www.slideshare.net/iwiwi/2-12188845 ... iwiwiさん
 * <p/>
 * insert, delete, search が, O(log n) の償却計算量.
 * <p/>
 * アルゴリズム:
 * SplayTreeは, アクセスされるたびに, 最後にアクセスされた頂点xに対して, splay(x) 操作を行い, xを根に持ってくる.
 * <p/>
 * splay操作では, xが, 左左, 右右 だったら, zigzig 操作を行う. 左左 → 右右 にする感じ.
 * そうでないとき, 普通の回転を行う.
 * <p/>
 * 計算量解析:
 * これで, 償却計算量が, O(log n) になることを示そう. 以下のようにならし計算量を定義する.
 * rank(v) = lg(vを頂点とする木の頂点数)
 * pot(T) = sum rank(v) ... ポテンシャル
 * A = R + pot(T') - pot(T) ... ならし計算量
 * R : 操作にかかった実際のコスト
 * <p/>
 * この値の総和は, mA = mR + pot(T) - pot(empty) となる.
 * よって, mR <= mA.
 * A = O(log n) を言えればよい.
 * <p/>
 * Aを更に, 一回の操作ごとに分解する.
 * A = sum A_i
 * A_i = 2 + pot(T_i+1) - pot(T_i).
 * xをsplayしているノードとすると,zig以外では,
 * A_i <= 3(r'(x) - r(x)) がいえる.([2] などを参照)
 * これの総和を考えると,
 * A <= 3log n + 1.
 * よって, 償却計算量が, O(log n) がいえた.
 * <p/>
 * problems:
 * JULY12 RRATING
 */

public class SplayTree<E> {
    SplayTree<E> parent;
    SplayTree<E> left;
    SplayTree<E> right;
    E entry;
    int size = 1;

    public SplayTree(E entry) {
        this.entry = entry;
    }

    // O(log n)
    public SplayTree insertAndGetNewRoot(E key) {
        Comparable<E> _key = (Comparable<E>) key;
        SplayTree<E> cur = this;
        for (; ; ) {
            cur.size++;
            if (_key.compareTo(cur.entry) < 0) {
//            if(key < cur.key){
                if (cur.left == null) {
                    SplayTree res = cur.left = new SplayTree(key);
                    cur.left.parent = cur;
                    cur.left.splay();
                    return res;
                }
                cur = cur.left;
            } else {
                if (cur.right == null) {
                    SplayTree res = cur.right = new SplayTree(key);
                    cur.right.parent = cur;
                    cur.right.splay();
                    return res;
                }
                cur = cur.right;
            }
        }
    }

    // O(log n)
    public SplayTree getKth(int k) {
        SplayTree cur = this;
        for (; ; ) {
            int leftSize = size(cur.left);
            if (leftSize == k) {
                cur.splay();
                return cur;
            } else if (leftSize > k) {
                cur = cur.left;
            } else {
                cur = cur.right;
                k -= leftSize + 1;
            }
        }

    }

    // O(1)
    public int getSize() {
        return size;
    }

    public boolean isRoot() {
        return parent == null;
    }

    SplayTree splay() {
        while (!isRoot()) {
            SplayTree p = parent;
            if (p.isRoot()) {
                if (p.left == this) rotR();
                else rotL();
            } else {
                SplayTree g = parent.parent;
                if (g.left == p) {
                    if (p.left == this) {
                        p.rotR(); rotR();
                    } else {
                        rotL(); rotR();
                    }
                } else {
                    if (p.right == this) {
                        p.rotL(); rotL();
                    } else {
                        rotR(); rotL();
                    }
                }
            }
        }
        return this;
    }

    /*
      /        3/
     p         t
    / \       / \2
   t   z  -> x   p
  / \          1/ \
 x   y         y   z

 t.size += z.size + 1
 p.size -= x.size + 1
     */
    void rotR() {
        SplayTree q = parent, g = parent.parent;
        if ((parent.left = right) != null) right.parent = q;// 1
        q.parent = this; right = q; // 2
        if ((parent = g) != null) {// 3
            if (g.right == q) g.right = this;
            if (g.left == q) g.left = this;
        }
        // size
        size += size(q.right) + 1;
        q.size -= size(left) + 1;
    }

    void rotL() {
        SplayTree q = parent, g = parent.parent;
        if ((parent.right = left) != null) left.parent = q;
        q.parent = this;
        left = q;
        if ((parent = g) != null) {
            if (g.right == q) g.right = this;
            if (g.left == q) g.left = this;
        }
        // size
        size += size(q.left) + 1;
        q.size -= size(right) + 1;
    }

    private static int size(SplayTree t) {
        return t == null ? 0 : t.getSize();
    }

    //    public int getKey() {
//        return key;
//    }
    public E getEntry() {
        return entry;
    }

    // thisが含まれるpathに，otherが含まれるpathをくっつける
    public void appendLast(SplayTree<E> other) {
        splay();
        other.splay();
        SplayTree<E> cur = this;
        while (cur.right != null) cur = cur.right;
        cur.addRight(other);
    }

    private void addRight(SplayTree<E> other) {
        if (right != null) throw new AssertionError();
        right = other;
        other.parent = this;
    }

    public SplayTree<E> previous() {
        splay();
        SplayTree<E> res = left;
        if (res == null) return res;
        while (res.right != null) res = res.right;
        return res;
    }
    public SplayTree<E> next() {
        splay();
        SplayTree<E> res = right;
        if (res == null) return res;
        while (res.left != null) res = res.left;
        return res;
    }

    // (thisを含めない)左側をカットして，thisを含まない方のrootを返す
    public SplayTree<E> cutLeftPath() {
        splay();
        SplayTree<E> res = left;
        removeLeft();
        return res;
    }
    // (thisを含めない)右側をカットして，thisを含まない方のrootを返す
    public SplayTree<E> cutRightPath() {
        splay();
        SplayTree<E> res = right;
        removeRight();
        return res;
    }

    private void removeLeft() {
        if (left == null) return;
        if (left.parent != this) throw new AssertionError();
        left.parent = null;
        size -= left.size;
        left = null;
    }

    private void removeRight() {
        if (right == null) return;
        if (right.parent != this) throw new AssertionError();
        right.parent = null;
        size -= right.size;
        right = null;
    }

    public SplayTree<E> first() {
        splay();
        SplayTree<E> res = this;
        while (res.left != null) res = res.left;
        res.splay();
        return res;
    }

    public SplayTree<E> last() {
        splay();
        SplayTree<E> res = this;
        while (res.right != null) res = res.right;
        res.splay();
        return res;
    }

    public E pollFirst() {
        SplayTree<E> first = first();
        first.splay();
        first.removeRight();
        return first.getEntry();
    }
    public E pollLast() {
        SplayTree<E> last = last();
        last.splay();
        last.removeLeft();
        return last.getEntry();
    }
    // thisを含むパスを返す
    public List<E> asList() {
        splay();
        return asList0();
    }
    private List<E> asList0() {
        List<E> res = left == null ? new ArrayList<E>() : left.asList0();
        res.add(getEntry());
        if (right != null) res.addAll(right.asList0());
        return res;
    }


    public boolean containsNaive(E entry) {
        return getEntry().equals(entry) || left != null && left.containsNaive(entry) || right != null && right.containsNaive(entry);
    }
}
