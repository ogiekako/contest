package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.ArrayList;
import java.util.Arrays;

public class CuttingBitString {
    int INF = 1 << 28;
    public int getmin(String S) {
        int[] dp = new int[S.length() + 1];
        Arrays.fill(dp, INF);
        ArrayList<String> list = new ArrayList<String>();
        for (long i = 1; ; i *= 5) {
            if (Long.toBinaryString(i).length() > S.length()) break;
            list.add(Long.toBinaryString(i));
        }
        dp[0] = 0;
        for (int i = 0; i < S.length(); i++) {
            for (String t : list) {
                if (S.substring(i).startsWith(t)) {
                    dp[i + t.length()] = Math.min(dp[i + t.length()], dp[i] + 1);
                }
            }
        }
        return dp[S.length()] < INF ? dp[S.length()] : -1;
    }


}

