package on2012_4_11.poj3281_maxflow_dining;



import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.GraphAlgorithm;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class POJ3281_MaxFlow_Dining {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int F = in.nextInt();
        int D = in.nextInt();
        Graph graph = new Graph(1+F+N+N+D+1);
        for (int i = 0; i < N; i++) graph.add(new FlowEdge(1+F+i,1+F+N+i,1));
        for (int i = 0; i < F; i++) graph.add(new FlowEdge(0,1+i,1));
        for (int i = 0; i < D; i++) graph.add(new FlowEdge(1+F+N+N+i,1+F+N+N+D,1));
        for (int i = 0; i < N; i++){
            int Fi = in.nextInt();
            int Di = in.nextInt();
            for (int j = 0; j < Fi; j++){
                int f = in.nextInt()-1;
                graph.add(new FlowEdge(1+f,1+F+i,1));
            }
            for (int j = 0; j < Di; j++){
                int d = in.nextInt()-1;
                graph.add(new FlowEdge(1+F+N+i,1+F+N+N+d,1));
            }
        }
        long res = GraphAlgorithm.maxFlow(graph,0,1+F+N+N+D);
        out.println(res);
    }
}
