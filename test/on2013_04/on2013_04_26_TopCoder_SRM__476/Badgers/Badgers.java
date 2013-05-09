package on2013_04.on2013_04_26_TopCoder_SRM__476.Badgers;


import java.util.Arrays;

public class Badgers {
    public int feedMost(int[] hunger, int[] greed, int totalFood) {
        for (int N = hunger.length; ; N--) {
            int[] need = new int[hunger.length];
            for (int i = 0; i < need.length; i++) {
                need[i] = hunger[i] + greed[i] * (N - 1);
            }
            Arrays.sort(need);
            int sum = 0;
            for (int i = 0; i < N; i++) {
                sum += need[i];
            }
            if (sum <= totalFood) return N;
        }
    }
}
