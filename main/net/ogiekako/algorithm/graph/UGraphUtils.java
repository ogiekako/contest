package net.ogiekako.algorithm.graph;

import net.ogiekako.algorithm.dataStructure.UnionFind;

public class UGraphUtils {

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
}
