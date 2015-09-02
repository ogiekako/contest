package on2015_09.on2015_09_01_TopCoder_Open_Round__DIV.FoxIntegerLevelThree;



import java.util.Arrays;

public class FoxIntegerLevelThree {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long count(long min, long max) {
        return f(max + 1) - f(min);
    }

    // [1,n)
    private long f(long n) {
        long res = 0;
        for (int d = 1; d <= 9; d++) {
            res += Math.max(0, n - d * d + 9 * d - 1) / (9 * d);
        }
        int[] a = {0, 7, 6, 1 ,4};
        int[] b = {0, 8, 7, 2, 5};
        for (int d = 1; d <= 4; d++) {
            res -= Math.max(0, n - (9 * d * a[d] + d * d) + 9 * d * b[d] - 1) / (9 * d * b[d]);
        }
        return res;
    }
}
