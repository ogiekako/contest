package net.ogiekako.algorithm.dataStructure.dynamic.dyncon;
import net.ogiekako.algorithm.dataStructure.dynamic.DynamicTree;
import net.ogiekako.algorithm.utils.Pair;

import java.util.*;
public class EulerTourTree implements DynamicTree {
    int n;

    Map<Integer, Treap> es[];
    Treap[] nodes;

    @SuppressWarnings("unchecked")
    public EulerTourTree(int n) {
        this.n = n;
        es = new Map[n];
        for (int i = 0; i < n; i++) es[i] = new HashMap<Integer, Treap>();
        nodes = new Treap[n];
    }

    private Treap getTreap(int v) {
        if (es[v].isEmpty()) return null;
        return es[v].entrySet().iterator().next().getValue();// O(1)
    }

    public void link(int u, int v) {
        if (root(u) == root(v)) throw new IllegalArgumentException(u + " " + v);
        Treap U = getTreap(u);
        Treap treeU = U == null ? null : U.root();
        if (U != null) { // --U--
            int uId = Treap.indexOf(U);
            Pair<Treap, Treap> pair = treeU.split(uId);
            treeU = pair.first;
            Treap rightU = pair.second;// -- | U--
            rightU = rightU.merge(treeU);// U----
            treeU = rightU;
        }
        Treap newUTreap = new Treap(u, 0);
        Treap newU = newUTreap.get(0);// U'
        treeU = Treap.merge(treeU, newUTreap);// U----U' / U'
        Treap V = getTreap(v);
        Treap treeV = V == null ? null : V.root();// --V--  / φ
        Treap rightV;
        if (V != null) {
            int vId = Treap.indexOf(V);
            Pair<Treap, Treap> pair = treeV.split(vId);
            treeV = pair.first;
            rightV = pair.second;// -- | V--
        } else {
            rightV = null;// φ | φ
        }
        Treap newVTreap = new Treap(v, 0);
        Treap newV = newVTreap.get(0);// V'
        treeV = Treap.merge(treeV, newVTreap);// --V' | V--
        treeV = treeV.merge(treeU); // --V'U----U' | V--
        treeV.merge(rightV);// --V'U----U'V--
        es[u].put(v, newU);
        es[v].put(u, newV);
        if (nodes[u] == null) nodes[u] = newU;
        if (nodes[v] == null) nodes[v] = newV;
    }

    public void cut(int u, int v) {
        if (!es[u].containsKey(v)) throw new IllegalArgumentException(u + " " + v);
        Treap U = es[u].get(v);// U'V
        Treap V = es[v].get(u);// V'U
        Treap tree = V.root();  // --V'U----U'V--
        int uId = Treap.indexOf(V) + 1;
        if (uId < tree.size()) {
            Pair<Treap, Treap> pair = tree.split(uId);
            tree = pair.first;
            Treap right = pair.second;// --V' | U----U'V--
            right = right.merge(tree);
            tree = right; // U----U'V----V'
        }
        int vId = Treap.indexOf(U) + 1;
        Pair<Treap, Treap> pair = tree.split(vId);
        tree = pair.first;
        Treap right = pair.second;// U----U' | V----V'
        tree = tree.split(tree.size() - 1).first; // U----
        right = right.split(right.size() - 1).first; // V----
        es[u].remove(v);
        es[v].remove(u);
        if (nodes[u] == U) {
            if (tree == null) nodes[u] = null;
            else {
                nodes[u] = tree.get(0);
                nodes[u].addIncident(U.incidents);
            }
        }
        if (nodes[v] == V) {
            if (right == null) nodes[v] = null;
            else {
                nodes[v] = right.get(0);
                nodes[v].addIncident(V.incidents);
            }
        }
    }
    /**
     * Make the vertex v a root.
     */
    public void evert(int v) {
        Treap V = getTreap(v);
        if (V == null) return;
        Treap tree = V.root();
        int vId = Treap.indexOf(V);
        Pair<Treap, Treap> pair = tree.split(vId);
        tree = pair.first;
        Treap right = pair.second;// -- | V--
        right.merge(tree);// V----
    }

    public int root(int v) {
        Treap V = getTreap(v);
        if (V == null) return v;
        Treap tree = V.root();
        return tree.get(0).vertex;
    }

    /**
     * @return true if this tree contains edge (u,v).
     */
    public boolean has(int u, int v) {
        return es[u].containsKey(v);
    }
    public int size(int v) {
        if (nodes[v] == null) return 1;
        return nodes[v].root().size();
    }

    /**
     * @param v
     * @return a list of indices of vertices that have at least one incident edge whose level is same as the level of this forest.
     */
    public List<Integer> candidates(int v) {
        if (nodes[v] == null) return Collections.emptyList();
        List<Integer> res = new ArrayList<Integer>();
        nodes[v].candidates(res);
        return res;
    }
}
