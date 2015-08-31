package on2015_08.on2015_08_29_TopCoder_Open_Round__3B.CrossingTheRiver;



import java.util.*;

public class CrossingTheRiver {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String isItEvenPossible(int waterWidth, int landWidth, int[] blockHeight, int depth) {
        int[] h = new int[blockHeight.length + landWidth];
        for (int i = 0; i < blockHeight.length; i++) {
            h[i] = blockHeight[i];
        }
        Arrays.sort(h);
        for (int h1 = 0; h1 <= 1; h1++) {
            for (int h2 = h1; h2 <= 100 && h2 < h1 + waterWidth; h2++) {
                for (int h3 = h2; h3 <= h2 + 1; h3++) {
                    loop:
                    for (int h4 = h3; h4 <= 100 && h4 < h3 + landWidth; h4++) {
                        List<Integer> remaining = new ArrayList<Integer>();
                        for (int i : h) remaining.add(i);
                        int waterCount = 0;
                        for (int i = h1; i <= h2; i++) {
                            if (!remaining.contains(i + depth)) continue loop;
                            remaining.remove((Integer) (i + depth));
                            waterCount++;
                        }
                        int landCount = 0;
                        for (int i = h3; i <= h4; i++) {
                            if (!remaining.contains(i)) continue loop;
                            remaining.remove((Integer) i);
                            landCount++;
                        }
                        int both = 0;
                        for(int i:remaining) {
                            boolean water = h1 <= i - depth && i - depth <= h2;
                            boolean land = h3 <= i && i <= h4;
                            if (water && land) both++;
                            if (water && !land) waterCount++;
                            if (land && !water) landCount++;
                        }
                        waterCount = Math.min(waterCount, waterWidth);
                        landCount = Math.min(landCount, landWidth);

                        if(waterCount + landCount + both >= landWidth + waterWidth) {
                            return "POSSIBLE";
                        }
                    }
                }
            }
        }
        return "IMPOSSIBLE";
    }
}
