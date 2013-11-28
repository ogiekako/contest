package tmp;

import java.util.Arrays;

public class NewArenaPassword {
    public int minChange(String oldPassword, int K) {
        if (K == oldPassword.length()) return 0;
        int res = 0;
        int n = oldPassword.length();
        for (int i = 0; i < n - K; i++) {
            int[] cnt = new int[256];
            for (int j = i; j < n; j += n - K) {
                cnt[oldPassword.charAt(j)]++;
                res++;
            }
            Arrays.sort(cnt);
            res -= cnt[255];
        }
        return res;
    }
}
