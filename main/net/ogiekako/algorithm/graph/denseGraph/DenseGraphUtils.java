package net.ogiekako.algorithm.graph.denseGraph;

import net.ogiekako.algorithm.annotations.verified;
import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.exceptions.UnsatisfiableException;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class DenseGraphUtils {

    public static boolean isConnected(boolean[][] graph, int x, int y) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        return isConnectedDfs(graph, x, y, visited);
    }

    private static boolean isConnectedDfs(boolean[][] graph, int x, int y, boolean[] visited) {
        if(x==y)return true;
        visited[x]=true;
        for(int i=0;i<graph.length;i++)if(graph[x][i] && !visited[i] && isConnectedDfs(graph,i,y,visited))return true;
        return false;
    }

    public static int lowerCommonAncestor(boolean[][] graph, int root, int x, int y) {
        UnionFind uf = new UnionFind(graph.length);
        boolean[] color = new boolean[graph.length];
        int[] ancestor = new int[graph.length];
        return lowerCommonAncestorDfs(graph, root, -1, x, y, color, ancestor, uf);
    }

    private static int lowerCommonAncestorDfs(boolean[][] graph, int u, int w, int x, int y, boolean[] color, int[] ancestor, UnionFind uf) {
        ancestor[uf.root(u)] = u;
        for (int i = 0; i < graph.length; i++)if(graph[u][i] && i!=w){
            int tmp = lowerCommonAncestorDfs(graph,i,u,x,y,color,ancestor, uf);
            if(tmp >= 0)return tmp;
            uf.union(u,i);
            ancestor[uf.root(u)] = u;
        }
        color[u] = true;
        int res = x==u ? y : y==u ? x : -1;
        if(res>=0 && color[res])return ancestor[uf.root(res)];
        return -1;
    }
    /**
     * kruskal
     * @param undirectedGraph
     * @return
     */
    public static long minimumSpanningTree(long[][] undirectedGraph) {
        int n = undirectedGraph.length;
        Edge[] es = new Edge[n*(n-1)/2];
        int m = 0;
        for(int i=0;i<n;i++)for(int j=i+1;j<n;j++)es[m++] = new Edge(i,j, undirectedGraph[i][j]);
        Arrays.sort(es);
        UnionFind uf = new UnionFind(n);
        long res = 0;
        for(Edge e:es){
            if(!uf.find(e.s,e.t)){
                uf.union(e.s,e.t);
                res += e.weight;
            }
        }
        return res;
    }
    /**
     * O(n^2).
     * @param undirectedGraph
     * @return
     */
    public static boolean isEdgeConnectedGraph(boolean[][] undirectedGraph) {
        int n = undirectedGraph.length;
        int cnt = 0;
        Queue<Integer> que = new LinkedList<Integer>();
        boolean[] visited=new boolean[n];
        for(int i=0;i<n;i++)if(ArrayUtils.contains(undirectedGraph[i], true)){
            cnt++;
            if(que.isEmpty()){
                que.offer(i);
                visited[i] = true;
            }
        }
        while(!que.isEmpty()){
            int i = que.poll();
            cnt--;
            for(int j=0;j<n;j++)if(undirectedGraph[i][j] && !visited[j]){
                visited[j]=true;
                que.offer(j);
            }
        }
        return cnt==0;
    }
    public static boolean isBipartite(boolean[][] graph) {
        return bipartise(graph) != null;
    }
    /**
     * O(n^2)
     * @param graph - graph
     * @return - One of the possible colorings of the given graph or null if the graph is not bipartite.
     */
    public static boolean[] bipartise(boolean[][] graph) {
        int n = graph.length;
        boolean[] isLeftSide = new boolean[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++)if(!visited[i]){
            boolean isValid = bipartiseDfs(i,true,isLeftSide, visited, graph);
            if(!isValid)return null;
        }
        return isLeftSide;
    }
    private static boolean bipartiseDfs(int ptr, boolean isLeft, boolean[] isLeftSide, boolean[] visited, boolean[][] graph) {
        visited[ptr] = true;
        isLeftSide[ptr] = isLeft;
        for (int i = 0; i < graph[ptr].length; i++) if (graph[ptr][i]) {
            boolean nextIsLeft = !isLeft;
            if (visited[i] && nextIsLeft != isLeftSide[i]) return false;
            if (!visited[i]) {
                boolean isValid = bipartiseDfs(i, nextIsLeft, isLeftSide, visited, graph);
                if (!isValid) return false;
            }
        }
        return true;
    }
    /**
     * calculate a minimum cost vertex cover which uses no more than k vertices.
     * @throws net.ogiekako.algorithm.exceptions.UnsatisfiableException
     */
    @verified("SRM571 B")
    public static int vertexCoverUpTo(boolean[][] graph, int[] cost, int k) throws UnsatisfiableException {
        int n = graph.length;
        boolean[] used = new boolean[n];
        int res = vertexCoverUpTo0(graph, used, cost, k);
        if(res == Integer.MAX_VALUE)throw new UnsatisfiableException();
        return res;
    }
    private static int vertexCoverUpTo0(boolean[][] graph, boolean[] used, int[] cost, int k) {
        if(k<0)return Integer.MAX_VALUE;
        int n = graph.length;
        int i,j=0;
        loop:for (i = 0; i < n; i++)if(!used[i]) for (j = 0; j < i; j++)if(!used[j] && graph[i][j])break loop;
        if(i==n)return 0;
        int res = Integer.MAX_VALUE;
        used[i] = true;
        int s1 = vertexCoverUpTo0(graph,used,cost,k-1);
        used[i] = false;
        used[j] = true;
        int s2 = vertexCoverUpTo0(graph,used,cost,k-1);
        used[j] = false;
        if(s1 < Integer.MAX_VALUE)res = Math.min(res, s1 + cost[i]);
        if(s2 < Integer.MAX_VALUE)res = Math.min(res, s2 + cost[j]);
        return res;
    }
    private static class Edge implements Comparable<Edge>{
        final int s,t;
        final long weight;

        private Edge(int s, int t, long weight) {
            this.s = s;
            this.t = t;
            this.weight = weight;
        }

        public int compareTo(Edge o) {
            return Long.compare(weight,o.weight);
        }
    }
}
