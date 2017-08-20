package on2017_08.on2017_08_20_2017_TCC_India_Online_DIV_1.ILike5;



import java.util.Arrays;

public class ILike5 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int transformTheSequence(int[] X) {
        boolean five = false;
        int res = 0;
        for (int x : X) {
            if (x % 2 == 0) res++;
            if (x % 10 == 5) five = true;
        }
        if (res == 0) {
            return five ? 0 : 1;
        }
        return res;
    }
}
