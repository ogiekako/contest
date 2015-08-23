package on_2012.on2012_5_19.rabbitworking;


// Paste me into the FileEdit configuration dialog


import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;

public class RabbitWorking {
    private static final double EPS = 1e-9;

    /*
   K = sum x_i
   C = K(200-K)
   P = sum [i<j] x_i x_j p_ij

   maximize obj = P/C

   C = 200(sum x_i) - (sum x_i) (sum x_j)
     = 200(sum x_i) - 2 * (sum [i<j] x_i x_j) - sum x_i
     = 199(sum x_i) - 2 * (sum [i<j] x_i x_j).

   P/C > r
   <=>  P > Cr  <=> sum [i<j] x_i x_j (p_ij + 2r) - 199r(sum x_i) > 0
   <=>  sum[i<j](p_ij+2r) > sum[i<j](1 - x_i x_j)(p_ij+2r) + 199r(sum x_i)

   S(0), T(1)
   x_i \in T => 199r : s -> x_i
   e_ij \in S => p_ij+2r : e_ij -> t
   x_i \in S || x_j \in S => e_ij \in S : x_i, x_j -> e_ij (inf)
    */

    public double getMaximum(String[] profit) {
        int n = profit.length;
        double[][] ps = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) ps[i][j] = profit[i].charAt(j) - '0';

        double left = 0, right = 10000;
        for (int o = 0; o < 200; o++) {
            double r = (left + right) / 2;
            if (calc(ps, r)) left = r;
            else right = r;
        }
        return (left + right) / 2;
    }

    private boolean calc(double[][] ps, double r) {
        int n = ps.length;
        int e = n * (n - 1) / 2;
        Graph graph = new Graph(1 + e + n + 1);
        double sum = 0;
        for (int i = 0, k = 0; i < n; i++) {
            graph.add(new net.ogiekako.algorithm.graph.FlowEdge(0, 1 + e + i, 199 * r));
            for (int j = 0; j < i; j++) {
                sum += ps[i][j] + 2 * r;
                graph.add(new net.ogiekako.algorithm.graph.FlowEdge(1 + k, 1 + e + n, ps[i][j] + 2 * r));
                graph.add(new net.ogiekako.algorithm.graph.FlowEdge(1 + e + i, 1 + k, Double.POSITIVE_INFINITY));
                graph.add(new net.ogiekako.algorithm.graph.FlowEdge(1 + e + j, 1 + k, Double.POSITIVE_INFINITY));
                k++;
            }
        }
        double flow = new MaxFlow(graph).maxFlow(0, 1 + e + n);
        return flow < sum - EPS;
    }
}
