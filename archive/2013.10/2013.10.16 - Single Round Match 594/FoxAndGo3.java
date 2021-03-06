package src;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.ArrayList;
import java.util.Arrays;
public class FoxAndGo3 {
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};
    public int maxEmptyCells2(String[] board) {
        int n = board.length;
        Graph graph = new Graph(2 + n * n);
        int s = n * n, t = s + 1;
        int res = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int p = i * n + j;
                if (board[i].charAt(j) == 'o') {
                    res++;
                    if ((i + j) % 2 == 0) {
                        graph.addFlow(s, p, 1);
                        for (int d = 0; d < 4; d++) {
                            int nx = i + dx[d];
                            int ny = j + dy[d];
                            if (0 <= nx && nx < n && 0 <= ny && ny < n) {
                                if (board[nx].charAt(ny) == '.') {
                                    graph.addFlow(p, nx * n + ny, 1);
                                }
                            }
                        }
                    } else {
                        graph.addFlow(p, t, 1);
                    }
                } else if (board[i].charAt(j) == '.') {
                    res++;
                    if ((i + j) % 2 == 0) {
                        graph.addFlow(s, p, 1);
                        for (int d = 0; d < 4; d++) {
                            int nx = i + dx[d];
                            int ny = j + dy[d];
                            if (0 <= nx && nx < n && 0 <= ny && ny < n) {
                                if (board[nx].charAt(ny) == 'o') {
                                    graph.addFlow(p, nx * n + ny, 1);
                                }
                            }
                        }
                    } else {
                        graph.addFlow(p, t, 1);
                    }
                }
            }
        res -= (int) Math.round(MaxFlow.maxFlow(graph, s, t));
        return res;
    }

    ArrayList<Integer>[]l;
    int[] r;
    boolean[] was;
    public int maxEmptyCells(String[] board){
        int n = board.length;
        int[][] id = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(id[i], -1);

        }
        l=new ArrayList[n*n];
        for (int i = 0; i < l.length; i++) {
            l[i]=new ArrayList<Integer>();
        }
        int cnt=0,len=0,ans=0;
        for (int i = 0; i < id.length; i++) {
            for (int j = 0; j < id[0].length; j++) {
                if(board[i].charAt(j)=='.')ans++;
                if(board[i].charAt(j)=='o'){
                    for (int k = 0; k < 4; k++) {
                        int nx = i + dx[k];
                        int ny = j + dy[k];
                        if (0 <= nx && nx < n && 0 <= ny && ny < n){
                            if(board[nx].charAt(ny)=='.'){
                                if(id[nx][ny]==-1) {
                                    id[nx][ny] = cnt++;
                                }
                                l[len].add(id[nx][ny]);
                            }
                        }
                    }
                    len++;
                }
            }
        }
        l=Arrays.copyOf(l,len);

        r=new int[cnt];
        was=new boolean[len];
        Arrays.fill(r,-1);
        for (int i = 0; i < l.length; i++) {
            Arrays.fill(was,false);
            dfs(i);
        }
        int d=0;
        for (int i = 0; i < r.length; i++) {
            if(r[i] != -1)  d++;
        }
        return Math.max(ans, ans+len-d);
    }
    private boolean dfs(int v) {
                            if(was[v])return false;
        was[v]=true;
        for(int u:l[v]){
            if(r[u]==-1 || dfs(r[u])) {
                r[u] = v;
                return true;
            }
        }
        return false;
    }

}
