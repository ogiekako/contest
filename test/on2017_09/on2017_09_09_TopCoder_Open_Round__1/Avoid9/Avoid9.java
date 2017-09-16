package on2017_09.on2017_09_09_TopCoder_Open_Round__1.Avoid9;



import java.util.Arrays;

public class Avoid9 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int maxSizeOf9Free(int[] A) {
        int res = 0;
        int[] dist = new int[9];
        for (int i = 0; i < A.length; i++) {
            dist[A[i] % 9]++;
        }
        loop:
        for (int i = 0; i < 1 << 18; i++) {
            int[] use = new int[9];
            int val = 0;
            for (int j = 0; j < 9; j++) {
                use[j] = (i >> j * 2) & 3;
                if (use[j] == 3) use[j] = 200;
                val += Math.min(dist[j], use[j]);
            }
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 9; k++) {
                    int l = (18 - j - k) % 9;

                    use[j]--;
                    use[k]--;
                    use[l]--;
                    if (use[j] >= 0 && use[k] >= 0 && use[l] >= 0) continue loop;
                    use[j]++;
                    use[k]++;
                    use[l]++;
                }
            }
            res = Math.max(res, val);
        }
        return res >= 3 ? res : -1;
    }
}
