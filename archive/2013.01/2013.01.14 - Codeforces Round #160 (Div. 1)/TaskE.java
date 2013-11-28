package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class TaskE {
    // 2944730
    int[] primes;
    int[] candidates;
    int numCandidates;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = (int) 1e9;
        int L = in.nextInt(), R = in.nextInt();
        int p = in.nextInt();
        if (p < 3) {
            out.println(0); return;
        }
        primes = MathUtils.generatePrimes(p + 1);
        candidates = new int[2944730];
        Arrays.fill(candidates, Integer.MAX_VALUE);
        numCandidates = 0;
        gen(0, 1L, N);
        Arrays.sort(candidates);
        int[] dist = new int[numCandidates];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;// 1
        boolean[] answer = new boolean[numCandidates];
        answer[0] = true;
        for (int n = 2; n <= p; n++) {
            for (int i = 0, j = 0; i < dist.length; i++)
                if (dist[i] < p - n && candidates[i] <= N / n) {
                    int nxt = candidates[i] * n;
                    while (candidates[j] != nxt) j++;
                    if (dist[j] > dist[i] + 1) {
                        dist[j] = dist[i] + 1;
                        answer[j] = true;
                    }
                }
        }
        int res = 0;
        for (int i = 0; i < answer.length; i++) if (answer[i] && L <= candidates[i] && candidates[i] <= R) res++;
        out.println(res);
    }

    private void gen(int p, long number, int N) {
        if (p >= primes.length) {
            candidates[numCandidates++] = (int) number;
            return;
        }
        while (number <= N) {
            gen(p + 1, number, N);
            number *= primes[p];
        }
    }
}
