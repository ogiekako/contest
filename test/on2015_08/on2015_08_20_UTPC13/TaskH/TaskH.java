package on2015_08.on2015_08_20_UTPC13.TaskH;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.BellmanFord;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskH {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        int[] p = new int[N], q = new int[N];
        for (int i = 0; i < N; i++) {
            p[i] = in.nextInt();
        }
        for (int i = 0; i < N; i++) {
            q[i] = in.nextInt();
        }
        Graph graph = new Graph(2 * N + 1);
        int[] ix = new int[N], iy = new int[N];
        for (int i = 0; i < N; i++) {
            ix[i] = i + 1;
            iy[i] = N + i + 1;
        }
        int source = 0;
        for (int i = 0; i < N; i++) {
            graph.addWeighted(source, ix[i], p[i]);
            graph.addWeighted(ix[i], source, 0);
            graph.addWeighted(source, iy[i], 0);
            graph.addWeighted(iy[i], source, q[i]);
        }
        for (int k = 0; k < M; k++) {
            int i = in.nextInt() - 1, j = in.nextInt() - 1;
            int a = in.nextInt(), b = in.nextInt();
            graph.addWeighted(ix[i], iy[j], -a);
            graph.addWeighted(iy[j], ix[i], b);
        }
//        out.println(new BellmanFord(graph).sssp(source) == null ? "no" : "yes");
        out.println(new BellmanFord(graph).hasNegativeCycle() ? "no" : "yes");
    }
}
