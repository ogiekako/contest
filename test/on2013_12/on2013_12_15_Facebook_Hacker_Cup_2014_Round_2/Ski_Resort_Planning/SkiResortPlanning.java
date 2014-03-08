package on2013_12.on2013_12_15_Facebook_Hacker_Cup_2014_Round_2.Ski_Resort_Planning;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SkiResortPlanning {
    static int MOD = 1000000007;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        System.err.println("test " + testNumber);
        int N = in.nextInt();
        int[] A = new int[N - 1];
        for (int i = 0; i < N - 1; i++) {
            A[i] = in.nextInt();
        }
        debug(A);
        long res = solve(A);
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }
    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }
    List<Integer>[] G;
    long[] dp;
    long[] size;
    long[] pow2;
    private long solve(int[] A) {
        int N = A.length + 1;
        //noinspection unchecked
        G = new List[N];
        for (int i = 0; i < N; i++) {
            G[i] = new ArrayList<>();
        }
        pow2 = new long[N + 1];
        for (int i = 0; i < N + 1; i++) {
            pow2[i] = i == 0 ? 1 : (pow2[i - 1] * 2) % MOD;
        }
        long res = 1;
        for (int i = 1; i < N; i++) {
            int p = A[i - 1];
            dp = new long[N];
            size = new long[N];
            Arrays.fill(dp, -1);
            Arrays.fill(size, -1);
//            debug(G);
            res = res * f(p) % MOD;
            G[p].add(i);
        }
        return res;
    }
    private long f(int v) {
        if (dp[v] != -1) return dp[v];
        dp[v] = pow2[(int) sz(v) - 1];
        if (G[v].size() == 0) return dp[v];
        long all = pow2[(int) (sz(v) - 1)];
        all = (all - 1 + MOD) % MOD;
        for (int u : G[v]) {
            all = (all - pow2[((int) sz(u))] + 1 + MOD) % MOD;
        }
        debug("v", v, all);
        dp[v] = (dp[v] + all) % MOD;
        return dp[v];
    }
    private long sz(int v) {
        if (size[v] != -1) return size[v];
        size[v] = 1;
        for (int u : G[v]) size[v] += sz(u);
        return size[v];
    }
    public static void main(String[] args) {
        int N = 6;

        Random rnd = new Random(1401294L);
        for(int t=0;t<50;t++){
            System.err.println("t = " + t);
            int[] A = new int[N];
            for (int i = 0; i < N-1; i++) {
                A[i] = rnd.nextInt(i+1);
            }
            long res = new SkiResortPlanning().solve(A);
            if(res >= MOD) throw new AssertionError();
            long exp = new SkiResortPlanning().naive(A);
            if(res != exp) throw new AssertionError();
            System.err.println(res + " " + exp);
        }


        N = 5000;
        for(int t=0;t<50;t++){
            System.err.println("t = " + t);
            int[] A = new int[N];
            for (int i = 0; i < N-1; i++) {
                A[i] = rnd.nextInt(i+1);
            }
            long res = new SkiResortPlanning().solve(A);
            if(res >= MOD) throw new AssertionError();
            System.err.println(res);
        }


    }

    int N;
    boolean[][] graph;
    long res;
    private long naive(int[] A) {
        N = A.length;
        graph = new boolean[N+1][N+1];
        res = 0;
        dfs(1,A);
        return res;
    }
    private void dfs(int i, int[] a) {
        if(i==N+1){
            boolean[][] nei = new boolean[N+1][N+1];
            for (int j = 0; j < nei.length; j++) {
                for (int k = 0; k < nei.length; k++) {
                    nei[j][k] = graph[j][k];
                }
            }
            for (int l = 0; l < nei.length; l++) {
                for (int j = 0; j < nei.length; j++) {
                    for (int k = 0; k < nei.length; k++) {
                        nei[j][k] |= nei[j][l] && nei[l][k];
                    }
                }
            }
            for(int j=1;j<N+1;j++){
                if(find(j,nei) != a[j-1])return;
            }
            res++;
            return;
        }
        for(int msk=0;msk<1<<i;msk++){
            for(int j=0;j<i;j++)graph[j][i] = msk<<31-j<0;
            dfs(i+1,a);
        }
    }
    private int find(int p, boolean[][] nei) {
        if(! nei[0][p])return -1;
        for(int i=p-1;i>=0;i--){
            boolean[][] nn = new boolean[N+1][N+1];
            for(int j=0;j<nei.length;j++)for(int k=0;k<nei.length;k++)if(j!=i && graph[j][k])nn[j][k] = true;

            for (int l = 0; l < nei.length; l++) {
                for (int j = 0; j < nei.length; j++) {
                    for (int k = 0; k < nei.length; k++) {
                        nn[j][k] |= nn[j][l] && nn[l][k];
                    }
                }
            }
            if(!nn[0][p])return i;
        }
        return -1;
    }
}
