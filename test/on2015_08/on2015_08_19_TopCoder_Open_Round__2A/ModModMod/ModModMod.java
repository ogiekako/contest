package on2015_08.on2015_08_19_TopCoder_Open_Round__2A.ModModMod;



import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ModModMod {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public long findSum(int[] ms, int R) {
        R++;
        int[] sz = new int[R];
        Arrays.fill(sz, 1);
        for (int m : ms) {
            for (int i=m;i<R;i++){
                sz[i%m] += sz[i];
            }
            R=Math.min(m,R);
        }
        long res = 0;
        for(int i=0;i<R;i++)res += (long)i * sz[i];
        return res;
    }
}
