package on2015_08.on2015_08_21_AOJ_GRL.SSSP;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.Dijkstra;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class SSSP {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        int r = in.nextInt();
        Graph graph = new Graph(n);
        for (int i = 0; i < m; i++) {
            int s = in.nextInt(), t = in.nextInt(), c = in.nextInt();
            graph.addWeighted(s,t,c);
        }
        double[] res = new Dijkstra(graph).sssp(r);
        for(double d:res){
            out.println(d == Double.POSITIVE_INFINITY ? "INF" : (long)d);
        }
    }
}
