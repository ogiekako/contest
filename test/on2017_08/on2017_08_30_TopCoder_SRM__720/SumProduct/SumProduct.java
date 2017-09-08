package on2017_08.on2017_08_30_TopCoder_SRM__720.SumProduct;



import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class SumProduct {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int findSum(int[] amount, int blank1, int blank2) {
        Mint.set1e9_7();
        Mint res = Mint.ZERO;

        Mint e1 = Mint.ZERO;
        Mint e2 = Mint.ZERO;
        for (int i = 0; i < blank1; i++) {
            e1 = e1.mul(10);
            e1 = e1.add(1);
        }
        for (int i = 0; i < blank2; i++) {
            e2 = e2.mul(10);
            e2 = e2.add(1);
        }
        Mint mul = e1.mul(e2);

        long[][] comb = MathUtils.genCombTableMod(500, 500, Mint.getMod());
        for (int i = 0; i < amount.length; i++) {
            for (int j = 0; j < amount.length; j++) {
                int[] m = amount.clone();
                m[i]--;
                m[j]--;
                int n = blank1 + blank2 - 2;
                Mint[][] ways = new Mint[2][n + 1];
                ArrayUtils.fill(ways, Mint.ZERO);
                int cur = 0, nxt = 1;
                ways[cur][0] = Mint.ONE;
                for (int k = 0; k < 10; k++) {
                    Arrays.fill(ways[nxt], Mint.ZERO);
                    for (int l = 0; l < n + 1; l++) {
                        for (int o = 0; l + o < n + 1 && o <= m[k]; o++) {
                            ways[nxt][l + o] = ways[nxt][l + o].add(ways[cur][l].mul(comb[n - l][o]));
                        }
                    }
                    int tmp = cur;
                    cur = nxt;
                    nxt = tmp;
                }

                res = res.add(mul.mul(i).mul(j).mul(ways[cur][n]));
            }
        }
        return res.get();
    }
}
