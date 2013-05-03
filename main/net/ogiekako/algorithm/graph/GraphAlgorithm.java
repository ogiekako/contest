package net.ogiekako.algorithm.graph;

import net.ogiekako.algorithm.graph.algorithm.SCC;
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
    public static long maxFlow(Graph graph, int s, int t) {
        return maxFlow(graph,s,t,Long.MAX_VALUE);
    }

    public static long maxFlow(Graph graph,int s,int t,long limit){
        if(s == t)return limit;
        int n = graph.size();
        int[] level = new int[n];
        int[] visited = new int[n];
        long flow = 0;
        for (int iteration = 1;limit > 0 ; iteration++) {// less than n iteration
            level[s] = -1;
            setLevel(graph, t, level, visited, iteration);
            if (level[s] == -1)break;
            long add = blockingFlow(graph, s, t, level, limit);
            flow += add;
            limit -= add;
        }
        return flow;
    }

    /*
    O(mn)
     */
    private static long blockingFlow(Graph graph, int s, int t, int[] level, long limit) {
        int n = graph.size();
        int[] edgeIds = new int[n];
        long flowSum = 0;
        for (; ; ) {
            long flow = blockingFlowDFS(graph, s, t, level, edgeIds, Long.MAX_VALUE, limit);
            if (flow > 0) {
                flowSum += flow;
                limit -= flow;
            } else
                return flowSum;
        }
    }

    private static long blockingFlowDFS(Graph graph, int cur, int t, int[] level, int[] edgeIds, long curFlow, long limit) {
        if (cur == t) return Math.min(curFlow, limit);
        List<Edge> es = graph.edges(cur);
        for (; edgeIds[cur] < es.size(); edgeIds[cur]++) {
            Edge e = es.get(edgeIds[cur]);
            if (e.residue() > 0 && level[cur] == level[e.to()] + 1) {
                long flow = blockingFlowDFS(graph, e.to(), t, level, edgeIds, Math.min(e.residue(), curFlow), limit);
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
    private static void setLevel(Graph graph, int t, int[] level, int[] visited, int iteration) {
        Queue<Integer> que = new LinkedList<Integer>();
        que.offer(t);
        level[t] = 0;
        visited[t] = iteration;
        while (!que.isEmpty()) {
            int cur = que.poll();
            /*
            residue = 逆辺のflow > 0 
             */
            for (Edge e : graph.edges(cur))
                if (e.flow() > 0 && visited[e.to()] < iteration) {
                    int nxt = e.to();
                    que.offer(nxt);
                    level[nxt] = level[cur] + 1;
                    visited[nxt] = iteration;
                }
        }
    }

    public static Graph minCutTree(int[][] capacity) {
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
        Graph graph = new Graph(n);
        for (int s = 0; s < n; s++)
            if (s != p[s]) {
                Edge e = new FlowEdge(s, p[s], w[s]);
                Edge r = new FlowEdge(p[s], s, w[s]);
                graph.add(e);
                graph.add(r);
            }
        return graph;
    }

    /*
    res[i][i] = 0.
    O(nm log m)
    */
    public static long[][] minDistanceDijkstra(Graph graph) {
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
    public static long[] dijkstra(Graph graph, int vertex) {
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
            for (Edge e : graph.edges(current)) {
                long nWeight = weight + e.cost();
                int next = e.to();
                if (res[next] > nWeight) {
                    res[next] = nWeight;
                    que.offer(new Pair<Long, Integer>(nWeight, next));
                }
            }
        }
        return res;
    }

    public static boolean isAcyclic(Graph graph) {
        if (graph.isDigraph()) {
            return isAcyclicDigraph(graph);
        } else {
            return isAcyclicUndirectedGraph(graph);
        }
    }

    private static boolean isAcyclicUndirectedGraph(Graph graph) {
        return false;
    }

    private static boolean isAcyclicDigraph(Graph graph) {
        int n = graph.size();
        for (int i = 0; i < n; i++) for (Edge e : graph.edges(i)) if (e.to() == i) return false;
        return SCC.sccWithComponents(graph).second.length == n;
    }

    public static boolean reachable(Graph graph, int from, int to) {
        return dijkstra(graph, from)[to] < INF;
    }

}
