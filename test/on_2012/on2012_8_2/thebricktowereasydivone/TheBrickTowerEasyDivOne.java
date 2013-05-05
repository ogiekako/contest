package on_2012.on2012_8_2.thebricktowereasydivone;


// Paste me into the FileEdit configuration dialog

import java.util.HashSet;

public class TheBrickTowerEasyDivOne {
    public int find(int redCount, int redHeight, int blueCount, int blueHeight) {
//        if (redHeight == blueHeight) {
//            int mn = Math.min(redCount, blueCount);
//            int mx = Math.max(redCount, blueCount);
//            return mn + mn + (mx > mn ? 1 : 0);
//        } else {
//            int res = 0;
//            for (int d = -1; d <= 1; d++) {
//                int from = Math.max(0, Math.max((-d + 2) / 2, -d));
//                int to = Math.min(redCount, blueCount - d);
//                res += Math.max(0, to - from + 1);
//            }
//            return res;
//        }
        int min = Math.min(redCount, blueCount);
        int ans;
        if (redHeight == blueHeight) ans = min * 2;
        else ans = min * 3;
        if (redCount != blueCount) ans++;
        return ans;
    }

    public static void main(String[] args) {
        TheBrickTowerEasyDivOne instance = new TheBrickTowerEasyDivOne();
        for (int rC = 1; rC <= 10; rC++)
            for (int rH = 1; rH <= 10; rH++)
                for (int bC = 1; bC <= 10; bC++)
                    for (int bH = 1; bH <= 10; bH++) {
                        int res = instance.find(rC, rH, bC, bH);
                        int exp = solveStupid(rC, rH, bC, bH);
                        System.err.println(rC + " " + rH + " " + bC + " " + bH);
                        System.err.println(res + " " + exp);
                        if (res != exp) throw new AssertionError(rC + " " + rH + " " + bC + " " + bH);
                    }
    }

    private static int solveStupid(int rC, int rH, int bC, int bH) {
        HashSet<Integer> set = new HashSet<Integer>();
        for (int i = 0; i <= rC; i++)
            for (int j = 0; j <= bC; j++) {
                if (Math.abs(i - j) <= 1 && i + j > 0) {
                    set.add(i * rH + j * bH);
                }
            }
        return set.size();
    }

}

