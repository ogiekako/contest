package on2015_08.on2015_08_25_TopCoder_SRM__658.DancingForever;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DancingForever {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] getStablePairs(String x) {
        int n = (int) MathUtils.sqrt(x.length());
        int s = n * 2, t = n * 2 + 1;
        Graph G = new Graph(n * 2 + 2);
        for (int i = 0; i < n; i++) {
            G.addFlow(s, i, 1);
            G.addFlow(n + i, t, 1);
            for (int j = 0; j < n; j++) {
                if (x.charAt(i * n + j) == 'Y') G.addFlow(i, n + j, 1);
            }
        }
        MaxFlow maxFlow = new MaxFlow(G);
        double flow = maxFlow.maxFlow(s, t);
        if (flow == n) {
            int[] matching = new int[n * 2];
            for (int i = 0; i < n; i++) {
                matching[i * 2] = i;
                for (Edge e : G.edges(i)) {
                    if (e.residue() > 0) continue;
                    if (e.to() < n || e.to() >= n * 2) continue;
                    matching[i * 2 + 1] = e.to() - n;
                }
            }
            return matching;
        }

        List<Integer> reachables = maxFlow.getReachableVerticesFrom(s);
        List<Integer> R = new ArrayList<Integer>();
        for (int i : reachables) if(n <= i && i < n * 2) R.add(i - n);
        int[] matching = new int[R.size() * 2];
        for (int i = 0; i < R.size(); i++) {
            matching[i * 2 + 1] = R.get(i);
            for (Edge e : G.edges(n + R.get(i))) {
                if (e.residue() <= 0) continue;
                if (e.to() >= n) continue;
                matching[i * 2] = e.to();
            }
        }
        return matching;
    }
}
