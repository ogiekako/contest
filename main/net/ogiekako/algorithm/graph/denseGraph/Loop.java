package net.ogiekako.algorithm.graph.denseGraph;

@SuppressWarnings("UnusedDeclaration")
public class Loop {
    /**
     * iを始点として検索し,iから行ける,グラフ内のループを見つける.
     * ループが見つかった場合,ループに含まれるノード番号 p を返す.
     * prevをたどることにより,ループを検出できる.
     * 見つからない場合,-1 を返す.
     * for(int i=0;i<graph.length;i++)if(visited[i]==0){
     * int p = findLoop();
     * if(p!=-1){
     * int q = p;
     * do{
     * <p/>
     * // what to do
     * <p/>
     * q = prev[q];
     * }while(p!=q);
     * }
     * }
     * のように使う.
     */
    public static int findLoop(int[] visited, int[] prev, boolean[][] graph, int i) {
        if (visited[i] != 0) throw new RuntimeException();
        visited[i] = 2;
        for (int j = 0; j < graph.length; j++)
            if (graph[i][j]) {
                if (visited[j] == 2) {
                    prev[j] = i;
                    return j;
                } else if (visited[j] == 0) {
                    prev[j] = i;
                    int p = findLoop(visited, prev, graph, j);
                    if (p >= 0) return p;
                }
            }
        visited[i] = 1;
        return -1;
    }
}
