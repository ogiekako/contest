package on2017_08.on2017_08_20_TopCoder_SRM__719.LongMansionDiv1;



import java.util.Arrays;

public class LongMansionDiv1 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long minimalTime(int[] t, int sX, int sY, int eX, int eY) {
        if (sY == eY) {
            long res = 0;
            for (int i = 0; i < t.length; i++) {
                if (sX <= i && i <= eX || eX <= i && i <= sX) res += t[i];
            }
            return res;
        }
        long res = Long.MAX_VALUE;
        for (int i = 0; i < t.length; i++) {
            long w = 0;
            for (int j = 0; j < t.length; j++) {
                if (sX <= j && j <= i || i <= j && j <= sX) {
                    w += t[j];
                }
                if (eX <= j && j <= i || i <= j && j <= eX) {
                    w += t[j];
                }
            }
            res = Math.min(res, w + (long) (Math.abs(eY - sY) - 1) * t[i]);
        }
        return res;
    }
}
