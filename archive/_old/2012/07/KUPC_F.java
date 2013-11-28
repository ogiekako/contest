package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class KUPC_F {
    void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int Q = in.nextInt();
        long[] w = new long[N];
        int[] t = new int[N], x = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = in.nextLong();
            t[i] = in.nextInt();
            x[i] = in.nextInt();
        }
        int LIM = 3652425 + 1;
//        int  LIM = 10;
        long[] rep = new long[LIM + 20000];
        long[][] diff = new long[20000][3];// day
        long[] add = new long[3];
        add[0] = 1;
        int jobId = 0;
        for (int k = 0; k < LIM; k++) {
            while (jobId < N && w[jobId] <= rep[k]) {
                out.println(k);
                if (t[jobId] == 0) {
                    diff[mod(k)][0] += 1;
                    diff[mod(k + x[jobId])][0] -= 1;
                } else if (t[jobId] == 1) {
                    diff[mod(k)][0] += 1 - k;
                    diff[mod(k)][1] += 1;
                    diff[mod(k + x[jobId])][0] -= 1 - k;
                    diff[mod(k + x[jobId])][1] -= 1;
                } else {
                    diff[mod(k)][0] += 1L * (1 - k) * (1 - k);
                    diff[mod(k)][1] += 2 * (1 - k);
                    diff[mod(k)][2] += 1;
                    diff[mod(k + x[jobId])][0] -= 1L * (1 - k) * (1 - k);
                    diff[mod(k + x[jobId])][1] -= 2 * (1 - k);
                    diff[mod(k + x[jobId])][2] -= 1;
                }
                jobId++;
            }
            for (int i = 0; i < 3; i++) {
                add[i] += diff[mod(k)][i];
                diff[mod(k)][i] = 0;
            }
            rep[k + 1] = rep[k];
            for (int i = 0; i < 3; i++) {
                rep[k + 1] += pow(k, i) * add[i];
            }
        }
        while (jobId < N) {
            out.println("Many years later");
            jobId++;
        }
        for (int i = 0; i < Q; i++) {
            int j = in.nextInt();
            out.println(rep[j]);
        }
    }

    private int mod(int k) {
        return k % 20000;
    }

    private long pow(long k, int i) {
        return i == 0 ? 1 : i == 1 ? k : k * k;
    }
}
