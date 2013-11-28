package tmp;

import java.util.Arrays;

public class IndependentOfOR {
    public int maxSum(int[] A) {
        int m = 20;
        int res = 0;
        int[] is = new int[m];
        for (int i = 0; i < 1 << m; i++) {
            int point = 0;
            Arrays.fill(is, 0);
            for (int a : A) {
                int b = a & i;
                if (Integer.bitCount(b) == 1) {
                    int p = Integer.numberOfTrailingZeros(b);
                    is[p] = Math.max(is[p], a);
                }
            }
            for (int k : is) point += k;
            res = Math.max(res, point);
        }
        return res;
    }
}
