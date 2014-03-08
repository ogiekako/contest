package on2014_02.on2014_02_08_Single_Round_Match_608.MysticAndCandies;



import java.util.Arrays;
import java.util.Random;
public class MysticAndCandies {
    public int minBoxes(int C, int X, int[] low, int[] high) {
        Arrays.sort(low);
        Arrays.sort(high);
        int r1 = 0;
        int n = low.length;
        for (long S = 0; r1 < low.length; r1++) {
            S += low[n - 1 - r1];
            if (S >= X) break;
        }
        int r2 = 0;
        for (long S = 0; r2 < low.length; r2++) {
            S += high[r2];
            if (S > C - X) break;
        }
//        return r1 + 1;
                         debug(r1+1,n-r2);
        return Math.min(r1 + 1, n - r2);
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public static void main(String[] args) {
        Random rnd = new Random(12081L);
        int n = 10;
        for (int iter = 0; iter < 1000; iter++) {
            System.err.println("iter " + iter);
            int[] low = new int[n];
            int[] high = new int[n];
            for (int i = 0; i < n; i++) {
                low[i] = rnd.nextInt(10);
                high[i] = low[i] = rnd.nextInt(10);
            }
            int L = sum(low);
            int H = sum(high);
            int C = L + rnd.nextInt(H - L + 1);
            int X = rnd.nextInt(C + 1);
            int res = new MysticAndCandies().minBoxes(C, X, low, high);
            int exp = n;
            for (int mask = 0; mask < 1 << n; mask++) {
                boolean valid = false;
                int lS = 0;
                for(int i=0;i<n;i++)if(mask<<31-i<0) lS += low[i];
                if(lS >= X)valid = true;
                int hS = 0;
                for(int i=0;i<n;i++)if(mask<<31-i>=0) hS += high[i];
                if(X <= C - hS) valid = true;
                if(valid) exp = Math.min(exp, Integer.bitCount(mask));
            }
            if(res != exp){
                throw new AssertionError();
            }
        }
    }
    private static int sum(int[] low) {
        int res = 0;
        for (int l : low) {
            res += l;
        }
        return res;
    }
}
