package tmp;

import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.WeightedEdge;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.io.PrintWriter;
import java.util.Arrays;

public class TaskJ {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        if (N == 0) throw new UnknownError();
        Graph tree = new Graph(N);
        for (int i = 0; i < N - 1; i++) {
            int s = in.nextInt() - 1;
            int t = in.nextInt() - 1;
            int time = in.nextInt();
            tree.add(new WeightedEdge(s, t, time));
            tree.add(new WeightedEdge(t, s, time));
        }
        int res = solve(N, tree);
        out.printf("Case %d: %d\n", testNumber, res);
    }

    public int solve(int N, Graph tree) {
        int left = -1, right = 120;
        do {
            int T = (left + right) / 2;
            if (can(N, tree, T)) right = T;
            else left = T;
        } while (right - left > 1);
        return right;
    }

    /*
    dp[i][j] : node i において,
    子孫 → 子孫に関しては, T 以内という制約を満たし,
    そのノードから(自分を含む)全ての子孫に対して, [-j, k] で行ける
    という割り当てで, 最小のk
     */
    private boolean can(int N, Graph tree, int T) {
        int[][] dp = new int[N][T + 1];// node, minus -> minimum plus
        ArrayUtils.fill(dp, -1);
        for (int minus = 0; minus < T + 1; minus++) {
            if (dfs(0, minus, tree, -1, T, dp) <= T) return true;
        }
        return false;
    }

    int INF = 1 << 28;

    /*
   順番に子孫を見ていって, いま, i-1 番目の子孫までみて,
   i番目を処理したいとする..
   i-1番目までの結果と,
   i番目を子孫とする結果はわかっている.
   i番目の子孫で {-a,b] で, +k の枝を使う場合は, (+k とは, それを使うと, 本来より+k になるということ)
   [min(0,-a+k),b+k] となる.
   -k を使う場合は,
   [-a-k, max(0, b-k)]

   [-a,b] , [-c,d] を組み合わせられるのは,
   -a-c >= -T , b+d <= T の場合のみ.
   それを組み合わせたときの結果は,
   [min(-a,-c), max(b,d)] となる.
   (ここでmin, maxをとっているため, 可能である場合も, 必ずしもdp[root][T] <= T とはならないことに注意)
    */
    private int dfs(int p, int minus, Graph tree, int parent, int T, int[][] dp) {
        if (dp[p][minus] >= 0) return dp[p][minus];
        int[][] dp2 = new int[2][T + 1];
        int cur = 0, nxt = 1;
        for (Edge e : tree.edges(p))
            if (e.to() != parent) {
                Arrays.fill(dp2[nxt], INF);
                for (int a = 0; a < T + 1; a++) {
                    int b = dp2[cur][a];
                    if (b < INF && b > T) throw new AssertionError();
                    for (int na = 0; na < T + 1; na++) {
                        int nb = dfs(e.to(), na, tree, p, T, dp);
                        if (nb < INF && nb > T) throw new AssertionError();
                        // minus
                        int k = (int) (e.cost() % 60);
                        int c = na + k;
                        int d = Math.max(0, nb - k);
                        if (a + c <= T && b + d <= T) {
                            dp2[nxt][Math.max(a, c)] = Math.min(dp2[nxt][Math.max(a, c)], Math.max(b, d));
                        }
                        // plus
                        k = 60 - k;
                        c = Math.max(0, na - k);
                        d = nb + k;
                        if (a + c <= T && b + d <= T) {
                            dp2[nxt][Math.max(a, c)] = Math.min(dp2[nxt][Math.max(a, c)], Math.max(b, d));
                        }
                    }
                }
                int tmp = cur; cur = nxt; nxt = tmp;
            }
        System.arraycopy(dp2[cur], 0, dp[p], 0, T + 1);
        return dp[p][minus];
    }
}