package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;

public class PrimePermutations {
    static int[] answers = new int[5000001];
    static int MOD = 1000000007;
    static {
        boolean[] isPrime = MathUtils.generatePrimaryTable(answers.length);
        answers[1] = 1;
        int cnt = 1;
        for (int i = 2; i < answers.length; i++) {
            if (isPrime[i]) cnt++;
            answers[i] = (int) ((long) answers[i - 1] * cnt % MOD);
        }
    }
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        out.println(answers[in.nextInt()]);
    }

//    public static void main(String[] args) {
//        long[] ls = new long[25];
//        for (int i = 1; i < ls.length; i++){
//            ls[i] = solve(i);
//            if(ls[i]%MOD != answers[i]){
//                System.err.println(i);
//                throw new AssertionError();
//            }
//            System.out.println(i+" "+ls[i]);
//        }
//    }
//
//    private static long solve(int n) {
//        long[][] dp = new long[2][1<<n];
//        int cur=0,nxt=1;
//        dp[cur][0] = 1;
//        for (int i = 0; i < n; i++){
//            Arrays.fill(dp[nxt],0);
//            for (int j = 0; j < 1 << n; j++) if(dp[cur][j] > 0){
//                for (int k = 0; k < n; k++)if((j>>k&1)==0){
//                    int msk = (1<<k)-1;
//                    int cnt = Integer.bitCount(msk&j);
//                    if(MathUtils.isPrime(cnt+1) || cnt==0){
//                        dp[nxt][j|(1<<k)] += dp[cur][j];
//                    }
//                }
//            }
//            int tmp=cur;cur=nxt;nxt=tmp;
//        }
//        return dp[cur][(1<<n)-1];
//    }
}
