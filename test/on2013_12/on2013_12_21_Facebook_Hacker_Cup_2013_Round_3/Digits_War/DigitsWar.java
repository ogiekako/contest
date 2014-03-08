package on2013_12.on2013_12_21_Facebook_Hacker_Cup_2013_Round_3.Digits_War;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

public class DigitsWar {
    int MOD = (int) (1e9 + 7);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long K = in.nextLong();
        boolean[][] war = new boolean[11][11];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                war[i][j] = in.nextInt() == 1;
            }
        }
        long[][] A = new long[11 * 11][11 * 11];
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                for (int k = 0; k < 11; k++) {
                    if (!war[j][k] && !war[i][k]) A[j * 11 + k][11 * i + j] = 1;
                    if (k == 10) A[j * 11 + k][11 * i + j] = 0;
                }
        long[] x = new long[11 * 11];
        for (int i = 1; i <= 9; i++) x[10 * 11 + i] = 1;
        long[] y = Matrix.sumPowered(A, K, x, MOD);
        long res = 0;
        for (long yy : y) res = (res + yy) % MOD;
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }
}
