package tmp;

import net.ogiekako.algorithm.graph.FlowEdge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCutTree;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class G {
    int next(MyScanner in) {
        String s = in.next();
        return Integer.valueOf(s.substring(0, s.length() - 1));
    }
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        Arrays.fill(z, 1);
        for (; ; ) {
            n = next(in);
//            debug(n);
            if (n == 0) return;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) {
                    a[i][j] = next(in);
//                    debug(n);
                }
            int res = (int) (solve(a, n) * 3);
            out.println(res);
//            int exp = solveStupid();
//            System.err.println(res + " " +exp);
        }
    }
    public int solveStupid() {
        z[0] = 1;
        z[1] = 4;
        answer = 1000000000;
        bunny(2);
        int position = 0;
        int i, j;
//        for (; ; ) {
//            n = data[position++];
//            debug(n);
//            if (n == 0) {
//                break;
//            }
//            for (i = 0; i < n; ++i) {
//                for (j = 0; j < n; ++j) {
//                    a[i][j] = data[position++];
//                }
//            }
//            z[0] = 1;
//            z[1] = 4;
//            answer = 1000000000;
//            bunny(2);
//            out.printf("%d\n", answer);
        return answer;
    }

    private long solve(int[][] a, int n) {
        Graph graph = new Graph(n);
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                graph.add(new FlowEdge(i, j, a[i][j]));
            }
        return MinimumCutTree.maxFlow(graph, 0, 1);
    }

    int n;
    int[][] a = new int[40][40];
    int[] z = new int[40];
    int answer;

    void bunny(int i) {
        int j, k;
        int sum = 0;
        if (i == n) {
            for (j = 0; j < n; ++j) {
                for (k = 0; k < n; ++k) {
                    if (z[j] < z[k]) {
                        sum += a[j][k] * (z[k] - z[j]);
                    }
                }
            }
            if (answer > sum) {
                answer = sum;
            }
        } else {
            for (z[i] = 1; z[i] <= 4; ++z[i]) {
                bunny(i + 1);
            }
        }
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
//        int m = 500;
//        for(int x=0;x<m;x++)for(int y=0;y<m;y++){
//            System.out.printf("%07d%c",new G().rabbit(x,y),y==m-1?'\n':' ');
//        }
//        new G().solve();
    }
}
