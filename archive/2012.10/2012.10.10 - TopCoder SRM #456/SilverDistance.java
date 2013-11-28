package tmp;

public class SilverDistance {
    public int minSteps(int sx, int sy, int gx, int gy) {
        if (sx != 0 || sy != 0) return minSteps(0, 0, gx - sx, gy - sy);
        if ((sx + sy & 1) != (gx + gy & 1)) return minSteps(sx, sy + 1, gx, gy) + 1;
        return Math.max(Math.abs(gx), Math.abs(gy));
    }
}
