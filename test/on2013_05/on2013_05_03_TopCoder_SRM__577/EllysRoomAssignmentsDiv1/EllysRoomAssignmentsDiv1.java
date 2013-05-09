package on2013_05.on2013_05_03_TopCoder_SRM__577.EllysRoomAssignmentsDiv1;


import net.ogiekako.algorithm.Builtin;
import net.ogiekako.algorithm.math.MathUtils;
public class EllysRoomAssignmentsDiv1 extends Builtin {
    public double getAverage(String[] ratings) {
        int[] rate = toInt(concat(ratings).split(" "));
        int elly = rate[0];
        sort(rate);
        reverse(rate);
        int N = rate.length;
        int R = (N + 19) / 20;
        long[][] C = MathUtils.combination(R + 1);
        int numPerson = 0;
        double res = 0;
        for (int i = 0; i < rate.length; i += R) {
            numPerson++;
            boolean hasElly = false;
            double sum = 0;
            int cnt = 0;
            for (int j = 0; j < R; j++) {
                if (i + j < rate.length) {
                    sum += rate[i + j];
                    if (rate[i + j] == elly) hasElly = true;
                    cnt++;
                }
            }
            if (hasElly) {
                res = (res * (numPerson - 1) + elly) / numPerson;
            } else if (cnt >= R) {
                res = (res * (numPerson - 1) + sum / cnt) / numPerson;
            } else {
                double p = 1.0 - (double) C[R - 1][cnt] / C[R][cnt];
                res = p * (res * (numPerson - 1) + sum / cnt) / numPerson + (1 - p) * res;
            }
        }
        return res;
    }
}
