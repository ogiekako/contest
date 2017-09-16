package on2017_09.on2017_09_10_TopCoder_Open_Round__1.EllysCode01;



import java.util.Arrays;

public class EllysCode01 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long getOnes(long L, long R) {
        return count(R + 1) - count(L);
    }

    private long count(long r) { // [0, n)
        return r / 2 + (r % 2 == 1 ? Long.bitCount(r - 1) % 2 : 0);
    }
}
