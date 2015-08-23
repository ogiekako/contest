package on2014_10.on2014_10_05_Single_Round_Match_635.StoryFromTCO;


import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

public class StoryFromTCO {
    public static void main(String[] args) {
        new StoryFromTCO().run();
    }

    public int minimumChanges(int[] places, int[] cutoff) {
        int n = places.length;
        ArrayUtils.sortBy(cutoff, places);
        Graph graph = new Graph(2 + n * 2);
        int s = n * 2, t = s + 1;
        for (int i = 0; i < n; i++) {
            graph.addFlow(s, i, 1, 0);
            graph.addFlow(n + i, t, 1, 0);
            if (i + 1 < n) graph.addFlow(n + i, n + i + 1, Integer.MAX_VALUE, 0);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (places[i] <= cutoff[j]) {
                    graph.addFlow(i, n + j, 1, 1);
                    break;
                }
            }
            if (places[i] <= cutoff[i]) {
                graph.addFlow(i, n + i, 1, 0);
            }
        }
        double cost = new MinimumCostFlow(graph).primalDual(s, t, (long) n);
        return cost == Double.POSITIVE_INFINITY ? -1 : (int) cost;
    }

    private void run() {
        int n = 1000;
        Random rnd = new Random(1401820182L);
        int[] p = new int[n];
        int[] c = new int[n];
        for (int o = 0; o < 100; o++) {
            for (int i = 0; i < n; i++) {
                p[i] = rnd.nextInt(400000) + 1;
                c[i] = rnd.nextInt(400000) * 2 + 1;
            }
            long s = System.currentTimeMillis();
            System.out.println(Arrays.toString(p));
            System.out.println(Arrays.toString(c));
            int res = minimumChanges(p, c);
            System.err.println("res=" + res);
            System.err.println(System.currentTimeMillis() - s);
            if (res > 0) break;
        }
    }
}
