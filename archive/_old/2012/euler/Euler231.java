package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class Euler231 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), K = in.nextInt();
        int[] divTable = MathUtils.generateDivisorTable(N);
        long[] sum = new long[N + 1];
        for (int i = 1; i <= N; i++) {
            sum[i] = sum[i - 1];
            int tmp = i;
            while (tmp > 1) {
                sum[i] += divTable[tmp];
                tmp /= divTable[tmp];
            }
        }
        out.println(sum[N] - sum[N - K] - sum[K]);
    }
}
