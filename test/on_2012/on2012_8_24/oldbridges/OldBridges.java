package on_2012.on2012_8_24.oldbridges;


// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

public class OldBridges {
    public String isPossible(String[] bridges, int a1, int a2, int an, int b1, int b2, int bn) {
        return ok(bridges, a1, a2, an, b1, b2, bn) && ok(bridges, a1, a2, an, b2, b1, bn) ? "Yes" : "No";
    }

    private boolean ok(String[] bridges, int a1, int a2, int an, int b1, int b2, int bn) {
        int n = bridges.length;
        Graph graph = new Graph(n + 2);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                char c = bridges[i].charAt(j);
                if (c == 'O') graph.add(new FlowEdge(i, j, 2));
                else if (c == 'N') graph.add(new FlowEdge(i, j, 300));
            }
        int s = n, t = n + 1;
        graph.add(new FlowEdge(s, a1, 2 * an));
        graph.add(new FlowEdge(s, b1, 2 * bn));
        graph.add(new FlowEdge(a2, t, 2 * an));
        graph.add(new FlowEdge(b2, t, 2 * bn));
        return MaxFlow.maxFlow(graph, s, t) >= 2 * (an + bn);
    }
}

