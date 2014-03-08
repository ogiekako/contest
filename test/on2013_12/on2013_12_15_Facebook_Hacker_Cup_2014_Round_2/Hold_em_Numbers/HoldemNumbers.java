package on2013_12.on2013_12_15_Facebook_Hacker_Cup_2014_Round_2.Hold_em_Numbers;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class HoldemNumbers {
    long[][] C = MathUtils.combination(110);
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        System.err.println("#test " + testNumber);
        int N = in.nextInt(), H = in.nextInt();
        int[] xs = new int[H], ys = new int[H];
        for (int i = 0; i < H; i++) {
            xs[i] = in.nextInt();
            ys[i] = in.nextInt();
        }
        String res = solve(N, xs, ys);
        out.printFormat("Case #%d: %s\n", testNumber, res);
    }
    private String solve(int N, int[] xs, int[] ys) {
        long all = C[N - 2][6] * 15;
        long[] win = new long[xs.length];
        for (int i = 0; i < xs.length; i++) {
            win[i] = solve(N, xs[i], ys[i]);
//            debug(all, win[i]);
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < xs.length; i++) {
            if (win[i] * 4 > all) res.append("B");
            else res.append("F");
        }
        return res.toString();
    }
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
    private long solve(int N, int x, int y) {
        if (x > y) return solve(N, y, x);
        // x < y
        long[] dp = new long[4];
        dp[0] = 1;
        for (int i = N; i >= 1; i--) {
            if (i == x || i == y) continue;
            int way = x + y - i;
            if (i > y) way--;
            way = Math.min(i - 1, way);
            if (way >= y) way--;
            if (way >= x) way--;
            for (int j = 2; j >= 0; j--)
                if (dp[j] > 0) {
                    int nWay = way - j;
                    if (nWay > 0)
                        dp[j + 1] += dp[j] * nWay;
                }
        }
        return dp[3];
    }
    public static void main(String[] args) throws FileNotFoundException {
//        System.setOut(new PrintStream("tmp.out"));
        int M = 11;
        Random rnd = new Random(1240912894L);
        for (int t = 0; t < 100; t++) {
            System.err.println("t " + t);
            int x = rnd.nextInt(M) + 1;
            int y = rnd.nextInt(M) + 1;
            if (x == y) {
                t--; continue;
            }
            String s = new HoldemNumbers().solve(M, new int[]{x}, new int[]{y});
            String exp = new HoldemNumbers().naive(M, x, y);
            if(!s.equals(exp)) throw new AssertionError(x+" "+y);
        }

        for (int N = 100; N >= 100; N--) {
            int H = 10000;
            int[] xs = new int[H], ys = new int[H];
            for (int i = 0; i < H; i++) {
                xs[i] = rnd.nextInt(N) + 1;
                ys[i] = rnd.nextInt(N) + 1;
                if (xs[i] == ys[i]) {
                    i--;
                    continue;
                }

            }
            String res = new HoldemNumbers().solve(N, xs, ys);
            System.err.println("res " + res);
        }
    }
    private String naive(int N, int x, int y) {
        long all = 0;
        long win = 0;
        for (int a = 1; a <= N; a++)
            for (int b = 1; b <= N; b++)
                for (int c = 1; c <= N; c++)
                    for (int d = 1; d <= N; d++)
                        for (int e = 1; e <= N; e++)
                            for (int f = 1; f <= N; f++)
//                                for(int g=1;g<=N;g++)
//                                    for(int h=1;h<=N;h++) {
                            {
                                Set<Integer> set = new TreeSet<>();
                                set.add(a);
                                set.add(b);
                                set.add(c);
                                set.add(d);
                                set.add(e);
                                set.add(f);
                                set.add(x);set.add(y);
//                                        set.add(g);
//                                        set.add(h);
                                if (set.size() != 8) continue;
                                all++;
                                if(win(a,b,x,y))continue;
                                if(win(c,d,x,y))continue;
                                if(win(e,f,x,y))continue;
                                win++;
                            }
        debug("naive",win / 48, all / 48);
        if(win * 4 > all)return "B";
        else return "F";
    }
    private boolean win(int a, int b, int x, int y) {
        if(a+b > x+y)return true;
        if(a+b==x+y && Math.max(a,b) > Math.max(x,y))return true;
        return false;
    }
}
