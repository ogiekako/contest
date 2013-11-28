package tmp;

import net.ogiekako.algorithm.convolution.SubsetConvolution;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class KOSARE {
    int MOD = (int) (1e9 + 7);
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        int[] counts = new int[1 << M];
        for (int i = 0; i < N; i++) {
            int K = in.nextInt();
            int mask = 0;
            for (int j = 0; j < K; j++) {
                mask |= 1 << in.nextInt() - 1;
            }
            counts[mask]++;
        }
        int res = solve(N, M, counts);
        out.println(res);
    }

    public int solve(int N, int M, int[] counts) {
        int[] two = new int[N + 1];
        for (int i = 0; i < N + 1; i++) {
            two[i] = i == 0 ? 1 : two[i - 1] + two[i - 1];
            if (two[i] >= MOD) two[i] -= MOD;
        }
        int[] sum = SubsetConvolution.zetaConvolution(counts, M);
        for (int i = 0; i < sum.length; i++) {
            sum[i] = two[sum[i]];
        }
        int res = 0;
        for (int i = 0; i < 1 << M; i++) {
            if ((Integer.bitCount(i) & 1) == 0) {
                res += sum[(1 << M) - 1 ^ i];
                if (res >= MOD) res -= MOD;
            } else {
                res -= sum[(1 << M) - 1 ^ i];
                if (res < 0) res += MOD;
            }
        }
        return res;
    }

}
