package tmp;

import net.ogiekako.algorithm.graph.denseGraph.SparseGraph;
import net.ogiekako.algorithm.graph.denseGraph.unused.UnusedVertex;
import net.ogiekako.algorithm.graph.problems.Combination;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt(), modulo = in.nextInt();
        UnusedVertex[] vs = new UnusedVertex[n];
        for (int i = 0; i < n; i++) vs[i] = new UnusedVertex();
        for (int i = 0; i < m; i++) {
            int s = in.nextInt() - 1, t = in.nextInt() - 1;
            vs[s].add(vs[t]);
            vs[t].add(vs[s]);
        }
        UnusedVertex[][] components = SparseGraph.connectedComponents(vs);
        int[] sizes = new int[components.length];
        for (int i = 0; i < sizes.length; i++) sizes[i] = components[i].length;
        long res = Combination.numWayToUnionComponentsWithMinimumEdges(sizes, modulo);
        out.println(res);
    }
}
