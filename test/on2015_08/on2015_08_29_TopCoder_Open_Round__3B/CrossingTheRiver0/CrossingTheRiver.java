package on2015_08.on2015_08_29_TopCoder_Open_Round__3B.CrossingTheRiver0;



import java.util.Arrays;

public class CrossingTheRiver {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    String okStr = "POSSIBLE", ng = "IMPOSSIBLE";

    public String isItEvenPossible(int waterWidth, int landWidth, int[] blockHeight, int depth) {
        int exactCount = 0;
        for (int x : blockHeight) if (x == depth) exactCount++;
        if (exactCount >= waterWidth) return okStr;

        for (int firstLand = 1; firstLand <= waterWidth + 1; firstLand++) {
            for (int lastLand = firstLand; lastLand <= firstLand + landWidth - 1; lastLand++) {
                int[] counts = new int[210];
                for (int x : blockHeight) counts[x]++;
                boolean ok = true;
                int remainingWater = waterWidth;
                int remainingLand = landWidth;
                for (int i = 1; i < firstLand; i++) {
                    counts[i + depth]--;
                    remainingWater--;
                    if (counts[i + depth] < 0) ok = false;
                }
                for (int i = firstLand; i <= lastLand; i++) {
                    counts[i]--;
                    remainingLand--;
                    if (counts[i] < 0) ok = false;
                }
                if (!ok) continue;

                int both = 0;
                for (int i = 0; i <= 100; i++) {
                    boolean water = 0 <= i - depth && i - depth <= firstLand;
                    boolean land = firstLand <= i && i <= lastLand;
                    if (water && land) both += counts[i];
                    if (!water && land) remainingLand -= counts[i];
                    if (water && !land) remainingWater -= counts[i];
                }
                remainingWater = Math.max(remainingWater, 0);
                remainingLand = Math.max(remainingLand, 0);
                if (remainingLand + remainingWater <= both) {
                    return okStr;
                }
            }
        }
        return ng;
    }
}
