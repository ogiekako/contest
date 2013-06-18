package net.ogiekako.algorithm.dataStructure.dynamic.dyncon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
public class GraphConnectivity implements I {
    EulerTourTree[] F;// F0 ⊇ F1 ⊇ ... ⊇ FL
    Graph[] F2;
    Graph[] E;// E[i] = {e ∈ E: e.level == i}
    int L;
    int n;
    public GraphConnectivity(int _n) {
        this.n = _n;
        L = 32 - Integer.numberOfLeadingZeros(n - 1);
        F = new EulerTourTree[L];
        for (int i = 0; i < L; i++) F[i] = new EulerTourTree(n);
        E = new Graph[L];
        for (int i = 0; i < L; i++) E[i] = new Graph(n);
    }

    public void link(int u, int v) {
        add(0, u, v);
    }

    private void add(int level, int u, int v) {
        if (F[level].root(u) == F[level].root(v)) {
            E[level].add(u, v);
            F[level].nodes[u].addIncident(1);
            F[level].nodes[v].addIncident(1);
        } else {
            F[level].link(u, v);
            F[level].nodes[u].addIncidentTreeEdges(1);
            F[level].nodes[v].addIncidentTreeEdges(1);
        }
    }

    public void cut(int u, int v) {
        int level = 0;
        while (!E[level].has(u, v)) level++;
        cut(level, u, v);
    }
    private void cut(int level, int u, int v) {
        if (level < 0) return;
        if (F[level].has(u, v)) {
            F[level].cut(u, v);
            F[level].nodes[u].addIncidentTreeEdges(-1);
            F[level].nodes[v].addIncidentTreeEdges(-1);
            if (F[level].size(u) > F[level].size(v)) {
                int tmp = u; u = v; v = tmp;
            }
            List<Integer> candidates = F[level].candidates(u);
            for (int x : candidates) {
                if (F[level].root(x) != F[level].root(u)) throw new AssertionError();
                int removeY = -1;
                List<Integer> increase = new ArrayList<Integer>();
                for (int y : E[level].es[x]) {
                    if (F[level].root(y) == F[level].root(x)) {
                        increase.add(y);
                    } else {
                        removeY = y;
                        break;
                    }
                }
                for (int y : increase) {
                    remove(level, x, y);
                    add(level + 1, x, y);
                }
            }
        } else {
            remove(level, u, v);
        }

    }
    private void remove(int level, int x, int y) {
        if (F[level].has(x, y)) {

        }
        E[level].remove(x, y);
        F[level].nodes[x].addIncident(-1);
        F[level].nodes[y].addIncident(-1);
        if (F[level].nodes[x].incidents < 0) throw new AssertionError();
        if (F[level].nodes[y].incidents < 0) throw new AssertionError();
    }


    public boolean connected(int u, int v) {
        return F[0].root(u) == F[0].root(v);
    }
}

class Graph {
    HashSet<Integer>[] es;

    @SuppressWarnings("unchecked")
    public Graph(int n) {
        es = new HashSet[n];
        for (int i = 0; i < n; i++) es[i] = new HashSet<Integer>();
    }

    void add(int u, int v) {
        if (has(u, v)) throw new IllegalArgumentException(u + " " + v);
        es[u].add(v);
        es[v].add(u);
    }
    void remove(int u, int v) {
        if (!has(u, v)) throw new IllegalArgumentException(u + " " + v);
        es[u].remove(v);
        es[v].remove(u);
    }
    boolean has(int u, int v) {
        return es[u].contains(v);
    }
}

interface I {
    public void link(int u, int v);
    public void cut(int u, int v);
    public boolean connected(int u, int v);
}

