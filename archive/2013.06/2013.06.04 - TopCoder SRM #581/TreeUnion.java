package src;

public class TreeUnion {
    /*
    permutation same prob.
    N <= 300
    for each K-cycle, find the prob
    count the number of

     sum * (n-2)! / n!
     count the number of vertices distant from v for each v
     */
    public double expectedCycles(String[] tree1, String[] tree2, int K) {
        boolean[][] g1 = make(tree1), g2 = make(tree2);
        int[][] d1 = make(g1), d2 = make(g2);
        int N = g1.length;
        int[][] c1 = new int[N][K + 1];
        int[][] c2 = new int[N][K + 1];
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) if (d1[i][j] <= K) c1[i][d1[i][j]]++;
        for (int i = 0; i < N; i++) for (int j = 0; j < N; j++) if (d2[i][j] <= K) c2[i][d2[i][j]]++;
        double sum = 0;
        for(int i=0;i<N;i++)for(int j=0;j<N;j++){
            for(int i2=1;i2<K;i2++)for(int j2=1;j2<K;j2++)if(2 + i2 + j2 == K){
                int way = c1[i][i2] * c2[j][j2];
                sum += way;
            }
        }
        sum /= 2;
        return sum / N / (N-1);
    }
    private int[][] make(boolean[][] graph) {
        int[][] res = new int[graph.length][graph.length];
        for (int i = 0; i < res.length; i++)
            for (int j = 0; j < res.length; j++) res[i][j] = i == j ? 0 : graph[i][j] ? 1 : 100000;
        for (int k = 0; k < res.length; k++)
            for (int i = 0; i < res.length; i++)
                for (int j = 0; j < res.length; j++) res[i][j] = Math.min(res[i][j], res[i][k] + res[k][j]);
        return res;
    }
    private boolean[][] make(String[] tree) {
        StringBuilder b = new StringBuilder();
        for (String s : tree) b.append(s);
        String[] ss = b.toString().split(
                " "
        );
        int N = ss.length + 1;
        boolean[][] graph = new boolean[N][N];
        for (int i = 0; i < ss.length; i++) {
            int j = Integer.parseInt(ss[i]);
            graph[i + 1][j] = graph[j][i + 1] = true;
        }
        return graph;
    }
}
