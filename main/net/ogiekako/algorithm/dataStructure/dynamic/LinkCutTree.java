package net.ogiekako.algorithm.dataStructure.dynamic;

/*
[1] http://www.slideshare.net/iwiwi/2-12188845
[2] http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-851-advanced-data-structures-spring-2010/lecture-notes/MIT6_851S10_lec16.pdf
[3] http://japl.pl/contest/ijpc/3/reviews/animals2.html (deep shallow が逆)
[4] http://www.cs.cmu.edu/~avrim/451/lectures/lect1009-linkcut.txt

触ったなかで, 一番深いノードをsplayすること.
そうしないと, ならし O(log n) にならない

problems:
http://acm.hdu.edu.cn/showproblem.php?pid=2475
http://www.spoj.pl/problems/DYNACON1/ - verified
http://www.spoj.pl/problems/DYNALCA/ - verified
http://jariasf.wordpress.com/tag/dynamic-tree/
http://acm.sgu.ru/problem.php?contest=0&problem=550
http://www.acm.uestc.edu.cn/bbs/read.php?tid=5178&page=e
http://fanhq666.blog.163.com/blog/static/819434262011518104215977/


下に向かっていくものについては，特にreverseの呼び忘れに注意．
(getParent() のデバッグに非常に時間使った 2012/12/17)
*/
public class LinkCutTree implements Comparable<LinkCutTree>, DynamicTree {
    public int compareTo(LinkCutTree o) {
        return id - o.id;
    }
    @Override
    public String toString() {
        return "" + id;
    }

    LinkCutTree upper; // left
    LinkCutTree lower; // right
    /*
    パスの親を表すのにも使う.
    パスとしての親は, 二分探索木のrootが持つ.
     */
    LinkCutTree parent;
    boolean rev; // The path represented by this splay tree is reversed

    public int id;

    public LinkCutTree() {}

    public LinkCutTree(int id) {
        this.id = id;
    }
//    static void debug(Object...os){
//        System.err.println(Arrays.deepToString(os));
//    }

    // propagate rev from root.
    // call this method whenever the node is accessed.
    private void pushFromRoot() {
//        debug("pushFromRoot", this);
        boolean isRev = rev;
        LinkCutTree cur = this;
        while (!cur.isPathRoot()) {
            cur = cur.parent;
            isRev ^= cur.rev;
        }
        cur = this;
        if (isRev) reverse();
        while (!cur.isPathRoot()) {
            isRev ^= cur.rev;
            cur.rev = isRev;
            cur = cur.parent;
            if (isRev) cur.reverse();
        }
        cur.rev = false;

        // test
        cur = this;
        if (cur.rev) throw new AssertionError();
        while (!cur.isPathRoot()) {
            cur = cur.parent;
            if (cur.rev) throw new AssertionError();
        }
    }

    protected void reverse() {
//        debug("reverse", this);
        LinkCutTree tmp = upper; upper = lower; lower = tmp;
        if (upper != null) upper.rev ^= true;
        if (lower != null) lower.rev ^= true;
    }

    public LinkCutTree getParent() {
//        debug("getParent", id);
        expose();
//        debug("after expose ; ");
//        for (LinkCutTree node : nodes) {
//            debug("node :", node.id, node.rev, node.lower, node.upper, node.parent);
//        }

        if (!isPathRoot()) throw new AssertionError();
        if (upper == null) return parent;
        LinkCutTree cur = upper;
//        debug("cur.id",cur.id);
        while (cur.lower != null) {
            cur = cur.lower;
            if (cur.rev) {
                cur.reverse();
                cur.rev = false;
            }
//            debug("cur.id",cur.id);
        }
//        debug("cur.rev", cur.rev);
//        if(cur.rev)throw null;
        cur.splay();
        return cur;
    }

    public void evert() {
        expose();
        rev = true;
    }

    /*
    パスとしての二分探索木の根か
     */
    private boolean isPathRoot() {
        return parent == null || parent.upper != this && parent.lower != this;
    }

    private void splay() {
        pushFromRoot();
        if (rev) throw null;
        while (!isPathRoot()) {
            LinkCutTree p = parent, g = p.parent;
            if (p.isPathRoot()) {/*  ==null としてはダメ! パスとしての親を持つ可能性がある.  */
                if (p.upper == this) rotR();
                else rotL();
            } else if (g.upper == p) {
                if (p.upper == this) {p.rotR(); rotR();} else {rotL(); rotR();}
            } else {
                if (p.lower == this) {p.rotL(); rotL();} else {rotR(); rotL();}
            }
        }
    }

    /*
      /        3/
     p         t
    / \       / \2
   t   z  -> x   p
  / \          1/ \
 x   y         y   z
     */
    private void rotR() {
        if (rev) throw new AssertionError();
        LinkCutTree p = parent, g = p.parent;
        if ((p.upper = lower) != null) lower.parent = p; // 1
        lower = p; p.parent = this; // 2
        if ((parent = g) != null) {// 3
            if (g.upper == p) g.upper = this;
            if (g.lower == p) g.lower = this;
        }
    }

    private void rotL() {
        if (rev) throw new AssertionError();
        LinkCutTree p = parent, g = p.parent;
        if ((p.lower = upper) != null) upper.parent = p;
        upper = p; p.parent = this;
        if ((parent = g) != null) {
            if (g.upper == p) g.upper = this;
            if (g.lower == p) g.lower = this;
        }
    }

    /*
    v=this の根へのパスを繋げる.(vより下は切る)
    返ったとき, vはsplay treeにおいて，根になっている.

    まず, vをsplayし, 右側(下の方)は切る.
    1つのステップでは, vの親をsplayして, 右側にvをくっつける.
    [1]p21参照
     */
    void expose() {
        for (LinkCutTree r = null, p = this; p != null; r = p, p = p.parent) {
            p.splay();// p.right : lower nodes
            if (p.rev)
                throw null;
            p.lower = r;// connect path
        }
        splay();// for convenience
        if (parent != null) throw new AssertionError();// this is root
        if (lower != null) throw new AssertionError();// there is no lower node
    }

    /*
    親への枝をなくす. 親がいなかった場合は単に無視される.

    exposeして, 根へのパスをつなげ,
    より浅いノードたちへの枝を切ればよい.

    単にsplayしてからcutすると, パスの親の処理がうまくできない.
     */
    public void cut() {
        expose();// left : upper nodes
        if (rev) throw null;
        if (upper != null) {
            upper.parent = null; upper = null;
        }
    }

    /*
    parentとは異なる木に属していないといけない.

    また, this が根でないときは, thisの属する木のrootを, parentにくっつけることになる.

     */
    public void link(DynamicTree parent) {
        LinkCutTree _parent = (LinkCutTree) parent;
        if (root() != this) throw new AssertionError();
        expose();// no parent
        _parent.expose();// no right
        if (!isPathRoot()) throw new IllegalStateException("not in distinct trees");
        if (_parent.rev) throw null;
        _parent.lower = this;
        if (this.parent != null) throw new AssertionError();
        this.parent = _parent;
    }

    public LinkCutTree root() {
        expose();// already reversed by expose.
        LinkCutTree res = this;
        while (res.upper != null) res = res.upper;
        res.splay();
        return res;
    }

////////////////////////////
/////  utility methods /////
////////////////////////////

    // verified, O(log n)
    public LinkCutTree lowestCommonAncestor(LinkCutTree other) {
        LinkCutTree a = this;
        if (a == other) return a;
        LinkCutTree ra = a.root();
        LinkCutTree rb = other.root();
        if (ra != rb) return null;
        LinkCutTree pr = a.pathRoot();
        if (pr != rb) {
            a.splay(); return a.parent;
        }
        a.expose();
        other.splay(); return other.parent;
    }

    private LinkCutTree pathRoot() {
        splay();
        LinkCutTree res = this;
        while (res.upper != null) res = res.upper;
        res.splay();
        return res;
    }

    public boolean isConnectedTo(LinkCutTree other) {
        return root() == other.root();
    }

    public LinkCutTree secondRoot() {
        expose();// already reversed by expose.
//        if(isRoot())return null; 不用意にやって状態を壊してた
        LinkCutTree res = this;
        while (res.upper != null) res = res.upper;
        if (res.lower == null) {
            res = res.parent;
            if (res != null) res.splay();
            return res;
        }
        res = res.lower;
        while (res.upper != null) res = res.upper;
        res.splay();
        return res;
    }

    public boolean isRoot() {
        return root() == this;
    }
}
