package src;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class AOJ2313 {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), E = in.nextInt(), Q = in.nextInt();
        Graph graph = new Graph(N);
        Edge[][] edge = new Edge[N][N];
        for (int i = 0; i < E; i++) {
            add(in, graph, edge);
        }
        MaxFlow mf = new MaxFlow(graph);
        int source = 0, sink = N - 1;
        long flow = mf.maxFlow(source, sink);
        for (int i = 0; i < Q; i++) {
            if (in.nextInt() == 1) {
                add(in, graph, edge);
                flow += mf.maxFlow(source, sink);
            } else {
                if (flow > 0) flow -= mf.maxFlow(sink, source, 1);
                int x = in.nextInt() - 1, y = in.nextInt() - 1;
                if (edge[x][y].flow() == 1) mf.maxFlow(x, y, 1);
                graph.remove(edge[x][y]);
                if (edge[y][x].flow() == 1) mf.maxFlow(y, x, 1);
                graph.remove(edge[y][x]);
                flow += mf.maxFlow(source, sink);
            }
            out.println(flow);
        }
    }
    private void add(MyScanner in, Graph graph, Edge[][] edge) {
        int x = in.nextInt() - 1, y = in.nextInt() - 1;
        edge[x][y] = new FlowEdge(x, y, 1);
        edge[y][x] = edge[x][y].transposed();
        graph.add(edge[x][y]);
        graph.add(edge[y][x]);
    }
}
