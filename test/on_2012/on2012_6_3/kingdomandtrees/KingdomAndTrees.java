package on_2012.on2012_6_3.kingdomandtrees;


// Paste me into the FileEdit configuration dialog

public class KingdomAndTrees {
    public int minLevel(int[] heights) {
        long left = -1, right = Integer.MAX_VALUE;
        do {
            long mid = (left + right) / 2;
            long cur = 0;
            boolean can = true;
            for (int height : heights) {
                long nxt = Math.max(cur + 1, height - mid);
                if (nxt > height + mid) {
                    can = false; break;
                }
                cur = nxt;
            }
            if (!can) {
                left = mid;
            } else {
                right = mid;
            }
        } while (right > left + 1);
        return (int) right;
    }


}

