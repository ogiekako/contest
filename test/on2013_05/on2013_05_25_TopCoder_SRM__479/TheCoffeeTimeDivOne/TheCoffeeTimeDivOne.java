package on2013_05.on2013_05_25_TopCoder_SRM__479.TheCoffeeTimeDivOne;


public class TheCoffeeTimeDivOne {
    public long find(int n, int[] tea) {
        long res = 0;
        res += 4L * n;
        int c = -1, t = -1;
        int ic = 6, it = 6;
        boolean[] isT = new boolean[n];
        for (int te : tea) isT[te - 1] = true;
        for (int i = n - 1; i >= 0; i--) {
            if (isT[i]) {
                it++;
                if (it == 7) {
                    it = 0;
                    if (t >= 0) {
                        res += (t + 1) * 2L;
                        res += 47;
                    }
                    t = i;
                }
            } else {
                ic++;
                if (ic == 7) {
                    ic = 0;
                    if (c >= 0) {
                        res += (c + 1) * 2L;
                        res += 47;
                    }
                    c = i;
                }
            }
        }
        if (t >= 0) {
            res += (t + 1) * 2L;
            res += 47;
        }
        if (c >= 0) {
            res += (c + 1) * 2L;
            res += 47;
        }
        return res;
    }
}
