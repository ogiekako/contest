package net.ogiekako.algorithm.graph.graphDouble;

import net.ogiekako.algorithm.utils.ArrayUtils;
import net.ogiekako.algorithm.utils.Pair;

import java.util.*;

import static java.lang.Math.min;
import static java.util.Arrays.fill;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/01
 * Time: 2:59
 * To change this template use File | Settings | File Templates.
 */
public class GraphAlgorithm {
    public static long INF = Long.MAX_VALUE / 2;
    public static int INF_I = Integer.MAX_VALUE / 2;

    /*
    s -> t へのmaxflow を求める.

    Dinic のアルゴリズム.
    O(n^2 m)
    ブロッキングフローを計算するのに O(nm). それがO(n) 回.

    アルゴリズム:
    以下を繰り返す.
    残余グラフに基づき, レベルを頂点に割り振る.
    これは, 終点への最短距離.
    最短路に使用しうる枝(レベルが1減少している枝)のみにしたグラフR を考える.
    始点から終点へのいかなるパスも, 容量いっぱいの辺を含んでいるとき, fをブロッキングフローと呼ぶ
    Rに関してブロッキングフローを流す.(後述)

    解析:
    前のレベル分けを思い描くと, つぎの残余グラフにおける枝は, まず, レベルを一気に2以上下げることはありえない.
    また, レベルを1ずつ下げるとすると, ブロッキングフローの仮定に反するから, これもありえない.
    よって, 始点から終点への距離は, かならず1以上大きくなる. よって, 高々n回で終了する.

    ブロッキングフロー:
    DFSで, 単純な貪欲で, ブロッキングフローを求めることができる.
    とにかく行けるところを流す. 前に見た枝を見なおす必要はない.
    ちなみに, RはDAGなので, 行った頂点を覚えておく必要はない.

    解析:
    DAGより 深さは高々n,
    もし, フローを流せる場合は, 容量いっぱいの枝ができて, 枝の数は1以上減る.
     (この場合は, 一旦フローを流して始点から再びDFS. ただし, すでに見込みのない枝はもう見なくていい. この処理をするため, 枝をどこまで見たかを持っておく必要がある)
    また, 流せない場合は, 最後に使った枝は, 今後使われない(いきどまりの枝)ので, 枝の数は1減る.
    よって, O(n)の操作が高々 m 回しか行われないので, O(nm).

     */
    public static double maxFlow(GraphD graph, int s, int t) {
        if (s == t) throw new IllegalArgumentException("s==t " + s);
        int n = graph.size();
        int[] level = new int[n];
        int[] visited = new int[n];
        double flow = 0;
        for (int iteration = 1; ; iteration++) {// less than n iteration
            level[s] = -1;
            setLevel(graph, t, level, visited, iteration);
            if (level[s] == -1) return flow;
            flow += blockingFlow(graph, s, t, level);
        }
    }

    /*
    O(mn)
     */
    private static double blockingFlow(GraphD graph, int s, int t, int[] level) {
        int n = graph.size();
        int[] edgeIds = new int[n];
        double flowSum = 0;
        for (; ; ) {
            double flow = blockingFlowDFS(graph, s, t, level, edgeIds, Double.POSITIVE_INFINITY);
            if (flow > 0) {
                flowSum += flow;
            } else
                return flowSum;
        }
    }

    private static double blockingFlowDFS(GraphD graph, int cur, int t, int[] level, int[] edgeIds, double curFlow) {
        if (cur == t) return curFlow;
        List<EdgeD> es = graph.getEdges(cur);
        for (; edgeIds[cur] < es.size(); edgeIds[cur]++) {
            EdgeD e = es.get(edgeIds[cur]);
            /*  pushFlowで引き算しているので, saturated なら, 真にresidue = 0 が成立するはず. */
            if (e.getResidue() > 0 && level[cur] == level[e.to()] + 1) {
                double flow = blockingFlowDFS(graph, e.to(), t, level, edgeIds, Math.min(e.getResidue(), curFlow));
                if (flow > 0) {
                    e.pushFlow(flow);
                    return flow;
                }
                /*
                行き止まり
                 */
            }
        }
        return 0;
    }

    /*
    O(m)
     */
    private static void setLevel(GraphD graph, int t, int[] level, int[] visited, int iteration) {
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(t);
        level[t] = 0;
        visited[t] = iteration;
        while (!que.isEmpty()) {
            int cur = que.poll();
            /*
            residue = 逆辺のflow > 0 
             */
            for (EdgeD e : graph.getEdges(cur))
                if (e.getFlow() > 0 && visited[e.to()] < iteration) {
                    int nxt = e.to();
                    que.offer(nxt);
                    level[nxt] = level[cur] + 1;
                    visited[nxt] = iteration;
                }
        }
    }

    public static GraphD minCutTree(int[][] capacity) {
        int n = capacity.length;
        int[][] flow = new int[n][n];
        int[] p = new int[n], prev = new int[n];
        int[] w = new int[n];
        for (int s = 1; s < n; s++) {
            int t = p[s];
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) flow[i][j] = 0;
            int total = 0;
            for (; ; ) {
                Queue<Integer> que = new LinkedList<Integer>();
                que.offer(s);
                fill(prev, -1);
                prev[s] = s;
                while (!que.isEmpty() && prev[t] < 0) {
                    int u = que.poll();
                    for (int i = 0; i < n; i++)
                        if (prev[i] < 0 && capacity[u][i] - flow[u][i] > 0) {
                            prev[i] = u;
                            que.offer(i);
                        }
                }
                if (prev[t] < 0) break;
                int inc = INF_I;
                for (int j = t; prev[j] != j; j = prev[j])
                    inc = min(inc, capacity[prev[j]][j] - flow[prev[j]][j]);
                for (int j = t; prev[j] != j; j = prev[j]) {
                    flow[prev[j]][j] += inc;
                    flow[j][prev[j]] -= inc;
                }
                total += inc;
            }
            w[s] = total;
            for (int u = 0; u < n; u++)
                if (u != s && prev[u] != -1 && p[u] == t)
                    p[u] = s;
            if (prev[p[t]] != -1) {
                p[s] = p[t];
                p[t] = s;
                w[s] = w[t];
                w[t] = total;
            }
        }
        GraphD graph = new GraphD(n);
        for (int s = 0; s < n; s++)
            if (s != p[s]) {
                EdgeD e = new FlowEdge(s, p[s], w[s]);
                EdgeD r = new FlowEdge(p[s], s, w[s]);
                graph.add(e);
                graph.add(r);
            }
        return graph;
    }

    /*
    res[i][i] = 0.
    O(nm log m)
    */
    public static long[][] minDistanceDijkstra(GraphD graph) {
        int n = graph.size();
        long[][] res = new long[n][n];
        for (int i = 0; i < n; i++) {
            res[i] = dijkstra(graph, i);
        }
        return res;
    }

    /*
    O(m log m)
     */
    public static long[] dijkstra(GraphD graph, int vertex) {
        int n = graph.size();
        long[] res = new long[n];
        Arrays.fill(res, INF);
        Queue<Pair<Long, Integer>> que = new PriorityQueue<Pair<Long, Integer>>();
        que.offer(new Pair<Long, Integer>(0L, vertex));
        res[vertex] = 0;
        while (!que.isEmpty()) {
            Pair<Long, Integer> pair = que.poll();
            long weight = pair.first;
            int current = pair.second;
            for (EdgeD e : graph.getEdges(current)) {
                long nWeight = weight + e.getWeight();
                int next = e.to();
                if (res[next] > nWeight) {
                    res[next] = nWeight;
                    que.offer(new Pair<Long, Integer>(nWeight, next));
                }
            }
        }
        return res;
    }

    public static boolean isAcyclic(GraphD graph) {
        if (graph.isDigraph()) {
            return isAcyclicDigraph(graph);
        } else {
            return isAcyclicUndirectedGraph(graph);
        }
    }

    private static boolean isAcyclicUndirectedGraph(GraphD graph) {
        return false;
    }

    private static boolean isAcyclicDigraph(GraphD graph) {
        int n = graph.size();
        for (int i = 0; i < n; i++) for (EdgeD e : graph.getEdges(i)) if (e.to() == i) return false;
        return scc(graph).second.length == n;
    }

    public static boolean reachable(GraphD graph, int from, int to) {
        return dijkstra(graph, from)[to] < INF;
    }

    /*
    res[i] には, i番目の強連結成分に属する 頂点の番号が入る.
    また, トポロジカルソートされていることが保証される.
    つまり, i < j なら, res[i] から, res[j] に属する頂点にはいけない.
    O(n + m)

    アルゴリズムは, まず帰りがけ順に, 降順に番号をふる.
    それから, 番号の昇順に, 逆辺を使って, 極大木を作っていく.
    この極大木が, 強連結成分に対応している.

    証明:
    まず, 同じ極大木に属していなければ, 同じ成分でありえないことはよい(先に選ばれた点から, 逆辺を通って行けないことより).
    逆を示す.
    uを極大木の根, vを根以外の点とすると, v -> u は構成方法より明らかなので, u -> v を示す.
    番号の付け方より, v は u より年寄りで, これは, Gの探索木で, v が uの子孫であったか, u が v のあとに探索された別の木 であることを意味する.
    前者の場合は, u -> v である. 後者は, ありえない.(v -> u なのだから, uは vの子孫となるはずで矛盾.) ■
     */
    public static Pair<int[], int[][]> scc(GraphD graph) {
        int n = graph.size();
        int[] order = new int[n];
        for (int i = 0; i < n; i++) order[i] = i;
        order = sccSub(graph, order, true);
        graph = graph.getTransposedGraph();
        int[] comp = sccSub(graph, order, false);
        int[][] res = ArrayUtils.classify(comp);
        return new Pair<int[], int[][]>(comp, res);
    }

    private static int[] sccSub(GraphD graph, int[] order, boolean first) {
        int n = graph.size();
        int[] edgeId = new int[n];
        int[] prev = new int[n];
        Arrays.fill(prev, -1);
        int[] res = new int[n];
        int m = first ? n : 0;
        for (int r : order)
            if (prev[r] == -1) {
                prev[r] = -2;// root
                for (int v = r; v >= 0; v = prev[v]) {
                    while (edgeId[v] < graph.getEdges(v).size()) {
                        int next = graph.getEdges(v).get(edgeId[v]++).to();
                        if (prev[next] == -1) {// go next vertex
                            prev[next] = v;
                            v = next;
                        }
                    }
                    // return from v
                    if (first) res[--m] = v; // define order
                    else res[v] = m;// scc
                }
                if (!first) m++;
            }
        return res;
    }
}
