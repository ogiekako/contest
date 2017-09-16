package on2017_09.on2017_09_09_TopCoder_Open_Round__1.EllysCode01;



import java.util.Arrays;

public class EllysCode01 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long getOnes(long L, long R) {
        return f(L, R + 1, false, 62);
    }

    private long f(long l, long r, boolean flip, int lvl) {
        long size = 1L << lvl;
        while (l >= size) {
            l -= size;
            r -= size;
            flip = !flip;
        }
        if (r > size) {
            return f(l, size, flip, lvl) + f(0, r - size, !flip, lvl);
        }
        if (r - l == size) {
            return flip ? size - size / 2 : size / 2;
        }
        if (r == l) return 0;
        return f(l, r, flip, lvl - 1);
    }
}
