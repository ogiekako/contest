package net.ogiekako.algorithm.dataStructure;

import java.util.Arrays;

public class UnionFind {
    public final int[] tree;

    public UnionFind(int n) {
        this.tree = new int[n];
        Arrays.fill(tree, -1);
    }

    public UnionFind(int[] tree) {
        if (tree == null) throw new NullPointerException();
        this.tree = tree;
    }

    /**
     * @return true if x and y are already in a same component.
     */
    public boolean union(int x, int y) {
        x = root(x);
        y = root(y);
        if (x == y) return true;
        if (tree[x] > tree[y]) {
            tree[y] += tree[x];
            tree[x] = y;
        } else {
            tree[x] += tree[y];
            tree[y] = x;
        }
        return false;
    }

    public boolean find(int x, int y) {
        return root(x) == root(y);
    }

    public int root(int x) {
        return tree[x] < 0 ? x : (tree[x] = root(tree[x]));
    }

    public int size(int x) {
        return -tree[root(x)];
    }
}
