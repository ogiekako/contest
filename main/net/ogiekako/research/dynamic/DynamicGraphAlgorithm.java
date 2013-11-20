package net.ogiekako.research.dynamic;
public interface DynamicGraphAlgorithm {
    void init(int n);
    void add(int u, int v);
    void remove(int u, int v);
    int compute();
}
