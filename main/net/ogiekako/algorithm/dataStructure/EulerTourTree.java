package net.ogiekako.algorithm.dataStructure;


import net.ogiekako.algorithm.dataStructure.tree.DynamicTree;
import net.ogiekako.algorithm.dataStructure.tree.SplayTree;

import java.util.List;

public class EulerTourTree implements DynamicTree {
    SplayTree<EulerTourTree> begin;
    SplayTree<EulerTourTree> end;

    public EulerTourTree() {
        begin = new SplayTree<EulerTourTree>(this);
        end = begin;
    }

    public void cut() {
        if (root() == this) throw new IllegalArgumentException("this is root");
        SplayTree<EulerTourTree> prev = begin.previous();
        EulerTourTree parent = prev.getEntry();
        // ------b--e-----
        // --l---    --r--
        SplayTree<EulerTourTree> left = begin.cutLeftPath();
        SplayTree<EulerTourTree> right = end.cutRightPath();
//        debug("left", left.asList());
//        debug("right", right.asList());
//        debug("this", begin.asList());
        if (right.first() == parent.end) {
//            debug("A");
            right = parent.end.next();
            parent.end = left.last();
//            debug("next right", right);
            if (right != null){
                right.pollFirst();
                left.appendLast(right);
            }
        } else if (left.last() == parent.begin) {
//            debug("B");
            left = parent.begin.previous();
            parent.begin = right.first();
            if (left != null){
                left.pollLast();
                left.appendLast(right);
            }
        } else {
            SplayTree<EulerTourTree> exp = parent.end.first();
            EulerTourTree first = parent.end.pollFirst();
            if (exp.getEntry() != first) throw new AssertionError();
//            debug("first", exp.asList());
//            debug("rest", parent.end.asList());
            parent.begin.appendLast(parent.end);
        }
//        debug("result:");
//        debug("left", parent.begin.asList());
//        debug("right", parent.end.asList());
//        debug("this", begin.asList());
        check();
        parent.check();
    }

    public void link(DynamicTree _parent) {
        EulerTourTree parent = (EulerTourTree) _parent;
        SplayTree<EulerTourTree> last = new SplayTree<EulerTourTree>(parent);
        SplayTree<EulerTourTree> right = parent.end.cutRightPath();
        parent.end.appendLast(begin);
        parent.end.appendLast(last);
        if (right != null) parent.end.appendLast(right);
        parent.end = last;
        check();
        parent.check();
    }

    private void check() {
        List<EulerTourTree> list = begin.asList();
        if (list.get(0) != list.get(list.size() - 1)) throw new AssertionError();
        list = end.asList();
        if (list.get(0) != list.get(list.size() - 1)) throw new AssertionError();
    }

    public void evert() {

    }

    public EulerTourTree root() {
        return begin.first().getEntry();
    }

    @Override
    public String toString() {
        return begin.toString();
    }

//    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
//    }
}