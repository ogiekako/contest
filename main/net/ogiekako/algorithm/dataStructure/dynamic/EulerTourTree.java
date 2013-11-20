package net.ogiekako.algorithm.dataStructure.dynamic;
import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.BalancedBinarySearchTree;
import net.ogiekako.algorithm.dataStructure.balancedBinarySearchTree.Treap;

import java.util.HashMap;
import java.util.Map;
public class EulerTourTree implements DynamicTree {
    int n;

    Map<Integer, BalancedBinarySearchTree.Node<Integer>> map[];

    @SuppressWarnings("unchecked")
    public EulerTourTree(int n) {
        this.n = n;
        map = new Map[n];
        for (int i = 0; i < n; i++) map[i] = new HashMap<Integer, BalancedBinarySearchTree.Node<Integer>>();
    }

    private BalancedBinarySearchTree.Node<Integer> getNode(int v) {
        if (map[v].isEmpty()) return null;
        return map[v].entrySet().iterator().next().getValue();// O(1)
    }

    public void link(int u, int v) {
        if (root(u) == root(v)) throw new IllegalArgumentException(u + " " + v);
        BalancedBinarySearchTree.Node<Integer> U = getNode(u);
        BalancedBinarySearchTree<Integer> treeU = U == null ? new Treap<Integer>() : U.getTree();
        if (U != null) { // --U--
            int uId = treeU.indexOf(U);
            BalancedBinarySearchTree<Integer> rightU = treeU.split(uId);// -- | U--
            rightU.merge(treeU);// U----
            treeU = rightU;
        }
        Treap<Integer> newUTreap = new Treap<Integer>(u);
        BalancedBinarySearchTree.Node<Integer> newU = newUTreap.get(0);// U'
        treeU.merge(newUTreap);// U----U' / U'
        BalancedBinarySearchTree.Node<Integer> V = getNode(v);
        BalancedBinarySearchTree<Integer> treeV = V == null ? new Treap<Integer>() : V.getTree();// --V--  / φ
        BalancedBinarySearchTree<Integer> rightV;
        if (V != null) {
            int vId = treeV.indexOf(V);
            rightV = treeV.split(vId);// -- | V--
        } else {
            rightV = new Treap<Integer>();// φ | φ
        }
        Treap<Integer> newVTreap = new Treap<Integer>(v);
        BalancedBinarySearchTree.Node<Integer> newV = newVTreap.get(0);// V'
        treeV.merge(newVTreap);// --V' | V--
        treeV.merge(treeU); // --V'U----U' | V--
        treeV.merge(rightV);// --V'U----U'V--
        map[u].put(v, newU);
        map[v].put(u, newV);
    }

    public void cut(int u, int v) {
        if (!map[u].containsKey(v)) throw new IllegalArgumentException(u + " " + v);
        BalancedBinarySearchTree.Node<Integer> U = map[u].get(v);// U'V
        BalancedBinarySearchTree.Node<Integer> V = map[v].get(u);// V'U
        BalancedBinarySearchTree<Integer> tree = V.getTree();  // --V'U----U'V--
        int uId = tree.indexOf(V) + 1;
        if (uId < tree.size()) {
            BalancedBinarySearchTree<Integer> right = tree.split(uId);// --V' | U----U'V--
            right.merge(tree);
            tree = right; // U----U'V----V'
        }
        int vId = tree.indexOf(U) + 1;
        BalancedBinarySearchTree<Integer> right = tree.split(vId);// U----U' | V----V'
        tree.split(tree.size() - 1); // U----
        right.split(right.size() - 1); // V----
        map[u].remove(v);
        map[v].remove(u);
    }
    /**
     * Make the vertex v a root.
     */
    public void evert(int v) {
        BalancedBinarySearchTree.Node<Integer> V = getNode(v);
        if (V == null) return;
        BalancedBinarySearchTree<Integer> tree = V.getTree();
        int vId = tree.indexOf(V);
        BalancedBinarySearchTree<Integer> right = tree.split(vId);// -- | V--
        right.merge(tree);// V----
    }

    public int root(int v) {
        BalancedBinarySearchTree.Node<Integer> V = getNode(v);
        if (V == null) return v;
        BalancedBinarySearchTree<Integer> tree = V.getTree();
        return tree.get(0).value();
    }
}
