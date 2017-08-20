package on2017_08.on2017_08_20_TopCoder_Open_Round__3B.HarmoniousGarden;



import java.util.ArrayList;
import java.util.Arrays;

public class HarmoniousGarden {
    private static final int INF = (int) 1e9;

    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    class E {
        int n;
        int k;

        public E(int n, int k) {
            this.n = n;
            this.k = k;
        }
    }

    public String isPossible(int n, int k, int L) {
        ArrayList<E> cands = new ArrayList<>();
        cands.add(new E(L, 1));
        if (L % 2 == 0) {
            for (int m = 3;; m++) {
                int nk = m * (m - 1) / 2;
                if (nk > k) break;
                int nn = (L / 2 - 1) * m + 2;
                cands.add(new E(nn, nk));
            }
        }
        int[] dp = new int[k + 1];
        Arrays.fill(dp, INF);
        dp[0] = 1;
        for (int i = 0; i < k; i++) {
            for (E e : cands) {
                if (i + e.k <= k) {
                    dp[i + e.k] = Math.min(dp[i + e.k], dp[i] - 1 + e.n);
                }
            }
        }
        return dp[k] <= n ? "Possible" : "Impossible";
    }
}
