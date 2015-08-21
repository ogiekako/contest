package on2015_08.on2015_08_21_TopCoder_Open_Round__2B.FruitTrees;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class FruitTrees {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maxDist(int a1, int a2, int a3) {
        int res = 0;
        for (int x2 = 0; x2 < 2000; x2++)
            for (int x3 = 0; x3 < 2000; x3++) {
                res = Math.max(res,
                        Math.min(f(a1, a2, x2), Math.min(f(a1, a3, x3), f(a2, a3, x3 - x2)))
                );
            }
        return res;
    }

    private int f(int a1, int a2, int x2) {
        int g = MathUtils.gcd(a1, a2);
        x2 %= g;
        return Math.min(x2, g - x2);
    }
}
