package on2017_09.on2017_09_16_Typical_DP_Contest.P______;



import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class TaskP {
    int[][] graph;
    Mint[][][][] dp;
    Mint[][] C;

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();
        int N = in.nextInt(), K = in.nextInt();
        C = MathUtils.combinationMint(K + 1);
        dp = new Mint[N][][][];
        int[] d = new int[N];
        graph = new int[N][N];
        for (int i = 0; i < N - 1; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            graph[a][d[a]++] = b;
            graph[b][d[b]++] = a;
        }
        for (int i = 0; i < N; i++) {
            graph[i] = Arrays.copyOf(graph[i], d[i]);
            dp[i] = new Mint[d[i] + 1][3][K + 1];
        }
        Mint res = recur(0, 0, -1, 0, K);
        res = res.add(recur(0, 0, -1, 2, K - 1));
        out.println(res);
    }

    private Mint recur(int r, int e, int p, int has, int k) {
        if (e == graph[r].length) {
            return has <= 1 && k == 0 ? Mint.ONE : Mint.ZERO;
        }
        if (graph[r][e] == p) return recur(r, e + 1, p, has, k);
        if (dp[r][e][has][k] != null) return dp[r][e][has][k];

        Mint res = Mint.ZERO;
        for (int i = 0; i <= k; i++) {
            if (has > 0)
                res = res.add(recur(graph[r][e], 0, r, 1, i).mul(recur(r, e + 1, p, has - 1, k - i)));
            res = res.add(recur(graph[r][e], 0, r, 0, i).mul(recur(r, e + 1, p, has, k - i)));
            if (i > 0)
                res = res.add(recur(graph[r][e], 0, r, 2, i - 1).mul(recur(r, e + 1, p, has, k - i)));
        }
        return dp[r][e][has][k] = res;
    }
}
