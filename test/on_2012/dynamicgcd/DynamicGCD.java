package on_2012.dynamicgcd;


import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.SimpleEdge;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.*;

/*
��ōl����D
b_i = a_{i+1} - a_i �Ƃ���D
[a_s, a_t) �� d ���Z����
�� b_{s-1} += d, b_{t-1} -= d.
gcd [a_s,a_t] = gcd(a_s, gcd [b_s, b_t) ).

b���x�[�X�ɂ��čl���āCsegTree�ŁC��Ԃ�gcd���Ǘ����Ă����΁C��Ɋւ��Ă͂ł����DO(log n)

http://topcoder.g.hatena.ne.jp/iwiwi/20111205/1323099376
�́CHeavy-Light Decomposition ���g���΁C
�؂Ɋւ��Ă��ł���DO(log n).

*/
public class DynamicGCD {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[][] edge = new int[N - 1][2];
        for (int i = 0; i < N - 1; i++) for (int j = 0; j < 2; j++) edge[i][j] = in.nextInt();
        int[] initialValues = new int[N];
        for (int i = 0; i < N; i++) initialValues[i] = in.nextInt();
        int Q = in.nextInt();
        int[][] queries = new int[Q][4];
        for (int i = 0; i < Q; i++) {
            queries[i][0] = in.nextChar() == 'C' ? 0 : 1;
            for (int j = 0; j < 3 - queries[i][0]; j++) {
                queries[i][j + 1] = in.nextInt();
            }
        }
        for (int i : solve(N, edge, initialValues, Q, queries)) {
            out.println(i);
        }
    }

//    long startTime;

//    void showTime(String s) {
//        System.err.println(s + " " + (System.currentTimeMillis() - startTime));
//    }

    int[] solve(int N, int[][] edge, int[] initialValues, int Q, int[][] queries) {
//        startTime = System.currentTimeMillis();
        Node[] nodes = new Node[N];
        for (int i = 0; i < N; i++) nodes[i] = new Node();
        for (int i = 0; i < N; i++) nodes[i].id = i;
        for (int i = 0; i < N - 1; i++) {
            int u = edge[i][0], v = edge[i][1];
            nodes[u].neighbors.add(nodes[v]); nodes[v].neighbors.add(nodes[u]);
        }
        int pathCount = doIt(nodes[0]);

//        for (Node node : nodes) debug(node);

//        makeRootedTree(nodes[0]);
//        showTime("A1");
//        setSize(nodes[0]);
//        showTime("A2");
//        setDepth(nodes[0]);
//        showTime("A3");
//        int pathCount = decompose(nodes[0]);
        int[] pathSize = new int[pathCount];
        for (Node node : nodes) pathSize[node.outerIndex] = Math.max(pathSize[node.outerIndex], node.innerIndex + 1);
        Seg[] arrayA = new Seg[pathCount];
        SegGCD[] arrayB = new SegGCD[pathCount];
//        for (int i = 0; i < pathCount; i++) {
//            arrayA[i] = new Seg(pathSize[i]);
//        }

//        showTime("A");
//        startTime = System.currentTimeMillis();

        int[][] initValueOnArray = new int[pathCount][];
        for (int i = 0; i < pathCount; i++) initValueOnArray[i] = new int[pathSize[i]];
        for (int i = 0; i < N; i++) initValueOnArray[nodes[i].outerIndex][nodes[i].innerIndex] = initialValues[i];
//        for (int i = 0; i < N; i++) {
//            arrayA[nodes[i].outerIndex].add(nodes[i].innerIndex, nodes[i].innerIndex + 1, initialValues[i]);
//        }
        for (int i = 0; i < pathCount; i++) {
            arrayA[i] = new Seg(initValueOnArray[i]);
        }
        for (int i = 0; i < pathCount; i++) {
            int[] initB = new int[pathSize[i]];
            for (int j = 0; j < pathSize[i] - 1; j++) {
                initB[j] = initValueOnArray[i][j + 1] - initValueOnArray[i][j];
            }
            arrayB[i] = new SegGCD(initB);
        }
//        showTime("B");
//        startTime = System.currentTimeMillis();

        int resLen = 0;
        for (int i = 0; i < Q; i++) resLen += queries[i][0];
        int[] res = new int[resLen];
        resLen = 0;

        for (int i = 0; i < Q; i++) {
            if (queries[i][0] == 1) {
                int u = queries[i][1], v = queries[i][2];
                int gcd = find(nodes[u], nodes[v], arrayA, arrayB);
                res[resLen++] = Math.abs(gcd);
            } else {
                int u = queries[i][1], v = queries[i][2];
                add(nodes[u], nodes[v], arrayA, arrayB, queries[i][3]);
            }
        }
//        showTime("C");
        return res;
    }

    /*
         makeRootedTree(nodes[0]); // root
        setSize(nodes[0]); // size
        setDepth(nodes[0]); // depth
        int pathCount = decompose(nodes[0]); // decomp
     */
    private int doIt(Node root) {
        Stack<Node> nodes = new Stack<Node>();
        Stack<Integer> ops = new Stack<Integer>();// 0 : push, 1 : add
        nodes.push(root);
        nodes.push(root);
        ops.push(0);
        int currentOuterIndex = 0;
        root.outerIndex = currentOuterIndex++;
        root.leader = root;
        while (!ops.isEmpty()) {
            root = nodes.pop();
            int op = ops.pop();
            if (op == 0) {// before
                nodes.push(root);
                ops.push(1);
                for (Node c : root.neighbors)
                    if (c != root.parent) {
                        c.parent = root; // root
                        c.depth = root.depth + 1;// depth
                        nodes.push(c);
                        ops.push(0);
                    }
            } else {// after
                // size
                root.size = 1;
                for (Node c : root.neighbors)
                    if (c != root.parent) {
                        root.size += c.size;
                    }

            }
        }
        while (!nodes.isEmpty()) {
            root = nodes.pop();
            for (Node c : root.neighbors)
                if (c != root.parent) {
                    // decomp
                    c.heavy = c.size * 2 >= root.size;
//                    debug(c.id, root.id, c.size, root.size);
                    if (!c.heavy) {
                        c.leader = c;
                        c.outerIndex = currentOuterIndex++;
                        c.innerIndex = 0;
                    } else {
                        c.leader = root.leader;
                        c.outerIndex = root.outerIndex;
                        c.innerIndex = root.innerIndex + 1;
                    }
                    c.leader = c.heavy ? root.leader : c;
                    nodes.push(c);
                }
        }

        return currentOuterIndex;
    }

    private void add(Node u, Node v, Seg[] arrayA, SegGCD[] arrayB, int add) {
//        debug("add", u, v);
        while (u.leader != v.leader) {
//            debug("A", u, v);
            if (u.leader.depth < v.leader.depth) {
                Node tmp = u; u = v; v = tmp;
            }
            arrayA[u.outerIndex].add(0, u.innerIndex + 1, add);
            arrayB[u.outerIndex].add(u.innerIndex, -add);
            u = u.leader.parent;
        }
        if (u.depth > v.depth) {
            Node tmp = u; u = v; v = tmp;
        }
        arrayA[u.outerIndex].add(u.innerIndex, v.innerIndex + 1, add);
        if (u.innerIndex > 0) arrayB[u.outerIndex].add(u.innerIndex - 1, add);
//        debug("B", v, u);
        arrayB[u.outerIndex].add(v.innerIndex, -add);
    }

    private int find(Node u, Node v, Seg[] arrayA, SegGCD[] arrayB) {
//        debug("find",u,v);
        int res = 0;
        while (u.leader != v.leader) {
            if (u.leader.depth < v.leader.depth) {
                Node tmp = u; u = v; v = tmp;
            }
            int tmp = arrayA[u.outerIndex].get(0);
//            debug("C", u.outerIndex, tmp);
            res = MathUtils.gcd(res, tmp);
            tmp = arrayB[u.outerIndex].query(0, u.innerIndex);
//            debug("D", u.innerIndex, tmp);
            res = MathUtils.gcd(res, tmp);
            u = u.leader.parent;
        }
        if (u.depth > v.depth) {
            Node tmp = u; u = v; v = tmp;
        }
        int tmp = arrayA[u.outerIndex].get(u.innerIndex);
//        debug("E", u.outerIndex, u.innerIndex, tmp);
        res = MathUtils.gcd(res, tmp);
        tmp = arrayB[u.outerIndex].query(u.innerIndex, v.innerIndex);
//        debug("F", v.outerIndex, v.innerIndex, tmp);
        res = MathUtils.gcd(res, tmp);
        return res;
    }

    class Seg {
        // root : 0
        // parent : (i-1)/2
        // left,right : 2*i+1, 2*i+2
        // leaf : i >= n-1
        private int[] values;
        private int n;

        public Seg(int _n) {
            n = 1;
            while (n < _n) n <<= 1;
            values = new int[n + n];
        }

        public Seg(int[] init) {
            n = 1;
            while (n < init.length) n <<= 1;
            values = new int[n + n];
            System.arraycopy(init, 0, values, n - 1, init.length);
//            for(int i=n-2;i>=0;i--){
//                values[i] = values[(i<<1)+1] + values[(i<<1)+2];
//            }
        }

        public void add(int l, int r, int add) {
            add(l, r, 0, 0, n, add);
        }

        private void add(int a, int b, int i, int l, int r, int add) {
            if (r <= a || b <= l) return;
            if (a <= l && r <= b) {
                values[i] += add;
                return;
            }
            add(a, b, (i << 1) + 1, l, (l + r) >>> 1, add);
            add(a, b, (i << 1) + 2, (l + r) >>> 1, r, add);
        }

        /**
         * O(log n)
         *
         * @param i - index
         * @return value[i]
         */
        public int get(int i) {
            i += n - 1;
            int res = 0;
            res += values[i];
            while (i > 0) {
                i = (i - 1) >> 1;
                res += values[i];
            }
            return res;
        }
    }

    class SegGCD {
        // root : 0
        // parent : (i-1)/2
        // left,right : 2*i+1, 2*i+2
        // leaf : i >= n-1
        private int[] values;
        private int n;

        public SegGCD(int[] init) {
            n = 1;
            while (n < init.length) n <<= 1;
            values = new int[n + n];
            System.arraycopy(init, 0, values, n - 1, init.length);
            for (int i = n - 2; i >= 0; i--) {
                values[i] = MathUtils.gcd(values[(i << 1) + 1], values[(i << 1) + 2]);
            }
        }

        public SegGCD(int _n) {
            n = 1;
            while (n < _n) n <<= 1;
            values = new int[n + n];
        }

        public int query(int l, int r) {
            return query(l, r, 0, 0, n);
        }

        private int query(int a, int b, int i, int l, int r) {
            if (r <= a || b <= l) return 0;
            if (a <= l && r <= b) return values[i];
            int vl = query(a, b, (i << 1) + 1, l, (l + r) >>> 1);
            int vr = query(a, b, (i << 1) + 2, (l + r) >>> 1, r);
            return MathUtils.gcd(vl, vr);
        }

        /**
         * O(log n)
         *
         * @param i
         * @param a
         */
        public void add(int i, int a) {
            if (i < 0 || i >= n) throw new IllegalArgumentException(i + " " + n);
            i += n - 1;
            values[i] += a;
            while (i > 0) {
                i = (i - 1) >> 1;
                values[i] = MathUtils.gcd(values[(i << 1) + 1], values[(i << 1) + 2]);
            }
        }

//        private int append(int valA, int valB) {
//            return MathUtils.gcd(valA, valB);
//        }
    }

    private int decompose(Node root) {
        int currentOuterIndex = 0;
        root.leader = root;
        root.outerIndex = currentOuterIndex++;
        Queue<Node> que = new LinkedList<Node>();
        que.offer(root);
        while (!que.isEmpty()) {
            root = que.poll();
            for (Node c : root.neighbors)
                if (c != root.parent) {
                    c.heavy = c.size * 2 >= root.size;
                    if (!c.heavy) {
                        c.leader = c;
                        c.outerIndex = currentOuterIndex++;
                        c.innerIndex = 0;
                    } else {
                        c.leader = root.leader;
                        c.outerIndex = root.outerIndex;
                        c.innerIndex = root.innerIndex + 1;
                    }
                    c.leader = c.heavy ? root.leader : c;
                    que.offer(c);
                }
        }
        return currentOuterIndex;
    }

    private void setDepth(Node root) {
        Queue<Node> que = new LinkedList<Node>();
        que.offer(root);
        root.depth = 0;
        while (!que.isEmpty()) {
            root = que.poll();
            for (Node c : root.neighbors)
                if (c != root.parent) {
                    c.depth = root.depth + 1;
                    que.offer(c);
                }
        }
    }

    private void setSize(Node root) {
        Stack<Node> nodes = new Stack<Node>();
        Stack<Integer> ops = new Stack<Integer>();// 0 : push, 1 : add
        nodes.push(root);
        ops.push(0);
        while (!nodes.isEmpty()) {
            root = nodes.pop();
            int op = ops.pop();
            if (op == 0) {
                nodes.push(root);
                ops.push(1);
                for (Node c : root.neighbors)
                    if (c != root.parent) {
                        nodes.push(c);
                        ops.push(0);
                    }
            } else {
                root.size = 1;
                for (Node c : root.neighbors)
                    if (c != root.parent) {
                        root.size += c.size;
                    }
            }
        }
    }

    private void makeRootedTree(Node root) {
        Queue<Node> que = new LinkedList<Node>();
        que.offer(root);
        while (!que.isEmpty()) {
            root = que.poll();
            for (Node c : root.neighbors)
                if (c != root.parent) {
                    c.parent = root;
                    que.offer(c);
                }
        }
    }


    class Node {
        int id;// for debug
        int outerIndex;
        int innerIndex;
        Node parent;
        Node leader;
        int size;
        int depth;
        boolean heavy;
        List<Node> neighbors = new ArrayList<Node>();

        @Override
        public String toString() {
            Node node = this;
            return "out: " + node.outerIndex + " in: " + node.innerIndex + " size: " + node.size
                    + " depth:" + node.depth + " heavy:" + node.heavy + " id:" + id;
        }
    }

    public static void main(String[] args) {
        long summation = 0;
        Random rnd = new Random(120498120498L);
        int N = 500000;
        int Q = 50000;
        int ITER = 10;
        boolean test = false;
        int[] parents = new int[N];
        int[][] edges = new int[N][2];
        for (int o = 0; o < ITER; o++) {
            for (int i = 0; i < N - 1; i++) {
                int p = rnd.nextInt(i + 1);
//                int p = i / 2;
                parents[i + 1] = p;
                edges[i][0] = p;
                edges[i][1] = i + 1;
            }
            int[] init = new int[N];
            for (int i = 0; i < N; i++) init[i] = rnd.nextInt(10000) + 1;
            int[][] queries = new int[Q][4];
            for (int i = 0; i < Q; i++) {
                int v = rnd.nextInt(2);
                queries[i][0] = v;
                for (int j = 0; j < 2; j++) {
                    queries[i][1 + j] = rnd.nextInt(N);
                }
                if (queries[i][0] == 0) {
                    queries[i][3] = rnd.nextInt(10000);
                }
            }
            long start = System.currentTimeMillis();
            int[] res = new DynamicGCD().solve(N, edges, init, Q, queries);
            long time = System.currentTimeMillis() - start;
            summation += time;
            System.out.println("time = " + time);
            if (test) {
                int[] exp = new DynamicGCD().solveStupid(N, edges, init, Q, queries);
                if (!Arrays.equals(res, exp)) {
                    System.err.println(Arrays.toString(parents));
                    debug(init);
                    debug(queries);
                    debug(res);
                    debug(exp);
                    throw new RuntimeException();
                }
//        System.err.println(Arrays.toString(exp));
            }
        }
        System.out.println(summation);
    }

    private int[] solveStupid(int N, int[][] edges, int[] init, int Q, int[][] queries) {
        Graph graph = new UndirectedGraph(N);
        int resLen = 0;
        for (int i = 0; i < Q; i++) resLen += queries[i][0];
        int[] res = new int[resLen];
        resLen = 0;
        for (int i = 0; i < N - 1; i++) graph.add(new SimpleEdge(edges[i][0], edges[i][1]));
        int[] values = init.clone();
        for (int i = 0; i < Q; i++) {
            if (queries[i][0] == 0) {
                int u = queries[i][1], v = queries[i][2], d = queries[i][3];
                add(graph, u, v, -1, values, d);
            } else {
                int u = queries[i][1], v = queries[i][2];
                int gcd = find(graph, u, v, -1, values);
                res[resLen++] = gcd;
//                out.println(gcd);
            }
        }
        return res;
    }

    private int find(Graph graph, int u, int v, int p, int[] values) {
        if (u == v) {
            return values[v];
        }
        for (Edge e : graph.edges(u))
            if (e.to() != p) {
                int g = find(graph, e.to(), v, u, values);
                if (g > 0) return MathUtils.gcd(values[u], g);
            }
        return 0;
    }

    private boolean add(Graph graph, int u, int v, int p, int[] values, int d) {
        if (u == v) {
            values[v] += d; return true;
        }
        for (Edge e : graph.edges(u))
            if (e.to() != p) {
                if (add(graph, e.to(), v, u, values, d)) {
                    values[u] += d; return true;
                }
            }
        return false;
    }
}
