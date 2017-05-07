package on2017_04.on2017_04_29_RCC.TaskD;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;

import java.util.ArrayList;
import java.util.Arrays;

public class TaskD {

    long MOD = (long) (1e9 + 7);

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt();

        V[] vs = new V[n];
        Mint.set1e9_7();
        for (int i = 0; i < n; i++) {
            vs[i] = new V();
            vs[i].q1 = new long[k + 1];
            vs[i].q2 = new long[k + 1];
        }
        int root = -1;
        for (int i = 0; i < n; i++) {
            int p = in.nextInt() - 1;
            if (p >= 0) {
                vs[p].cs.add(vs[i]);
            } else {
                root = i;
            }
        }

        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int t = in.nextInt();
            int v = in.nextInt() - 1;
            if (t == 1) {
                for (int j = 0; j < k + 1; j++) {
                    vs[v].q1[j] += in.nextInt();
                    if (vs[v].q1[j] >= MOD) vs[v].q1[j] -= MOD;
                }
            }else {
                for (int j = 0; j < k + 1; j++) {
                    vs[v].q2[j] += in.nextInt();
                    if (vs[v].q2[j] >= MOD) vs[v].q2[j] -= MOD;
                }
            }
        }

        vs[root].dfs();

        for (int i = 0; i < n; i++) {
            out.printFormat("%d ", vs[i].calc());
        }
        out.println();
    }

    class V {
        ArrayList<V> cs = new ArrayList<>();
        long[] q1;
        long[] q2;

        int d = 1;

        public void dfs() {
            for(V c:cs) {
                for (int i = 0; i < q1.length; i++) {
                    c.q1[i] += q1[i];
                    if (c.q1[i] >= MOD) c.q1[i] -= MOD;
                }
                c.d = d + 1;
                c.dfs();

                for (int i = 0; i < q2.length; i++) {
                    q2[i] += c.q2[i];
                    if (q2[i] >= MOD) q2[i] -= MOD;
                }
            }
        }

        public long calc() {
            long res = 0;
            long k = 1;
            for (int i = 0; i < q1.length; i++) {
                res = (res + k * (q1[i] + q2[i])) % MOD;
                k = k * d % MOD;
            }
            return res;
        }
    }
}
