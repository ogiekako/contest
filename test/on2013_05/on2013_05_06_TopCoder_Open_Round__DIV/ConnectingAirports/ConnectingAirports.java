package on2013_05.on2013_05_06_TopCoder_Open_Round__DIV.ConnectingAirports;


import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

// TCO'09 R2 900
public class ConnectingAirports {
    public String[] getSchedule(int[] capacityA, int[] capacityU) {
        int n = capacityA.length, m = capacityU.length;
        int S = 0;
        for (int c : capacityA) S += c;
        for (int c : capacityU) S -= c;
        if (S != 0) return new String[0];
        for (int c : capacityA) S += c;
        Graph graph = new Graph(n + m + 2);
        int source = n + m, sink = source + 1;
        for (int i = 0; i < n; i++) graph.addFlow(source, i, capacityA[i]);
        for (int i = 0; i < m; i++) graph.addFlow(n + i, sink, capacityU[i]);
        Edge[][] edge = new Edge[n][m];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) edge[i][j] = new FlowEdge(i, n + j, 1);
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) graph.add(edge[i][j]);
        double maxFlow = new MaxFlow(graph).maxFlow(source, sink);
        if (maxFlow < S - 1e-9) return new String[0];
        String[] res = new String[n];
        for (int i = 0; i < n; i++) res[i] = "";
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                if (edge[i][j].flow() == 0) {
                    graph.remove(edge[i][j]);
                    res[i] += '0';
                } else {
                    double flow = new MaxFlow(graph).maxFlow(i, n + j, 1);
                    if (flow < 1e-9) {
                        res[i] += '1';
                    } else {
                        graph.remove(edge[i][j]);
                        res[i] += '0';
                    }
                }
            }
        return res;
    }
}
