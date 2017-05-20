package on2017_05.on2017_05_15_TopCoder_Open_Round__1B.ExploringNumbers;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;
import java.util.HashSet;

public class ExploringNumbers {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int numberOfSteps(int n) {
        HashSet<Integer> seen = new HashSet<>();
        int cur = n;
        for (int i = 0; i < n; i++) {
            if (MathUtils.isPrime(cur)) {
                return i + 1;
            }
            if (seen.contains(cur)) return -1;
            seen.add(cur);
            int nxt = 0;
            while(cur > 0) {
                nxt+= (cur % 10) * (cur % 10);
                cur /= 10;
            }
            cur = nxt;
        }
        return -1;
    }
}
