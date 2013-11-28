package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Random;

public class LJU {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        int[] is = new int[M];
        for (int i = 0; i < M; i++) is[i] = in.nextInt();
        out.println(solve(N, M, is));
    }
    private int solve(int N, int M, int[] is) {
        int left = 0, right = (int) (1e9 + 10);
        do {
            int mid = (left + right) / 2;
            long cur = 0;
            for (int i : is) {
                cur += (i + mid - 1) / mid;
                if (cur > N) break;
            }
            if (cur <= N) right = mid;
            else left = mid;
        } while (right - left > 1);
        return right;
    }

    public static void main(String[] args) {
        Random rnd = new Random(121208L);
        for (int oo = 0; oo < 100; oo++) {
            int N = rnd.nextInt((int) 1e9);
            int M = rnd.nextInt((int) 3e5);
            int[] is = new int[M];
            for (int i = 0; i < is.length; i++) is[i] = rnd.nextInt((int) 1e9);
            int res = new LJU().solve(N, M, is);
            System.err.println(res);
        }
    }
}
