package on_2012.on2012_6_7.kingdomandtrees;


// Paste me into the FileEdit configuration dialog

public class KingdomAndTrees {
    int[] hs;
    public int minLevel(int[] heights) {
        hs = heights;
        int b = 1 << 30;
        int res = b - 1;
        for (; b > 0; b >>= 1) {
            if (can(res - b)) res -= b;
        }
        return res;
    }

    private boolean can(int a) {
        int cur = 0;
        for (int h : hs) {
            cur = Math.max(cur + 1, h - a);
            if (cur > h + a) return false;
        }
        return true;
    }
}
