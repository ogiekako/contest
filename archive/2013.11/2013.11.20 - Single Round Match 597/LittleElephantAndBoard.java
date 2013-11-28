package src;

import net.ogiekako.algorithm.math.CombinationSystem;
public class LittleElephantAndBoard {
    int MOD = (int) (1e9 + 7);
    CombinationSystem system = new CombinationSystem((int) 2e6 + 10, MOD);
    public int getNumber(int M, int R, int G, int B) {
        if (R == 0 || G == 0 || B == 0) return 0;
        R = M - R;
        G = M - G;
        B = M - B;
        if (R < 0 || G < 0 || B < 0) return 0;
        long res = 0;
        for (int k = 0; k <= M; k++) {
            debug("k", k);
            int r = B + G - 1 - k;
            long w1 = way(B, (k + 1) / 2) * way(G, (k + 2) / 2) % MOD;
            long w2 = way(G, (k + 1) / 2) * way(B, (k + 2) / 2) % MOD;
            long w = w1 + w2;
            debug(w1, w2, w);
            w %= MOD;
            int balls = R - r;
            if (balls < 0) continue;
            int place = B +  G + 1 - r;

            long c = place >= 0 && balls >= 0 ? system.choose(place, balls) : 0;
            debug("c", c);
            w *= c;
            debug("w", w);
            res = (res + w) % MOD;
        }
        return (int) (res * 2) % MOD;
    }
    static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }
    private long way(int ball, int box) {
        if (box == 0) return ball == 0 ? 1 : 0;
        if (ball == 0) return 0;
        return system.choose(ball - 1, box - 1);
    }
}
