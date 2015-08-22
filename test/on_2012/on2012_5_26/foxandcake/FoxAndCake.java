package on_2012.on2012_5_26.foxandcake;


// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.Arrays;
import java.util.TreeSet;

public class FoxAndCake {
    public String ableToDivide(int n, int m, int[] xs, int[] ys) {
        int[] sortX = gen(xs, n);
        int[] sortY = gen(ys, m);
        n = sortX.length;
        m = sortY.length;
        int V = n * m * 2 + 2;
        int S = n * m * 2;
        int T = n * m * 2 + 1;
        Graph g = new Graph(V);
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        int bad = Arrays.binarySearch(sortX, xs[0]) * m + Arrays.binarySearch(sortY, ys[0]);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                int a = i * m + j;
                if (a != bad) g.add(new FlowEdge(a, a + n * m, 1.0));
                for (int d = 0; d < 4; d++) {
                    int ni = i + dx[d];
                    int nj = j + dy[d];
                    if (0 <= ni && ni < n && 0 <= nj && nj < m) {
                        int b = ni * m + nj;
                        if (a != bad && b != bad) {
                            g.add(new FlowEdge(a + n * m, b, 1));
                        }
                    }
                }
            }
        for (int i = 0; i < 3; i++) {
            int sx = Arrays.binarySearch(sortX, xs[i + 1]);
            int sy = Arrays.binarySearch(sortY, ys[i + 1]);
            int tx = Arrays.binarySearch(sortX, xs[i + 4]);
            int ty = Arrays.binarySearch(sortY, ys[i + 4]);
            g.add(new FlowEdge(S, sx * m + sy, 1));
            g.add(new FlowEdge(n * m + tx * m + ty, T, 1));
        }
        int res = (int) new MaxFlow(g).maxFlow(S, T);
        System.err.println(res);
        return res == 3 ? "Yes" : "No";
    }

    private int[] gen(int[] xs, int n) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int x : xs) {
            for (int d = -3; d <= 3; d++) {
                int nx = x + d;
                if (1 <= nx && nx <= n) set.add(nx);
            }
        }
        return tois(set.toArray(new Integer[0]));
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }
}

