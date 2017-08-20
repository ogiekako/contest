package on2017_08.on2017_08_20_2017_TopCoder_Open_Algorithm.HarmoniousGarden;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HarmoniousGarden {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    // 8 4 4

    String pos = "Possible";
    String imp = "Impossible";

    class E {
        int k;
        int n;

        public E(int k, int n) {
            this.k = k;
            this.n = n;
        }
    }

    public String isPossible(int n, int k, int L) {
        if (n >= L + (k - 1) * (L - 1)) {
            return pos;
        }
        List<E> cands = new ArrayList<>();
        cands.add(new E(1, L));
        if (L % 2 == 0) {
            for (int m = 3; m <= 100; m++) {
                int kk = m * (m - 1) / 2;
                int nn = (L / 2 - 1) * m + 2;
                if (kk <= k && nn <= n)
                    cands.add(new E(kk, nn));
            }
        }
        int INF = (int) 1e9;
        int[] dp = new int[k + 1];// n
        Arrays.fill(dp, INF);
        for (int i = 0; i < k; i++) {
            for (E e : cands) {
                if (i + e.k <= k) {
                    dp[i + e.k] = Math.min(dp[i + e.k], i == 0 ? e.n : dp[i] + e.n - 1);
                }
            }
        }
        return dp[k] <= n ? pos : imp;
    }

    public static void main(String[] args) {

    }
}
