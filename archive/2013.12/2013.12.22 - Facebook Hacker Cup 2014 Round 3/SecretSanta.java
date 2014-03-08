package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SecretSanta {
    static int MOD = (int) (1e9 + 7);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        System.err.println("test " + testNumber);
        int K = in.nextInt();
        int[] ns = new int[K];
        for (int i = 0; i < K; i++) {
            ns[i] = in.nextInt();
        }
        long res = solve(K, ns);

//        int[] count = new int[5];
//        for (int i = 0; i < K; i++) {
//            int n = in.nextInt();
//            count[n]++;
//        }
//        long[][][][] dp = new long[26][35][51][101];
//        dp[count[4]][count[3]][count[2]][count[1]] = 1;
//
//        for(int a4=25;a4>=0;a4--)for(int a3=34;a3>=0;a3--)for(int a2=50;a2>=0;a2--)for(int a1=100;a1>=0;a1--){
//            if(dp[a4][a3][a2][a1] == 0) continue;
//            int[] as = {0,a1,a2,a3,a4};
//            for(int i=0;i<4;i++){
//                if(as[i] > 0) {
//
//                }
//            }
//        }
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }
    private long solve(int K, int[] ns) {
        long[] dp = new long[110];
        long[][] C = MathUtils.combinationMod(110, MOD);
        long[] F = MathUtils.factorialMod(110, MOD);
        dp[0] = 1;
        for (int i = 0; i < K; i++) {
            long[] nDp = new long[110];
            int n = ns[i];
            for (int j = 0; j < dp.length; j++)
                if (dp[j] > 0) {
                    for (int rec = 0; rec <= n; rec++)
                        for (int sen = 0; sen <= n; sen++) {
                            if (sen <= j && rec <= j) {
                                int nj = j - rec + n - sen;
                                nDp[nj] = (nDp[nj] + dp[j] * C[j][sen] % MOD * F[sen] % MOD * C[j][rec] % MOD * F[rec] % MOD
                                * C[n][rec] % MOD * C[n][sen]) % MOD;
                            }
                        }
                }
            dp = nDp;
        }
        return dp[0];
    }
    public static void main(String[] args) {
        int all = 0;
        List<int[]> probs = new ArrayList<>();
        for (int a4 = 0; a4 <= 25; a4++)
            for (int a3 = 0; a3 <= 100; a3++)
                for (int a2 = 0; a2 <= 50; a2++)
                    for (int a1 = 0; a1 <= 100; a1++) {
                        if (a4 * 4 + a3 * 3 + a2 * 2 + a1 <= 100){
                            probs.add(new int[]{a4,a3,a2,a1});
                            all++;
                        }
                    }
        Random rnd = new Random();
        for(int t=0;t<100;t++){
            System.err.println(t);
            int p = rnd.nextInt(probs.size());
            int[] as = probs.get(p);
            int K = 0;
            for(int a:as)K += a;
            int[] ns = new int[K];
            int q = 0;
            for(int i=0;i<4;i++){
                int a = as[i];
                for(int j=0;j<a;j++){
                    ns[q+j] = 4-i;
                }
                q += a;
            }
            long exp = new SecretSanta().solve(K, ns);
            debug(ns, exp);
            ArrayUtils.shuffle(ns);
            long res = new SecretSanta().solve(K, ns);
            debug(ns, res);
            if(exp != res || exp < 0 || exp >= MOD) throw new AssertionError();
        }
        System.out.println(all);

    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

}
