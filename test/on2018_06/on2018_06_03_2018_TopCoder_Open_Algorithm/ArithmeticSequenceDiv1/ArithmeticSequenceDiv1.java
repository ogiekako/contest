package on2018_06.on2018_06_03_2018_TopCoder_Open_Algorithm.ArithmeticSequenceDiv1;



import java.util.Arrays;

public class ArithmeticSequenceDiv1 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int findMinCost(int[] x) {
        int res = Integer.MAX_VALUE;
        for (int d = -110; d <= 110; d++) {
            int minA = Integer.MAX_VALUE, maxA = Integer.MIN_VALUE;
            for (int i = 0; i < x.length; i++) {
                int a = x[i] - i * d;
                minA = Math.min(minA, a);
                maxA = Math.max(maxA, a);
            }
            for (int a = minA; a <= maxA; a++) {
                int val = 0;
                for (int i = 0; i < x.length; i++) {
                    int v = a + i * d;
                    val += Math.abs(v - x[i]);
                }
                res = Math.min(res, val);
            }
        }
        return res;
    }
}
