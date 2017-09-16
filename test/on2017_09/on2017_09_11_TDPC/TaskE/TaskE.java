package on2017_09.on2017_09_11_TDPC.TaskE;



import net.ogiekako.algorithm.Debug;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;

import java.math.BigInteger;
import java.util.Arrays;

public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int D = in.nextInt();
        String N = in.next();

        Mint.set1e9_7();
        Mint[][] dp = new Mint[2][D];
        Arrays.fill(dp[0], Mint.ZERO);
        int cur = 0, nxt = 1;
        int same = 0;
        for (char c : N.toCharArray()) {
            Arrays.fill(dp[nxt], Mint.ZERO);
            int i = c - '0';
            for (int j = 0; j < 10; j++) {
                if (j < i) {
                    dp[nxt][(same + j) % D] = dp[nxt][(same + j) % D].add(1);
                }
                for (int k = 0; k < D; k++) {
                    dp[nxt][(k + j) % D] = dp[nxt][(k + j) % D].add(dp[cur][k]);
                }
            }
            same = (same + i) % D;
            int tmp = cur;
            cur = nxt;
            nxt = tmp;
        }
        Mint res = dp[cur][0].minus(1);
        if (same == 0) res = res.add(1);
        out.println(res.get());
    }
}
