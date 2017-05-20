package on2017_05.on2017_05_21_2017_TopCoder_Open_Algorithm.FourLamps;



import java.util.ArrayList;
import java.util.Arrays;

public class FourLamps {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long count(String init, int k) {
        StringBuilder seq = new StringBuilder();
        for (int i = 0; i < init.length() - 1; i++) {
            if (init.charAt(i) != init.charAt(i + 1)) seq.append('A');
            else seq.append('B');
        }
        if (!seq.toString().contains("AA") && !seq.toString().contains("BB")) return 1;
        if (!seq.toString().contains("A") || !seq.toString().contains("B")) return 1;
        int[] bounds = new int[init.length()];
        int numBounds = 0;
        for (int i = 0; i < seq.length() - 1; i++) {
            if (seq.charAt(i) != seq.charAt(i + 1)) {
                bounds[numBounds++] = i + 1;
            }
        }
        bounds = Arrays.copyOf(bounds, numBounds);
        int maxMoves = Math.min(k, init.length() * init.length());
        long[][] ways = new long[init.length() - 1][maxMoves + 1];
        ways[0][0] = 1;
        for(int x : bounds) {
            long[][] nways = new long[init.length() - 1][maxMoves + 1];
            for (int old = 0; old < ways.length; old++) {
                for (int oldMoves = 0; oldMoves < ways[old].length; ++oldMoves) {
                    long w = ways[old][oldMoves];
                    if (w == 0) continue;
                    for (int nxt = old + 1; nxt < ways.length; ++nxt) {
                        int newMoves = Math.abs(nxt - x) + oldMoves;
                        if (newMoves <= maxMoves)
                            nways[nxt][newMoves] += w;
                    }
                }
            }
            ways = nways;
        }
        long res = 0;
        for (long[] a : ways) for (long b : a) res += b;
        res *= 2;
        if (k <= 1) res--;
        return res;
    }
}
