package tmp;

import java.util.Arrays;

public class HyperKnight {
    public long countCells(int a, int b, int h, int w, int k) {
        long[] cands = new long[]{0, a, b, h - a, h - b, w - a, w - b, h, w};
        Arrays.sort(cands);
        long res = 0;
        for (int i = 0; i < cands.length - 1; i++)
            for (int j = 0; j < cands.length - 1; j++) {
                long x1 = cands[i], x2 = cands[i + 1];
                long y1 = cands[j], y2 = cands[j + 1];
                if (x1 < 0 || y1 < 0 || x2 > h || y2 > w) continue;
                int cnt = 0;
                for (long dx = -1; dx <= 1; dx += 2)
                    for (int dy = -1; dy <= 1; dy += 2) {
                        long nx = x1 + dx * a, ny = y1 + dy * b;
                        if (0 <= nx && nx < h && 0 <= ny && ny < w) {
                            cnt++;
                        }
                    }
                for (long dx = -1; dx <= 1; dx += 2)
                    for (int dy = -1; dy <= 1; dy += 2) {
                        long nx = x1 + dx * b, ny = y1 + dy * a;
                        if (0 <= nx && nx < h && 0 <= ny && ny < w) {
                            cnt++;
                        }
                    }
                if (k == cnt) {
                    res += (x2 - x1) * (y2 - y1);
                }
            } return res;
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
