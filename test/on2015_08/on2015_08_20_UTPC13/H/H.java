package on2015_08.on2015_08_20_UTPC13.H;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.graphDouble.GraphAlgorithm;

import java.util.Scanner;
import java.io.PrintWriter;

public class H {
    int N,M;
    int[] p,q;

    public void solve(int testNumber, Scanner in, PrintWriter out) {
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
            ix[i] = i;
            iy[i] = N + i;
        }
        int source = 2 * N;
        for (int i = 0; i < N; i++) {
            graph.addWeighted(source, ix[i], p[i]);
            graph.addWeighted(ix[i], source, 0);
            graph.addWeighted(source, iy[i], q[i]);
            graph.addWeighted(iy[i], source, 0);
        }
        for (int k = 0; k < M; k++) {
            int i = in.nextInt() - 1, j = in.nextInt() - 1;
            int a = in.nextInt(), b = in.nextInt();
            graph.addWeighted(ix[i], iy[j], q[j] - a);
            graph.addWeighted(iy[j], ix[i], b - q[j]);
        }

    }
}
