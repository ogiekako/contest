package net.ogiekako.algorithm.dataStructure.dynamic;

public interface DynamicTreeNode {
    void link(DynamicTreeNode parent);
    void cut();
    DynamicTreeNode root();
    void evert();
}
