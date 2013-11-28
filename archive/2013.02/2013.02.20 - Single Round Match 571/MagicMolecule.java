package tmp;

public class MagicMolecule {
    int n;
    boolean[][] graph;
    int[] power;
    public int maxMagicPower(int[] magicPower, String[] magicBond) {
        n = magicPower.length;
        graph = new boolean[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = magicBond[i].charAt(j) == 'Y';
        power = magicPower;
        long canUse = (1L << n) - 1;
        int res = calc(canUse, 0);
        return res < 0 ? -1 : res;
    }

    private int calc(long canUse, int used) {
        int m = Long.bitCount(canUse) + used;
        if (3 * m < 2 * n) return Integer.MIN_VALUE;
        boolean perfect = true;
        for (int i = 0; i < n; i++)
            if ((canUse >> i & 1) == 1)
                for (int j = 0; j < i; j++) if ((canUse >> j & 1) == 1 && !graph[i][j]) perfect = false;
        if (perfect) {
            int res = 0;
            for (int i = 0; i < n; i++) if ((canUse >> i & 1) == 1) res += power[i];
            return res;
        }
        for (int i = 0; i < n; i++)
            if ((canUse >> i & 1) == 1) {
                long connected = 0;
                for (int j = 0; j < n; j++)
                    if (i != j && (canUse >> j & 1) == 1 && graph[i][j]) {
                        connected |= 1L << j;
                    }
                int res = calc(connected, used + 1) + power[i];
                if ((connected | 1L << i) == canUse) return res;
                res = Math.max(res, calc(canUse & ~(1L << i), used));
                return res;
            }
        throw new AssertionError();
    }

    public static void main(String[] args) {
        int n = 50;

    }
}
