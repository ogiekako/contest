package on2015_08.on2015_08_21_TopCoder_Open_Round__2B.SumAndProductPuzzle;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class SumAndProductPuzzle {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MX = 5000100;

    public long getSum(int A, int B) {
        A = Math.max(3, A);
        long res = 0;

        boolean[] isPrime = MathUtils.generatePrimaryTable(MX);
        boolean[] validP = new boolean[MX];
        Arrays.fill(validP, true);
        for (int x = 2; x * x < MX; x++) {
            for (int y = x; x * y < MX; y++) {
                int P = x * y;
                if (!isPrime[x + y - 1]) validP[P] = false;
            }
        }
        for (int P = A - 1; P <= B - 1; P++) if (!isPrime[P] && validP[P]) res += P + 1;
        return res;
    }
}
