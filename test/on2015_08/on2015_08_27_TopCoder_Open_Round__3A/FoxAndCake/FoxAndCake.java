package on2015_08.on2015_08_27_TopCoder_Open_Round__3A.FoxAndCake;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.Arrays;
import java.util.TreeSet;

public class FoxAndCake {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }


    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public String ableToDivide(int n, int m, int[] x, int[] y) {
        TreeSet<Integer> xSet = new TreeSet<Integer>();
        TreeSet<Integer> ySet = new TreeSet<Integer>();
        for (int t : x) for (int i = t - 3; i <= t + 3; i++) if (1 <= i && i <= n) xSet.add(i);
        for (int t : y) for (int i = t - 3; i <= t + 3; i++) if (1 <= i && i <= m) ySet.add(i);
        int[] xs = tois(xSet.toArray(new Integer[0]));
        int[] ys = tois(ySet.toArray(new Integer[0]));
        int[][] from = new int[xs.length][ys.length];
        int[][] to = new int[xs.length][ys.length];
        int N = 0;
        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from[0].length; j++) {
                from[i][j] = N++;
                to[i][j] = N++;
            }
        }
        int s = N++, t = N++;
        Graph G = new Graph(N);
        for (int i = 0; i < from.length; i++) {
            for (int j = 0; j < from[0].length; j++) {
                if (xs[i] == x[0] && ys[j] == y[0]) continue;
                for (int d = 0; d < 4; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    if (0 <= ni && ni < xs.length && 0 <= nj && nj < ys.length) {
                        G.addFlow(to[i][j], from[ni][nj], 1);
                    }
                }
                for (int k = 1; k < 4; k++) {
                    if (xs[i] == x[k] && ys[j] == y[k]) {
                        G.addFlow(s, from[i][j], 1);
                    }
                }
                for (int k = 4; k < 7; k++) {
                    if (xs[i] == x[k] && ys[j] == y[k]) {
                        G.addFlow(to[i][j], t, 1);
                    }
                }
                G.addFlow(from[i][j], to[i][j], 1);
            }
        }
        return new MaxFlow(G).maxFlow(s, t) == 3 ? "Yes" : "No";
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) {
            is[i] = Is[i];
        }
        return is;
    }
}
