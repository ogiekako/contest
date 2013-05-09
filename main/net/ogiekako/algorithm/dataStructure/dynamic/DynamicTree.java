package net.ogiekako.algorithm.dataStructure.dynamic;

public interface DynamicTree {
    void link(DynamicTree parent);
    void cut();
    DynamicTree root();
    void evert();
}
