package on2017_09.on2017_09_09_Fun_SRM_2017_09_08.Avoid9;



import java.util.Arrays;

public class Avoid9 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maxSizeOf9Free(int[] A) {
        int[] dist = new int[9];
        for (int a : A) dist[a % 9]++;
        int res = dfs(dist, 0, 0, 0);
        return res >= 3 ? res : -1;
    }

    private int dfs(int[] cur, int i, int mask1, int mask2) {
        if (i >= 9) {
            return 0;
        }
        int res = dfs(cur, i + 1, mask1, mask2);
        if (cur[i] > 0) {
            cur[i]--;
            int nMask1 = mask1 | 1 << i;
            int nMask2 = mask2 | add(mask1, i);
            int nMask3 = add(mask2, i);
            if (nMask3 << 31 >= 0) {
                res = Math.max(res, dfs(cur, i, nMask1, nMask2) + 1);
            }
            cur[i]++;
        }
        return res;
    }

    private int add(int mask, int x) {
        int upper = (mask << x) & (1 << 9) - 1;
        int lower = (mask << x) >> 9;
        return upper | lower;
    }

    public static void main(String[] args) {
        
    }
}
