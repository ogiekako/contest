package on2017_07.on2017_07_23_2017_TopCoder_Open_Algorithm.AngelDemonGame;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;

import java.util.Arrays;
import java.util.Random;

public class AngelDemonGame {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    String Demon = "Demon";
    String Unknown = "Unknown";
    public String winner(String[] G, int A, int D) {
        int n = G.length;
        Graph graph = new Graph(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    graph.addFlow(i, j, 1, G[i].charAt(j) == 'Y' ? 0 : 1);
                }
            }
        }
        double cost = new MinimumCostFlow(graph).primalDual(0, n - 1, D + 1);
        if (cost <= A) {
            return "Angel";
        }
        if (n - 1 <= D) {
            return Demon;
        }
        return Unknown;
    }
}
