package on2015_08.on2015_08_21_TopCoder_Open_Round__2C.CliqueGraph;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.UndirectedGraph;
import net.ogiekako.algorithm.graph.algorithm.Dijkstra;

import java.util.Arrays;

public class CliqueGraph {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long calcSum(int N, int[] V, int[] sizes) {
        int K = sizes.length;
        Graph G = new UndirectedGraph(N + K);
        for (int i = 0, p = 0; i < K; i++) {
            for (int j = 0; j < sizes[i]; j++) {
                G.add(V[p++], N + i);
            }
        }
        Dijkstra dijkstra = new Dijkstra(G);
        long res = 0;
        for (int i = 0; i < N; i++) {
            double[] dist = dijkstra.sssp(i);
            for (int j = 0; j < i; j++) {
                res += (long) dist[j] / 2;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        new CliqueGraph().test();
    }

    private void test() {
        testLarge();
    }

    private void testLarge() {
        int N = 2500;
        for (int i = 0; i < 100; i++) {
            System.out.println(i);

        }
    }
}
