package src;

import net.ogiekako.algorithm.dataStructure.dynamic.EulerTourTreeNode;
import net.ogiekako.algorithm.graph.SimpleEdge;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.List;

public class TaskDYNACON2 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), Q = in.nextInt();
        DynamicGraphConnectivity graph = new DynamicGraphConnectivity(N);
        for (int iter = 0; iter < Q; iter++) {
            String type = in.next();
            int A = in.nextInt() - 1, B = in.nextInt() - 1;
            if (type.equals("add")) {
                graph.add(A, B);
            } else if (type.equals("rem")) {
                graph.remove(A, B);
            } else if (type.equals("conn")) {
                boolean res = graph.query(A, B);
                out.println(res ? "YES" : "NO");
            } else throw new AssertionError();
        }
    }

    class DynamicGraphConnectivity {
        final int N;
        int L;
        EulerTourTreeNode[] F;// F0 ⊇ F1 ⊇ ... ⊇ FL
        public DynamicGraphConnectivity(int N) {
            this.N = N;
            L = 32 - Integer.numberOfLeadingZeros(N);
            for (int i = 0; i < L; i++) {
                F[i] = new EulerTourTreeNode();
            }
        }
        public void add(int x, int y) {

        }
        public void remove(int x, int y) {

        }
        public boolean query(int x, int y) {
            return true;
        }
    }

    class Forest implements Listener {
        int N;
        int level;
        Node[] nodes;

        Forest(int N, int level) {
            this.N = N;
            this.level = level;
            nodes = new Node[N];
            for (int i = 0; i < N; i++) nodes[i] = new Node();
        }
        void link(int x, int y) {
            if (nodes[x].root() == nodes[y].root()) throw new IllegalArgumentException(x + " " + y);
            nodes[x].link(nodes[y]);
        }
        void cut(int x, int y) {
            nodes[x].evert();
            nodes[y].cut();
        }
        List<LeveledEdge> edgesIncidentToTree(int x) {
            List<LeveledEdge> list = new ArrayList<LeveledEdge>();
            nodes[x].evert();
//            nodes[x].traverse(list);
            return list;
        }
        public void update(LeveledEdge e) {
            if (e.level() - 1 == this.level) {
//                nodes[e.from()].inc();
//                nodes[e.to()].inc();
                e.from();
            } else if (e.level() == this.level) {

            }
        }
    }

    class LeveledEdge extends SimpleEdge {
        int level = 0;
        List<Listener> listeners = new ArrayList<Listener>();
        int level() {
            return level;
        }
        LeveledEdge(int from, int to) {
            super(from, to);
        }
        void incLevel() {
            level++;
            for (Listener listener : listeners) listener.update(this);
        }
    }

    interface Listener {
        void update(LeveledEdge e);
    }

    class Node extends EulerTourTreeNode {

    }
}
