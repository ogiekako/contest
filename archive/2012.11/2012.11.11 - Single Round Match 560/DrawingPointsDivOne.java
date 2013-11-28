package tmp;

public class DrawingPointsDivOne {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    public int maxSteps(int[] xs, int[] ys) {
        for (int i = 0; i < xs.length; i++) {
            xs[i] *= 2; ys[i] *= 2;
        }
        int left = 0, right = 310;
        do {
            int mid = (left + right) / 2;
            if (can(mid, xs, ys)) left = mid;
            else right = mid;
        } while (right - left > 1);
        return left > 300 ? -1 : left;
    }

    private boolean can(int n, int[] xs, int[] ys) {
        for (int i = 0; i < xs.length; i++) {
            for (int d = 0; d < 4; d++) {
                int cx = xs[i] + dx[d] * 2;
                int cy = ys[i] + dy[d] * 2;
                boolean is = true;
                for (int j = 0; j < xs.length; j++) {
                    if (cx == xs[j] && cy == ys[j]) is = false;
                }
                for (int j = -n; j <= n; j += 2) {
                    int x = xs[i] + dx[d] * (n + 2) + (1 - Math.abs(dx[d])) * j;
                    int y = ys[i] + dy[d] * (n + 2) + (1 - Math.abs(dy[d])) * j;
                    if (!in(x, y, xs, ys, n)) is = false;
                }
                if (is) return false;
            }
        }
        return true;
    }

    private boolean in(int x, int y, int[] xs, int[] ys, int n) {
        for (int i = 0; i < xs.length; i++) {
            int dx = Math.abs(x - xs[i]);
            int dy = Math.abs(y - ys[i]);
            if (dx <= n && dy <= n) return true;
        }
        return false;
    }
}
