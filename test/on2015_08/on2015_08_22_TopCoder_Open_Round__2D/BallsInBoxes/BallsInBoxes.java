package on2015_08.on2015_08_22_TopCoder_Open_Round__2D.BallsInBoxes;



import java.util.Arrays;

public class BallsInBoxes {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long maxTurns(long N, long K) {
        N -= K - 1;
        long res = 0;
        if (N > 2 * K) {
            long t = (N - 2 * K + K - 1) / K;
            res += t;
            N -= K * t;
        }
        while (N > 1) {
            res++;
            N -= N / 2;
        }
        return res;
    }
}
