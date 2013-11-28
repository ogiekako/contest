package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskE {
    int INF = (int) 1e9;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int M = in.nextInt();
        int[] dice = new int[6];
        for (int i = 0; i < 6; i++) dice[i] = in.nextInt();
        int s = in.nextInt() - 1, g = in.nextInt() - 1;
        int[] N = new int[M];
        for (int i = 0; i < M; i++) N[i] = in.nextInt();
        int[][] graph = new int[M][M];
        for (int i = 0; i < M; i++) Arrays.fill(graph[i], INF);
        for(int i=0;i<M;i++)graph[i][i]
                =0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < 6; j++) {
                int ni = i + dice[j];
                if (0 <= ni && ni < M) graph[i][ni + N[ni]] = 1;
                ni = i - dice[j];
                if (0 <= ni && ni < M) graph[i][ni + N[ni]] = 1;
            }
        }
        for (int k = 0; k < M; k++)
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        for (; ; ) {
            if(s==g)break;
            debug(s);
            int p = dice[in.nextInt() - 1];
            if (s + p < M) {
                int ns = s + p + N[s + p];
                if (graph[s][g] == graph[ns][g] + 1) {
                    s=ns;
                    out.println(1);
                    out.flush();
                    continue;
                }
            }
            if (s - p >= 0) {
                int ns = s - p + N[s - p];
                if (graph[s][g] == graph[ns][g] + 1) {
                    s=ns;
                    out.println(-1);
                    out.flush();
                    continue;
                }
            }
            out.println(0);
            out.flush();
        }
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
}
