package net.ogiekako.algorithm.graph.algorithm;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;

import java.util.*;

public class MaxFlow {
    /*
Compute a max-flow from s to t using Dinic's algorithm.
http://www.slideshare.net/KuoE0/acmicpc-dinics-algorithm

O(n^2 m)
Compute blocking flow (O(nm)) O(n) times.

アルゴリズム:
以下を繰り返す.
残余グラフに基づき, レベルを頂点に割り振る.
これは, 終点への最短距離.
最短路に使用しうる枝(レベルが1減少している枝)のみにしたグラフR を考える.
始点から終点へのいかなるパスも, 容量いっぱいの辺を含んでいるとき, fをブロッキングフローと呼ぶ
Rに関してブロッキングフローを流す.(後述)

解析:
前のレベル分けを思い描くと, `前向き' の枝が `後ろ向き' になるだけ．
ブロッキングフローの仮定より， 始点から終点への距離は, かならず1以上大きくなる. よって, 高々n回で終了する.

ブロッキングフロー:
DFSで, 単純な貪欲で, ブロッキングフローを求めることができる.
とにかく行けるところを流す. 前に見た枝を見なおす必要はない.
ちなみに, RはDAGなので, 行った頂点を覚えておく必要はない.

解析:
DAGなので 深さは高々n,
もし, フローを流せる場合は, 容量いっぱいの枝ができて, 枝の数は1以上減る.
 (この場合は, 一旦フローを流して始点から再びDFS. ただし, すでに見込みのない枝はもう見なくていい. この処理をするため, 枝をどこまで見たかを持っておく必要がある)
また, 流せない場合は, 最後に使った枝は, 今後使われない(いきどまりの枝)ので, 枝の数は1減る.
よって, O(n)の操作が高々 m 回しか行われないので, O(nm).

POJ3281 でテストしたところ、
Long: 1469MS
Double: 1313MS だったので、double で統一して良さそう。

 */
    final Graph graph;
    final int n;
    int[] level, visited;
    int source, sink;
    double limit;
    EdgeIterator[] iterators;
    int flag;

    public MaxFlow(Graph graph) {
        this.graph = graph;
        n = graph.size();
    }

    /**
     * Solve maximum flow problem in O(n^2 m) time with Dinic's algorithm.
     * Returns Double.POSITIVE_INFINITY if the answer is unbounded.
     * <p>Verified: PKU 3155 (Maximum Density Subgraph) (double)</p>
     */
    public double maxFlow(int source, int sink) {
        return maxFlow(source, sink, Double.POSITIVE_INFINITY);
    }

    /**
     * Solve maximum flow with a limit in O(n^2 m) time with Dinic's algorithm.
     * Returns `limit' if it is possible to supply more than `limit' of flow from the source to the sink.
     */
    public double maxFlow(int _source, int _sink, double _limit) {
        source = _source;
        sink = _sink;
        limit = _limit;
        if (source == sink) return limit;
        level = new int[n];
        visited = new int[n];
        iterators = new EdgeIterator[n];
        double flow = 0;
        for (flag = 1; limit > 0; flag++) {// less than n iterations
            setLevel(flag);
            if (visited[source] < flag) break;
            flow += blockingFlow();
        }
        return flow;
    }

    /*
    O(mn)
     */
    private double blockingFlow() {
        for (int i = 0; i < n; i++) iterators[i] = new EdgeIterator(graph.edges(i).listIterator());
        for (double totalFlow = 0; ; ) {  // at most m iterations (since at lease one edge is saturated by a dfs and a level graph is acyclic.)
            double flow = blockingFlowDFS();
            if (flow > 1e-9) {
                totalFlow += flow;
                limit -= flow;
            } else
                return totalFlow;
        }
    }

    /**
     * O(n)
     * At least one edge will be saturated.
     */
    private double blockingFlowDFS() {
        Stack<Edge> parents = new Stack<Edge>();
        int v = source;
        for (; ; ) {
            if (v == sink) {
                double flow = limit;
                for (Edge e : parents) flow = Math.min(flow, e.residue());
                for (Edge e : parents) e.pushFlow(flow);
                return flow;
            }
            if (iterators[v].hasNext()) {
                Edge e = iterators[v].peek();
                if (e.residue() > 1e-9 && level[v] == level[e.to()] + 1) {
                    parents.push(e);
                    v = e.to();
                } else {
                    iterators[v].next();
                }
            } else {
                if (parents.isEmpty()) return 0;
                v = parents.pop().from();
                iterators[v].next();
            }
        }
    }

    /**
     * level[v] := minimum distance from v to the sink in the residual graph.
     * O(m)
     */
    private void setLevel(int flag) {
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(sink);
        level[sink] = 0;
        visited[sink] = flag;
        while (!que.isEmpty()) {
            int u = que.poll();
            for (Edge e : graph.edges(u))
                // e.flow() > 0 <=> Residue of the reversed edge of e is positive.
                if (e.flow() > 1e-9 && visited[e.to()] < flag) {
                    int v = e.to();
                    que.offer(v);
                    level[v] = level[u] + 1;
                    visited[v] = flag;
                }
        }
    }

    ///// information of the last flow /////
    public boolean sourceSide(int v) {
        return visited[v] < flag;
    }

    class EdgeIterator implements Iterator<Edge> {
        Iterator<Edge> iterator;
        Edge last;

        EdgeIterator(Iterator<Edge> iterator) {
            this.iterator = iterator;
        }

        public boolean hasNext() {
            return last != null || iterator.hasNext();
        }

        public Edge next() {
            if (last == null) return iterator.next();
            Edge res = last;
            last = null;
            return res;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Edge peek() {
            if (last == null) last = iterator.next();
            return last;
        }
    }

    /**
     * Utility function to get all reachable vertices from the given source, where edges with no residue are impassable.
     * A typical usage is to use this after a max flow computation.
     * <p>O(m)</p>
     * <p>Verified: PKU3155 (Maximum Density Subgraph)</p>
     */
    public List<Integer> getReachableVerticesFrom(int source) {
        boolean[] visited = new boolean[n];
        Queue<Integer> que = new LinkedList<Integer>();
        visited[source] = true;
        que.offer(source);
        while (!que.isEmpty()) {
            for (Edge e : graph.edges(que.poll())) {
                if (e.residue() <= 0) continue;
                if (visited[e.to()]) continue;
                visited[e.to()] = true;
                que.offer(e.to());
            }
        }
        List<Integer> res = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (visited[i]) res.add(i);
        }
        return res;
    }
}
