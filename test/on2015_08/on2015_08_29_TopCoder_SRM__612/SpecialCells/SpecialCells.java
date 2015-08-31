package on2015_08.on2015_08_29_TopCoder_SRM__612.SpecialCells;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.utils.Cast;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class SpecialCells {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int guess(int[] x, int[] y) {
        Set<Integer> xSet = new TreeSet<Integer>();
        Set<Integer> ySet = new TreeSet<Integer>();
        for (int i : x) xSet.add(i);
        for (int i : y) ySet.add(i);
        Graph G = new Graph(xSet.size() + ySet.size() + 2);
        int s = xSet.size() + ySet.size();
        int t = s + 1;
        int[] xs = Cast.toInt(xSet), ys = Cast.toInt(ySet);
        for (int i = 0; i < xSet.size(); i++) {
            for (int j = 0; j < ySet.size(); j++) {
                boolean has = false;
                for (int k = 0; k < x.length; k++) {
                    if (xs[i] == x[k] && ys[j] == y[k]) has = true;
                }
                G.addFlow(i, xSet.size() + j, 1, has ? 1 : 0);
            }
        }
        for (int i = 0; i < xs.length; i++) {
            int d = 0;
            for (int xx : x) if (xs[i] == xx) d++;
            G.addFlow(s, i, d, 0);
        }
        for (int i = 0; i < ys.length; i++) {
            int d = 0;
            for (int yy : y) if (ys[i] == yy) d++;
            G.addFlow(xs.length + i, t, d, 0);
        }

        return (int) new MinimumCostFlow(G).primalDual(s, t, x.length);
    }
}
