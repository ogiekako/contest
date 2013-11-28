package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TopBatsmen {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int[] is = in.nextIntArray(11);
        int K = in.nextInt();
        int max = 0;
        for (int i = 0; i < 1 << 11; i++) {
            if (Integer.bitCount(i) == K) {
                int sum = 0;
                for (int j = 0; j < 11; j++)
                    if ((i >> j & 1) == 1) {
                        sum += is[j];
                    }
                max = Math.max(max, sum);
            }
        }
        int res = 0;
        for (int i = 0; i < 1 << 11; i++) {
            if (Integer.bitCount(i) == K) {
                int sum = 0;
                for (int j = 0; j < 11; j++)
                    if ((i >> j & 1) == 1) {
                        sum += is[j];
                    }
                if (max == sum) res++;
            }
        }
        out.println(res);
    }
}
