package tmp;

import java.util.Arrays;

public class TomekPhone {
    public int minKeystrokes(int[] occurences, int[] keySizes) {
        int res = 0;
        Arrays.sort(occurences);
        int p = occurences.length - 1;
        for (int i = 1; i <= 50; i++) {
            for (int k : keySizes)
                if (i <= k && p >= 0) {
                    res += i * occurences[p--];
                }
        }
        return p < 0 ? res : -1;
    }
}
