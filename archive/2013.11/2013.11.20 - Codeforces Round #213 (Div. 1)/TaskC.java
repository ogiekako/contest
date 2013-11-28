package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskC {
    /*
    k <= 5000
    p1, p2, ... pm
     < 2k^2
     */
    int m;
    long[] base;
    long[][] pow;
    int[] counts;
    int ptr;
    long k;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long[] res = solve(in.nextInt());
        for (int i = 0; i < res.length; i++) {
            out.printFormat("%d%c", res[i], i == res.length - 1 ? '\n' : ' ');
        }
    }
    public long[] solve(int k) {
        for (int m = 2; m <= 5; m++) {
            long[] res = solve(k, m);
//            System.err.println("res = " + Arrays.toString(res));
            if (valid(res)) return res;
        }
        throw new AssertionError();
    }
    private boolean valid(long[] res) {
        int[] cnt = new int[12];
        for (int b : new int[]{2, 3, 5, 7, 11}) {
            for (long r : res) {
                if (r == 0) return false;
                if (r % b == 0) cnt[b]++;
            }
        }
        for (int c : cnt) {
            if (0 < c && c < k / 2) {
//                System.out.println("k");
//                System.out.println(Arrays.toString(res));
//                System.out.println(Arrays.toString(cnt));
                return false;
            }
        }
        return true;
    }

    public long[] solve(int k, int m) {
        this.k = k;
        this.m = m;
        base = new long[]{2, 3, 5, 7, 11};
        pow = new long[m][30];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < 30; j++) {
                pow[i][j] = j == 0 ? 1 : pow[i][j - 1] * base[i];
                if (pow[i][j] > 1e9) break;
            }
        int count = 0;
        counts = new int[m];
        long[] res = new long[k];
        ptr = 0;
        dfs(0, 1L, res);
        return res;
    }
    private void dfs(int i, long num, long[] res) {
        if (num > 1e9 || ptr >= k) return;
        if (i == m) {
            if (num <= 2 * k * k) {
                res[ptr++] = num;
            }
            return;
        }
        for (int j = 0; j < 30; j++) {
            if (pow[i][j] == 0) return;
            dfs(i + 1, num * pow[i][j], res);
        }
    }
    public static void main(String[] args) {
        for (int k = 5000; k >= 10; k--) {
//            System.err.println("k = " + k);
            long[] res = new TaskC().solve(k);
            int[] cnt = new int[12];
            for (int b : new int[]{2, 3, 5, 7, 11}) {
                for (long r : res) {
                    if(r==0) throw new AssertionError();
                    if (r % b == 0) cnt[b]++;
                }
            }
            for (int c : cnt) {
                if (0 < c && c < k / 2) {
                    System.out.println("k");
                    System.out.println(Arrays.toString(res));
                    System.out.println(Arrays.toString(cnt));
                    throw new AssertionError();
                }
            }
        }
    }
}
