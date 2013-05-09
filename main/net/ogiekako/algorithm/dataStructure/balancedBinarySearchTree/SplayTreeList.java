package net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree;

import net.ogiekako.algorithm.dataStructure.tree.QueryableList;
import net.ogiekako.algorithm.dataStructure.tree.SimpleList;
public abstract class SplayTreeList<E> implements QueryableList<E> {
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    protected abstract E join(E left, E right);

    protected abstract SplayTreeList<E> create();

    protected abstract E identity();

    public E query(int from, int to) {
//        if(from > to)throw new IllegalArgumentException(from + " " + to);
//        if(from == to)return identity();
//        int prevSize = size();
        SplayTreeList<E> part = cut(from, to);
//        if(part.size() != to - from)throw new AssertionError();
//        if(size() != prevSize - (to - from))throw new AssertionError();
        E answer = part.root.answer;
        insert(from, part);
//        if(size() != prevSize)throw new AssertionError();
        return answer;
    }

    Node root;

    public void add(E value) {
        if (root == null) {
            root = new Node(value);
            return;
        }
        int size = root.getSize();
        debug("add", size);
        root = root.appendLast(new Node(value));
        debug(root.getSize());
    }

    public int size() {
        if (root == null) return 0;
        return root.getSize();
    }

    public void set(int i, E value) {
        setRoot(i);
        root.entry = value;
        root.update();
    }

    public SplayTreeList<E> cut(int from, int to) {
        SplayTreeList<E> right = cut(to);
        SplayTreeList<E> res = cut(from);
        append(right);
        return res;
    }

    public SplayTreeList<E> cut(int from) {
        if (from > size()) throw new ArrayIndexOutOfBoundsException(from);
        SplayTreeList<E> res = create();
        if (from == 0) {
            res.root = this.root;
            this.root = null;
            return res;
        }
        setRoot(from - 1);
        res.root = root.cutRightPath();
        return res;
    }

    public void append(SimpleList<E> other) {
        if (other.size() == 0) return;
        if (size() == 0) root = cast(other).root;
        else {
            int size = size(), oSize = other.size();
            root = root.appendLast(cast(other).root);
            if (size() != size + oSize) throw new AssertionError(size() + " " + size + " " + oSize);
        }
    }

    public void insert(int i, SimpleList<E> other) {
        int size = size(), oSize = other.size();
        SplayTreeList<E> right = cut(i);
        if (size() + right.size() != size) throw new AssertionError();
        append(other);
        if (size() + right.size() != size + oSize) throw new AssertionError();
        append(right);
        if (size() != size + oSize) throw new AssertionError(size() + " " + size + " " + oSize);
    }

    public E get(int i) {
        setRoot(i);
        return root.getEntry();
    }

    private void setRoot(int i) {
        if (i < 0 || i >= size()) throw new ArrayIndexOutOfBoundsException(i + " " + size());
        root = root.getKth(i);
    }

    private static <V> SplayTreeList<V> cast(SimpleList<V> root) {
        return (SplayTreeList<V>) root;
    }

    class Node {
        Node parent;
        Node left;
        Node right;
        E entry;
        E answer;
        int size;

        public Node(E entry) {
            this.entry = entry;
            this.answer = entry;
            this.size = 1;
        }

        // O(log n)
        public Node getKth(int k) {
            if (k < 0 || k >= size) throw new ArrayIndexOutOfBoundsException(k + " " + size);
            Node cur = this;
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

        public int getSize() {
            return size;
        }

        public boolean isRoot() {
            return parent == null;
        }

        Node splay() {
            while (!isRoot()) {
                Node p = parent;
                if (p.isRoot()) {
                    if (p.left == this) rotR();
                    else rotL();
                } else {
                    Node g = parent.parent;
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
         */
        void rotR() {
            Node t = this, p = parent, g = parent.parent, x = left, y = right, z = p.right;
            p.left = y;
            if (y != null) y.parent = p;// 1
            t.right = p;
            p.parent = t;// 2
            t.parent = g;
            if (g != null) {// 3
                if (g.right == p) g.right = t;
                if (g.left == p) g.left = t;
            }

            p.update();
            t.update();
            debug("rotR", p.size, size);
        }

        /*
          /          3/
         p           t
        / \        2/ \
       z   t  ->   p   x
          / \     / \1
         y   x   z   y
         */
        void rotL() {
            Node t = this, p = parent, g = parent.parent, x = right, y = left, z = p.left;
            p.right = y;
            if (y != null) y.parent = p;// 1
            t.left = p;
            p.parent = t;// 2
            t.parent = g;
            if (g != null) {// 3
                if (g.right == p) g.right = t;
                if (g.left == p) g.left = t;
            }

            p.update();
            t.update();
            debug("rotL", p.size, size);
        }

        private E getEntry() {
            return entry;
        }

        // thisが含まれるpathに，otherが含まれるpathをくっつける
        // append the path represented by the tree that contains 'other'
        // to the path represented by the tree that contains 'this'.
        private Node appendLast(Node other) {
            splay();
            other.splay();
            Node cur = this;
            while (cur.right != null) cur = cur.right;
            cur.addRight(other);
            Node deepest = cur;
            while (!cur.isRoot()) {
                cur.update();
                cur = cur.parent;
            }
            cur.update();
            return deepest.splay();
        }

        private void addRight(Node other) {
            if (right != null) throw new AssertionError();
            right = other;
            other.parent = this;
        }

        private void update() {
            size = 1 + size(left) + size(right);
            answer = join(answer(left), join(entry, answer(right)));
        }

        private E answer(Node node) {
            return node == null ? identity() : node.answer;
        }

        // (thisを含めない)右側をカットして，thisを含まない方のrootを返す
        public Node cutRightPath() {
            splay();
            Node res = right;
            removeRight();
            return res;
        }

        private void removeRight() {
            if (right == null) return;
            if (right.parent != this) throw new AssertionError();
            right.parent = null;
            this.size -= right.size;
            right = null;
            update();
        }

        private int size(Node t) {
            return t == null ? 0 : t.getSize();
        }

    }


    public static class Min extends SplayTreeList<Integer> {
        @Override
        protected Integer identity() {
            return Integer.MAX_VALUE;
        }

        @Override
        protected Integer join(Integer left, Integer right) {
            return Math.min(left, right);
        }

        @Override
        protected SplayTreeList<Integer> create() {
            return new Min();
        }
    }

    public static class Sum extends SplayTreeList<Integer> {
        @Override
        protected Integer join(Integer left, Integer right) {
            return left + right;
        }

        @Override
        protected SplayTreeList<Integer> create() {
            return new Sum();
        }

        @Override
        protected Integer identity() {
            return 0;
        }
    }
}
