package on2017_09.on2017_09_09_TopCoder_Open_Round__1.EllysChocolates;



import java.util.Arrays;

public class EllysChocolates {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int getCount(int P, int K, int N) {
        int res = N / P;
        int wrapper = N / P;
        while (wrapper >= K) {
            res += wrapper / K;
            wrapper -= wrapper / K * K - wrapper / K;
        }
        return res;
    }
}
