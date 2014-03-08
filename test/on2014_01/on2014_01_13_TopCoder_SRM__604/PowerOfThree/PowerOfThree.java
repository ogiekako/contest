package on2014_01.on2014_01_13_TopCoder_SRM__604.PowerOfThree;



public class PowerOfThree {
    public String ableToGet(int x, int y) {
        return possible(x, y) ? "Possible" : "Impossible";
    }
    private boolean possible(int x, int y) {
        if (x == 0 && y == 0) return true;
        if (x < 0) x = -x;
        if (y < 0) y = -y;
        int xi = x % 3;
        int yi = y % 3;
        if (xi > 0 && yi > 0) return false;
        if (xi == 0 && yi == 0) return false;
        if (xi == 2) return possible(x / 3 + 1, y / 3);
        if (yi == 2) return possible(x / 3, y / 3 + 1);
        return possible(x / 3, y / 3);
    }
}
