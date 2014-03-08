package net.ogiekako.research.dynamic;
import org.junit.Assert;

import java.util.*;
public class _UndirectedGraph {
    int n;
    HashSet<Integer>[] G;
    public void init(int n) {
        this.n = n;
        G = new HashSet[n];
        for (int i = 0; i < n; i++) {
            G[i] = new HashSet<>();
        }
    }
    public int d(int v) {
        return G[v].size();
    }
    public void add(int u, int v) {
        Assert.assertNotSame(u + " " + v, u, v);
        boolean valid;
        valid = G[u].add(v);
        valid &= G[v].add(u);
        if (!valid) throw new IllegalArgumentException(u + " " + v);
    }
    public void remove(int u, int v) {
        Assert.assertNotSame(u + " " + v, u, v);
        boolean valid;
        valid = G[u].remove(v);
        valid &= G[v].remove(u);
        if (!valid) throw new IllegalArgumentException(u + " " + v);
    }

    public Set<Integer> N(int u) {
        return Collections.unmodifiableSet(G[u]);
    }
    public boolean contains(int u, int v) {
        return G[u].contains(v);
    }
    public int size() {
        return n;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int u = 0; u < n; u++) {
            List<Integer> Nu = new ArrayList<>();
            for (int v : G[u])
                if (u < v) {
                    Nu.add(v);
                }
            if (!Nu.isEmpty()) {
                res.append(u + " - " + Nu + "\n");
            }
        }
        return res.toString();
    }
}
