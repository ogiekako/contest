package on2017_08.on2017_08_18_TopCoder_Open_Round__3B.BearFills;



import java.util.Arrays;

public class BearFills {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long countSets(int N, long W, long H) {
        if (W > H) {
            long tmp = W; W = H; H = tmp;
        }
        int D = 0;
        while(1L << D < H) D++;
        if (D > N) return 0;
        long res = ((1L << (N - D)) - 1) * (1L << D);
        if (H == (1L << D)) return res;
        res += countSets(D-1, H - (1L << D - 1), W);
        return res;
    }
}
