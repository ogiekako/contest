package tmp;

public class CutSticks {
    public double maxKth(int[] sticks, int C, int K) {
        double left = 0, right = Long.MAX_VALUE;
        for (int i = 0; i < 100; i++) {
            double mid = (left + right) / 2;
            if (can(sticks, C, K, mid)) left = mid;
            else right = mid;
        }
        return left;
    }

    private boolean can(int[] sticks, int C, int K, double len) {
        long cut = 0;
        long count = 0;
        for (int stick : sticks) {
            if (stick > len) {
                int num = (int) Math.floor(stick / len);
                count += num;
                cut += num - 1;
            }
        }
        if (cut > C) count -= cut - C;
        return count >= K;
    }
}
