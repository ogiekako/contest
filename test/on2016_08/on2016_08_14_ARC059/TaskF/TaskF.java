package on2016_08.on2016_08_14_ARC059.TaskF;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;

import java.util.Arrays;

public class TaskF {
    int MOD = (int) (1e9 + 7);

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();
        int N = in.nextInt();
        int M = in.next().length();

        int res = solve(N, M);
        out.println(res);
    }

    private long[] computeStep(int N, int M) {
        int[][] stepDp = new int[2][N + 1];
        int cur=0,nxt=1;
        stepDp[cur][0] = 1;
        long[] step = new long[N + 1];
        for (int i = 0; i <= N; i++) {
            step[i] = stepDp[cur][M];
            Arrays.fill(stepDp[nxt],0);
            for (int j = 0; j < N; j++) {
                if(stepDp[cur][j] == 0) continue;
                stepDp[nxt][j + 1] += stepDp[cur][j];
                if (stepDp[nxt][j + 1] >= MOD) {
                    stepDp[nxt][j + 1] -= MOD;
                }
                stepDp[nxt][Math.max(0, j - 1)] += stepDp[cur][j];
                if (stepDp[nxt][Math.max(0, j - 1)] >= MOD) {
                    stepDp[nxt][Math.max(0, j - 1)] -= MOD;
                }
            }
            int tmp=cur;cur=nxt;nxt=tmp;
        }
        return step;
    }

    private int solve(int N, int M) {
        long[] step = computeStep(N, M);

        two = new Mint[N+1];
        for (int i = 0; i < N + 1; i++)
            two[i] = i==0 ? Mint.ONE : two[i-1].mul(2);

        Mint res = Mint.ZERO;
        for (int t = 0; t < N + 1; t++) if(t%2==0) {
            for (int k = 0; k < N + 1; k++) {
                if (N-t+k < 0 || t/2-k<0) continue;

                if (step[N - t] > 0) {
                    Mint c = MathUtils.comb(N - t + k, k);
                    Mint det = det(t,k);
                    Mint val = c.mul(step[N - t]).mul(det);
                    res = res.add(val);
                }
            }
        }
        return res.get();
    }
    Mint[] two;
    Mint det(int i, int J) {
        if(i==0 && J==0)return Mint.ONE;
        int n = i / 2;
        if(n-J < 0) return Mint.ZERO;
        return MathUtils.catalanTransposed(n,J).mul(two[n-J]);
    }
}
