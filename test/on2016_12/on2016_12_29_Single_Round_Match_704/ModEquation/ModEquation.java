package on2016_12.on2016_12_29_Single_Round_Match_704.ModEquation;



import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModEquation {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9+7);
    public int[] count(int n, int K, int[] query) {
        Mint.setMod(MOD);
        List<Integer> primes = new ArrayList<>();
        List<Integer> dups = new ArrayList<>();
        for(int i=2;i*i<=K;i++){
            int d=0;
            while(K%i==0){
                K/=i;d++;
            }
            if(d>0){
                primes.add(i);
                dups.add(d);
            }
        }
        if(K>1){
            primes.add(K);
            dups.add(1);
        }

        int m = primes.size();
        Mint[][][] dp = new Mint[m][][];
        for(int i=0;i<m;i++){
            int p = primes.get(i);
            int d = dups.get(i);
            dp[i] = new Mint[n+1][d + 1];
            ArrayUtils.fill(dp[i], Mint.ZERO);

            dp[i][0][0] = Mint.ONE;
            for(int j=0;j<n;j++){
                for(int e=0;e<=d;e++){
                    dp[i][j+1][d] = dp[i][j+1][d].add(dp[i][j][e]); // 0
                    for(int ne=0;ne<d;ne++){
                        long pat = (p-1) * MathUtils.power(p, d-1-ne) % MOD;
                        dp[i][j+1][Math.min(d, e+ne)] = dp[i][j+1][Math.min(d, e+ne)].add(dp[i][j][e].mul(pat));
                    }
                }
            }
        }

        int[] ans = new int[query.length];
        for(int i=0;i<query.length;i++){
            int v = query[i];
            Mint res = Mint.ONE;
            for(int j=0;j<m;j++){
                int p = primes.get(j);
                int d = dups.get(j);
                int e = 0;
                while(v%p==0 && e<d){
                    v/=p;
                    e++;
                }
                res = res.mul(dp[j][n][e]);
                if(e<d){
                    long pat = (p-1) * MathUtils.power(p, d-1-e) % MOD;
                    res = res.div(pat);
                }
            }
            ans[i] = res.get();
        }

        return ans;
    }
}
