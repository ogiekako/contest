package on2017_08.on2017_08_05_TopCoder_Open_Round__1.NonCoprimeGraph;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.Dijkstra;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;
import java.util.Random;

public class NonCoprimeGraph {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int distance(int n, int s, int t) {
        if (s == t) return 0;
        if (MathUtils.gcd(s, t) > 1) return 1;
        if (s == 1 || t == 1) return -1;
        long p = p(s);
        long q = p(t);
        if (p * q <= n) return 2;
        if (2 * p <= n && 2 * q <= n) return 3;
        return -1;
    }

    private int p(int s) {
        for (int p = 2; p * p <= s; p++) if (s % p == 0) return p;
        return s;
    }

    public static void main(String[] args) {
        Random rnd = new Random(4801824L);
        for (int iter = 0; iter < 1000000; iter++) {
            if (iter % 1000 == 0) debug(iter);
            int n = rnd.nextInt(100) + 1;
            int s = rnd.nextInt(n) + 1;
            int t = rnd.nextInt(n) + 1;
            Graph graph = new Graph(n + 1);
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (MathUtils.gcd(i,j) > 1) graph.add(i,j);
                }
            }
            double res = new Dijkstra(graph).sssp(s)[t];
            int exp = res == Double.POSITIVE_INFINITY ? -1 : (int) (res);

            int actual = new NonCoprimeGraph().distance(n,s,t);

            if (exp != actual) {
                debug(n,s,t);
                throw new RuntimeException();
            }
        }
    }
}
