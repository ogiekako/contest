package on2017_07.on2017_07_23_2017_TopCoder_Open_Algorithm.CanidsSeesaw;



import java.util.Arrays;

public class CanidsSeesaw {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] construct(int[] wolf, int[] fox, int k) {
        int n = fox.length;
        long[] f = new long[n];
        for (int i = 0; i < n; i++) {
            f[i] = ((long)fox[i] << 32) | i;
        }
        Arrays.sort(f);
        for(;;) {
            int count = 0;
            int W = 0;
            int F = 0;
            for (int i = 0; i < fox.length; i++) {
                W += wolf[i];
                F += (int) (f[i] >>> 32);
                if (F > W) {
                    count++;
                }
            }
            if (count == k) {
                int[] id = new int[n];
                for (int i=0;i<n;i++) {
                    id[i] = (int) (f[i] & 63);
                }
                return id;
            }
            if (count > k) return new int[0];

            boolean ok = false;
            for (int i = 0; i < fox.length - 1; i++) {
                if (f[i] < f[i + 1]) {
                    long tmp = f[i];
                    f[i] = f[i + 1];
                    f[i + 1] = tmp;
                    ok = true;
                    break;
                }
            }
            if (!ok) {
                return new int[0];
            }
        }
    }
}
