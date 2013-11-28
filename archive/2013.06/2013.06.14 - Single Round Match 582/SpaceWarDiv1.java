package src;

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
public class SpaceWarDiv1 {
    public long minimalFatigue(int[] magicalGirlStrength, int[] enemyStrength, long[] enemyCount) {
        Arrays.sort(magicalGirlStrength);
        ArrayUtils.sortBy(enemyStrength, enemyCount);
        int n = magicalGirlStrength.length;
        int m = enemyStrength.length;
        if (magicalGirlStrength[n - 1] < enemyStrength[m - 1]) return -1;
        long left = 0, right = Long.MAX_VALUE / 2;
        do {
            long F = (left+right)/2;

            long[] count = enemyCount.clone();
            for(int i=n-1;i>=0;i--){
                long power = F;
                for(int j=m-1;j>=0;j--){
                    if(magicalGirlStrength[i] > enemyStrength[j]){
                        long def = Math.min(power, count[j]);
                        power -= def;
                        count[j] -= def;
                    }
                }
            }
            boolean ok = true;
            for(long c:count)if(c > 0)ok = false;
            if(ok)right = F;
            else left= F;
        } while (right - left > 1);
        return right;
    }
}
