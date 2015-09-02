package on2015_09.on2015_09_01_TopCoder_Open_Round__2A.ModModMod;



import java.util.Arrays;

public class ModModMod {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long findSum(int[] m, int R) {
        R++;
        long[] cnts = new long[R];
        Arrays.fill(cnts, 1);
        int tail = R;
        for (int n : m) {
            if (n >= tail) continue;
            for (int i=n;i<tail;i++){
                cnts[i%n] += cnts[i];
            }
            tail = n;
        }
        long res = 0;
        for (int i = 0; i < tail; i++) {
            res += i * cnts[i];
        }
        return res;
    }
}
