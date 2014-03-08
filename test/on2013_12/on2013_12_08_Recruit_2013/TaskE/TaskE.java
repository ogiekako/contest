package on2013_12.on2013_12_08_Recruit_2013.TaskE;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            int A = in.nextInt(), N = in.nextInt();
            int S = 0;
            for (int i = 0; i < N; i++) {
                S += in.nextInt();
            }
            double[][] dp = new double[A + 1][S + 1];
            dp[A][S] = 1;
            for (int i = A; i >= 1; i--)
                for (int j = S; j >= 1; j--) {
                    double lose = 21.0 / 36;
                    double win = 1 - lose;
                    dp[i - 1][j] += dp[i][j] * lose;
                    dp[i][j - 1] += dp[i][j] * win;
                }
            double res = 0;
            for (int i = 0; i <= A; i++) {
                res += i * dp[i][0];
            }
            out.printFormat("%.12f\n", res);
        }
    }
}
