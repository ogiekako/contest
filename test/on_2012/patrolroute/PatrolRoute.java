package on_2012.patrolroute;


// Paste me into the FileEdit configuration dialog

public class PatrolRoute {
    int MOD = (int) (1e9 + 7);
    public int countRoutes(int X, int Y, int minT, int maxT) {
        long res = 0;
        for (int x = 2; x < X; x++)
            for (int y = 2; y < Y; y++) {
                int time = (x + y) * 2;
                if (minT <= time && time <= maxT) {
                    res += 6L * (X - x) * (Y - y) % MOD * (x - 1) * (y - 1);
                    res %= MOD;
                }
            }
        return (int) res;
    }
}

