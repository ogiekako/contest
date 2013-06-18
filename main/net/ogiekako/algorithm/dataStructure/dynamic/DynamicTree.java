package net.ogiekako.algorithm.dataStructure.dynamic;
public interface DynamicTree {
    void cut(int u, int v);
    void link(int u, int v);
    int root(int vertex);
    void evert(int vertex);
}
