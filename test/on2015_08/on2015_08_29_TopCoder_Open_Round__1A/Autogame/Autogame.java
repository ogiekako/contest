package on2015_08.on2015_08_29_TopCoder_Open_Round__1A.Autogame;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;

public class Autogame {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MOD = (int) (1e9 + 7);

    public int wayscnt(int[] a, int K) {
        ArrayUtils.decreaseByOne(a);
        int[] to = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
            int step = K;
            to[i] = i;
            while (step > 0) {
                if (map.containsKey(to[i])) {
                    int p = map.get(to[i]) - step;
                    step %= p;
                }
                if (step <= 0) break;
                map.put(to[i], step);
                to[i] = a[to[i]];
                step--;
            }
        }

        long res = 1;
        for (int i = 0; i < a.length; i++) {
            int c = 0;
            for (int t : to) if (t == i) c++;
            res = res * (c + 1) % MOD;
        }
        return (int) res;
    }
}
