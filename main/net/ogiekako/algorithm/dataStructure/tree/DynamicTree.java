package net.ogiekako.algorithm.dataStructure.tree;

public interface DynamicTree {
    void link(DynamicTree parent);
    void cut();
    DynamicTree root();
    void evert();
}
