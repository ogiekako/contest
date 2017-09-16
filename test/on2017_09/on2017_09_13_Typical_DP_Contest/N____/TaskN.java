package on2017_09.on2017_09_13_Typical_DP_Contest.N____;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.math.discreteFourierTransform.NTT;

public class TaskN {

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();
        N = in.nextInt();
        graph = new boolean[N][N];
        for (int i = 0; i < N - 1; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            graph[a][b] = graph[b][a] = true;
        }
        nums = new int[N][N];
        ways = new Mint[N][N];

        C = MathUtils.combinationMint(N);
        Mint res = Mint.ZERO;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (!graph[i][j]) continue;
                recur(i, j);
                recur(j, i);

                res = res.add(ways[i][j].mul(ways[j][i]).mul(C[N - 2][nums[i][j]]));
            }
        }
        out.println(res);
    }

    private void recur(int p, int r) {
        if (ways[p][r] != null) return;

        ways[p][r] = Mint.ONE;
        for (int i = 0; i < N; i++) {
            if (i != p && graph[r][i]) {
                recur(r, i);
                nums[p][r] += 1 + nums[r][i];
                ways[p][r] = ways[p][r].mul(ways[r][i]).mul(C[nums[p][r]][1 + nums[r][i]]);
            }
        }
    }

    int N;
    boolean[][] graph;
    Mint[][] C;
    Mint[][] ways;
    int[][] nums;
}