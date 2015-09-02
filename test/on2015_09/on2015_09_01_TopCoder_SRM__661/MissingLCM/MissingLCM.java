package on2015_09.on2015_09_01_TopCoder_SRM__661.MissingLCM;



import net.ogiekako.algorithm.math.MathUtils;

import java.util.Arrays;

public class MissingLCM {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int MX = 3000000;
    public int getMin(int N) {
        int[] divisors = MathUtils.generateDivisorTable(MX);
        int[] counts = new int[MX];
        int num = 0;
        for (int i = 1; i <= N; i++) {
            for(int j=i;j>1;) {
                int p = divisors[j];
                int c = 0;
                while(j%p==0){
                    j/=p;
                    c++;
                }
                if (counts[p] == 0) num++;
                counts[p] = Math.max(counts[p], c);
            }
        }
        for (int i = N + 1;; i++) {
            for(int j=i;j>1;) {
                int p = divisors[j];
                int c = 0;
                while(j%p==0){
                    j/=p;
                    c++;
                }
                if (counts[p] > 0 && counts[p] <= c) {
                    num--;
                    counts[p] = 0;
                }
            }
            if (num <= 0) return i;
        }
    }
}
