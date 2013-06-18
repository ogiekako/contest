package net.ogiekako.algorithm.dataStructure.dynamic;


import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.SplayTree;

import java.util.List;

public class EulerTourTreeNode implements DynamicTreeNode {
    SplayTree<EulerTourTreeNode> begin;
    SplayTree<EulerTourTreeNode> end;

    public EulerTourTreeNode() {
        begin = new SplayTree<EulerTourTreeNode>(this);
        end = begin;
    }

    public void cut() {
        if (root() == this) throw new IllegalArgumentException("this is root");
        SplayTree<EulerTourTreeNode> prev = begin.previous();
        EulerTourTreeNode parent = prev.getEntry();
        // ------b--e-----
        // --l---    --r--
        SplayTree<EulerTourTreeNode> left = begin.cutLeftPath();
        SplayTree<EulerTourTreeNode> right = end.cutRightPath();
//        debug("left", left.asList());
//        debug("right", right.asList());
//        debug("this", begin.asList());
        if (right.first() == parent.end) {
//            debug("A");
            right = parent.end.next();
            parent.end = left.last();
//            debug("next right", right);
            if (right != null) {
                right.pollFirst();
                left.appendLast(right);
            }
        } else if (left.last() == parent.begin) {
//            debug("B");
            left = parent.begin.previous();
            parent.begin = right.first();
            if (left != null) {
                left.pollLast();
                left.appendLast(right);
            }
        } else {
            SplayTree<EulerTourTreeNode> exp = parent.end.first();
            EulerTourTreeNode first = parent.end.pollFirst();
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

    public void link(DynamicTreeNode _parent) {
        EulerTourTreeNode parent = (EulerTourTreeNode) _parent;
        SplayTree<EulerTourTreeNode> last = new SplayTree<EulerTourTreeNode>(parent);
        SplayTree<EulerTourTreeNode> right = parent.end.cutRightPath();
        parent.end.appendLast(begin);
        parent.end.appendLast(last);
        if (right != null) parent.end.appendLast(right);
        parent.end = last;
        check();
        parent.check();
    }

    private void check() {
        List<EulerTourTreeNode> list = begin.asList();
        if (list.get(0) != list.get(list.size() - 1)) throw new AssertionError();
        list = end.asList();
        if (list.get(0) != list.get(list.size() - 1)) throw new AssertionError();
    }

    public void evert() {
        throw new UnsupportedOperationException();
    }

    public EulerTourTreeNode root() {
        return begin.first().getEntry();
    }

    @Override
    public String toString() {
        return begin.toString();
    }
}