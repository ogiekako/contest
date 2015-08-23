package on2015_08.on2015_08_22_AOJ.AOJ1095_KND_Factory0;





import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.linearAlgebra.LinearSystem;

import java.util.ArrayList;
import java.util.Arrays;

public class AOJ1095_KND_Factory {
        private static void debug(Object... os) {
                System.out.println(Arrays.deepToString(os));
            }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), s = in.nextInt(), t = in.nextInt();
        int F = in.nextInt();
        double[][] a = new double[N][N];
        double[] c = new double[N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                a[i][j] = in.nextDouble();
            }
            c[i] = in.nextDouble();
        }
        new ArrayList<Integer>();
        double[][] X = LinearSystem.solutionSpace(a, c, 1e-9);
        if (X == null || X.length > 1) throw new AssertionError();
        double[] res = X[0];
        Graph G = new Graph(N);
        for (int i = 0; i < N; i++) {
            int M = in.nextInt();
            int[] d = new int[M];
            for (int j = 0; j < M; j++) {
                d[j] = in.nextInt();
            }
            for (int j = 0; j < M; j++) {
                int f = in.nextInt();
                G.addFlow(i, d[j], f, Math.abs(res[i] - res[d[j]]));
            }
        }

        double ans = new MinimumCostFlow(G).primalDual(s, t, F);
        if (ans == Double.POSITIVE_INFINITY) {
            out.println("impossible");
        } else {
            out.printFormat("%.10f\n", ans);
        }
    }
}
