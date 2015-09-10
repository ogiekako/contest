package on2013_04.on2013_04_22_Codeforces_Round__114__Div__1_.E___Wizards_and_Bets;


import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

// Codeforces Div1 114 (167) E
// http://topcoder.g.hatena.ne.jp/ogiekako/comment?date=20120327
public class TaskE {
    int MOD;

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        MOD = in.nextInt();
        Graph graph = new Graph(n);
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1;
            graph.add(from, to);
        }

        int[] inDeg = new int[n];
        int[] outDeg = new int[n];
        for (int i = 0; i < n; i++)
            for (Edge e : graph.edges(i)) {
                inDeg[e.to()]++;
                outDeg[i]++;
            }

        int k = 0;
        for (int i = 0; i < n; i++) {
//            if (graph.inDegree(i) == 0 && graph.outDegree(i) > 0) k++;
            if (inDeg[i] == 0 && outDeg[i] > 0) k++;
        }

        long res = 1;
        int[] source = new int[k];
        int[] sink = new int[k];
        for (int i = 0, i1 = 0, i2 = 0; i < n; i++) {
//            if (graph.inDegree(i) == 0 && graph.outDegree(i) > 0) source[i1++] = i;
//            if (graph.outDegree(i) == 0 && graph.inDegree(i) > 0) sink[i2++] = i;
//            if (graph.inDegree(i) == 0 && graph.outDegree(i) == 0) res *= (i1 + i2) % 2 == 0 ? 1 : -1;
            if (inDeg[i] == 0 && outDeg[i] > 0) source[i1++] = i;
            if (outDeg[i] == 0 && inDeg[i] > 0) sink[i2++] = i;
            if (inDeg[i] == 0 && outDeg[i] == 0) res *= (i1 + i2) % 2 == 0 ? 1 : -1;
        }

        long[][] A = new long[k][k];
        for (int i = 0; i < k; i++) {
            Integer[] ways = new Integer[n];
            ways[sink[i]] = 1;
            for (int j = 0; j < k; j++) {
                A[j][i] = (long) new Calc<Integer>() {

                    @Override
                    protected Integer op(Integer a, Integer b) {
                        return (a + b) % MOD;
                    }

                    @Override
                    protected Integer identity() {
                        return 0;
                    }
                }.dfs(graph, source[j], ways);
            }
        }

        res *= Matrix.determinant(A, MOD);
        if (res < 0) res += MOD;
        out.println(res);
    }

    abstract class Calc<V> {

        V dfs(Graph dag, int pos, V[] res) {
            if (res[pos] != null) return res[pos];
            res[pos] = identity();
            for (Edge to : dag.edges(pos)) {
                res[pos] = op(res[pos], dfs(dag, to.to(), res));
            }
            return res[pos];
        }

        protected abstract V op(V a, V b);

        protected abstract V identity();
    }
}
