package on2015_09.on2015_09_02_TopCoder_Open_Round__1B.EagleInZoo;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EagleInZoo {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public double calc(int[] _parent, int K) {
        if (K == 1) return 1;
        int n = _parent.length + 1;
        int[] parent = new int[n];
        parent[0] = -1;
        for (int i = 0; i < n - 1; i++) {
            parent[i + 1] = _parent[i];
        }
        double res = 0;
        for (int i = 1; i < n; i++) {
            List<Integer> degrees = new ArrayList<Integer>();
            for (int j = parent[i]; j >= 0; j = parent[j]) {
                int d = 0;
                for (int k = 0; k < n; k++) {
                    if (parent[k] == j) d++;
                }
                degrees.add(d);
            }
            Collections.reverse(degrees);
            int m = degrees.size();
            double[] prob = new double[m];
            for (int j = 0; j < m; j++) {
                prob[j] = j == 0 ? 1.0 / degrees.get(j) : prob[j-1] / degrees.get(j);
            }
            double[][] dp = new double[K][m+1];
            dp[1][0] = 1;
            for(int j=1;j<K-1;j++)for(int k=0;k<m;k++){
                dp[j+1][k+1] += dp[j][k]*prob[k];
                dp[j+1][k] += dp[j][k]*(1-prob[k]);
            }
            res += dp[K-1][m-1] * prob[m-1];
        }
        return res;
    }
}
