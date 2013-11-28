package tmp;

import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.WeightedEdge;
import net.ogiekako.algorithm.utils.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: ogiekako
 * Date: 12/05/06
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class TaskJTest {
    @Test
    public void testSolve() throws Exception {
        Random rnd = new Random(1209128L);
        for (int o = 0; o < 100; o++) {
            int n = 10;
            Graph tree = new Graph(n);
            UnionFind uf = new UnionFind(n);
            for (int i = 0; i < n - 1; ) {
                int s = rnd.nextInt(n);
                int t = rnd.nextInt(n);
                if (s == t || uf.find(s, t)) continue;
                i++;
                int time = rnd.nextInt(60) + 1;
                tree.add(new WeightedEdge(s, t, time));
                tree.add(new WeightedEdge(t, s, time));
                uf.union(s, t);
            }
            int res = new TaskJ().solve(n, tree);
            int exp = solveStupid(n, tree);
            if (res != exp) {
                for (int i = 0; i < n; i++)
                    for (Edge e : tree.edges(i))
                        if (i < e.to()) {
                            System.out.printf("%d %d %d\n", i, e.to(), e.cost());
                        }
            }
            Assert.assertEquals(exp, res);
        }
    }

    private int solveStupid(int N, Graph tree) {
        int[][] real = new int[N][N];
        ArrayUtils.fill(real, 1 << 28);
        for (int i = 0; i < N; i++) real[i][i] = 0;
        for (int i = 0; i < N; i++) for (Edge e : tree.edges(i)) real[i][e.to()] = (int) (e.cost() % 60);
        for (int k = 0; k < N; k++)
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                    real[i][j] = Math.min(real[i][j], real[i][k] + real[k][j]);
                }
        int res = 1 << 28;
        for (int mask = 0; mask < 1 << N - 1; mask++) {
            int[][] graph = new int[N][N];
            ArrayUtils.fill(graph, 1 << 28);
            for (int i = 0; i < N; i++) graph[i][i] = 0;
            int p = 0;
            for (int i = 0; i < N; i++)
                for (Edge e : tree.edges(i))
                    if (i < e.to()) {
                        int weight = (mask >> p & 1) == 1 ? 0 : 60;
                        p++;
                        graph[i][e.to()] = graph[e.to()][i] = weight;
                    }
            for (int k = 0; k < N; k++)
                for (int i = 0; i < N; i++)
                    for (int j = 0; j < N; j++) {
                        graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                    }
            int maxDiff = 0;
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++) {
                    maxDiff = Math.max(maxDiff, Math.abs(real[i][j] - graph[i][j]));
                }
            res = Math.min(res, maxDiff);
        }
        return res;
    }
}
