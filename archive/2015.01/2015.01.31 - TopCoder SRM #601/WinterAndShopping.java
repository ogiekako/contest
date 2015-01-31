package src;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WinterAndShopping {

    int MAX = 510;
    int MOD = (int) (1e9 + 7);
    int[] first, R, G, B;
    long[][] memo;
    List<Integer>[] open;
    long[][] C;

    public int getNumber(int[] first, int[] red, int[] green, int[] blue) {
        this.first = first;
        R = red;
        G = green;
        B = blue;
        int n = first.length;
        open = new List[MAX];
        for (int i = 0; i < MAX; i++) {
            open[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = first[i]; j < first[i] + red[i] + green[i] + blue[i]; j++) {
                open[j].add(i);
            }
        }
        memo = new long[MAX][110 * 110];
        for (int i = 0; i < MAX; i++) {
            Arrays.fill(memo[i], -1);
        }
        C = MathUtils.combinationMod(400, MOD);
        return (int) f(0, 0, 0, 0, -1);
    }

    private long C(int r, int g, int b) {
        return C[r + g + b][r + g] * C[r + g][r] % MOD;
    }

    private long f(int pos, int r, int g, int b, int opening) {
        int key = r * 105 + g;
        if (r < 0 || g < 0 || b < 0) return 0;
        if (pos >= MAX) return 1;
        if (memo[pos][key] >= 0) return memo[pos][key];
        // empty がない場合と本質的に違わないはずなので、パラメータをそのまま渡すべき。
        if (open[pos].isEmpty()) return f(pos + 1, r, g, b, opening);
        //
        if (!open[pos].contains(opening)) {
            opening = open[pos].get(0);
            r = R[opening];
            g = G[opening];
            b = B[opening];
        }
        if (open[pos].size() == 2) {
            int nPos = pos;
            while (open[pos].equals(open[nPos])) nPos++;

            int opened = open[pos].get(0) != opening ? open[pos].get(0) : open[pos].get(1);
            int nOpening;
            int nR, nG, nB;
            long mul;

            //  opening ---     <- (r,g,b)
            //  opened  ------
            if (r + g + b == nPos - pos) {
                nOpening = opened;
                nR = R[opened] - r;
                nG = G[opened] - g;
                nB = B[opened] - b;
                mul = C(r,g,b);
            }
            // opening -----  <- (r,g,b)
            // opened  ---
            else {
                nOpening = opening;
                nR = r - R[opened];
                nG = g - G[opened];
                nB = b - B[opened];
                mul = C(R[opened], G[opened], B[opened]);
            }

            return memo[pos][key] = mul * f(nPos, nR, nG, nB, nOpening) % MOD;
        }

        long res = 0;
        res += f(pos + 1, r - 1, g, b, opening);
        res += f(pos + 1, r, g - 1, b, opening);
        res += f(pos + 1, r, g, b - 1, opening);
        return memo[pos][key] = res % MOD;
    }
}
