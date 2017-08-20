package on2017_08.on2017_08_19_TopCoder_Open_Round__3A.ParenthesesAndPermutation;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

import java.util.ArrayList;
import java.util.Arrays;

public class ParenthesesAndPermutation {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String getSequence(int[] p) {
        int n = p.length;
        Graph graph = new Graph(n * 2 + 2);
        int s = n, t = 2 * n + 1;
        ArrayList<Edge> es = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.addFlow(i + 1, i, (i + 1) / 2);
            graph.addFlow(n + 1 + i, n + 2 + i, (i + 1) / 2);
            es.add(graph.addFlow(i, n + 1 + p[i], 1));
        }
        MaxFlow maxFlow = new MaxFlow(graph);
        double flow = maxFlow.maxFlow(s, t);
        if (flow != n / 2) {
            return "Impossible";
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (es.get(i).flow() > 0) {
                res.append(")");
            } else {
                res.append("(");
            }
        }
        return res.toString();
    }
}
