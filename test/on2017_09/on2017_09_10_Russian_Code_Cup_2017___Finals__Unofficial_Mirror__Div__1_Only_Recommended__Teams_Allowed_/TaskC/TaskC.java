package on2017_09.on2017_09_10_Russian_Code_Cup_2017___Finals__Unofficial_Mirror__Div__1_Only_Recommended__Teams_Allowed_.TaskC;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskC {

    int MOD = 998244353;

    long[][] C = MathUtils.combinationMod(2010, MOD);
    long[] fact = MathUtils.factorialMod(2010, MOD);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int n0 = 0, n1 = 0;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            if (("" + a[i]).length() % 2 == 0) n0++;
            else n1++;
        }
        int[] a0 = new int[n0];
        int[] a1 = new int[n1];
        n0 = 0;
        n1 = 0;
        for (int i = 0; i < n; i++) {
            if (("" + a[i]).length() % 2 == 0) {
                a0[n0++] = a[i] % 11;
            } else {
                a1[n1++] = a[i] % 11;
            }
        }
        long[][] dp0 = f(n0, a0);
        long[][] dp1 = f(n1, a1);

        long res = 0;
        for (int n00 = 0; n00 <= n0; n00++) {
            for (int n10 = 0; n10 <= n1; n10++) {
                int n01 = n0 - n00;
                int n11 = n1 - n10;
                if (n10 == n11 || n10 == n11 + 1) {

                    for (int mod0 = 0; mod0 < 11; mod0++) {
                        int mod1 = 11 - mod0;
                        if (mod1 == 11) mod1 = 0;

                        long val = dp0[n00][mod0] * dp1[n10][mod1] % MOD;
                        val = val * fact[n00] % MOD * C[n00 + n11][n00] % MOD;
                        val = val * (n01 == 0 ? 1 : fact[n01] * C[n01 + n10 - 1][n01] % MOD) % MOD;
                        val = val * fact[n10] % MOD;
                        val = val * fact[n11] % MOD;

                        res = res + val;
                        if (res >= MOD) res -= MOD;
                    }

                }
            }
        }
        out.println(res);
    }

    long[][] f(int n0, int[] a0) {
        long[][][] dp0 = new long[2][n0 + 1][11];
        dp0[0][0][0] = 1;
        int cur = 0, nxt = 1;
        for (int i = 0; i < n0; i++) {
            ArrayUtils.fill(dp0[nxt], 0);
            for (int j = 0; j < n0 + 1; j++) {
                for (int k = 0; k < 11; k++) {
                    if (dp0[cur][j][k] == 0) continue;
                    int nk = k + a0[i];
                    if (nk >= 11) nk -= 11;
                    dp0[nxt][j + 1][nk] += dp0[cur][j][k];
                    if (dp0[nxt][j + 1][nk] >= MOD) dp0[nxt][j + 1][nk] -= MOD;
                    nk = k - a0[i];
                    if (nk < 0) nk += 11;
                    dp0[nxt][j][nk] += dp0[cur][j][k];
                    if (dp0[nxt][j][nk] >= MOD) dp0[nxt][j][nk] -= MOD;
                }
            }
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        return dp0[cur];
    }
}
