package on2017_08.on2017_08_05_TopCoder_Open_Round__3A.ParenthesesAndPermutation;



import java.util.Arrays;

public class ParenthesesAndPermutation {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String getSequence(int[] p) {
        int n = p.length;

        int[] pp = new int[n];
        for (int i = 0; i < n; i++) {
            pp[p[i]] = i;
        }
        p = pp;

        String[] dp = new String[n + 1];
        dp[0] = "";
        for (int i = 0; i < n; i++) {
            dp[0] += "?";
        }
        for (int i = 0; i < n; i++) {
            String[] nDp = new String[n + 1];
            for (int j = 0; j < n + 1; j++) if (dp[j] != null){
                char[] cs = dp[j].toCharArray();
                if (j > 0) {
                    cs[p[i]] = ')';
                    nDp[j - 1] = best(nDp[j - 1], new String(cs));
                }
                cs[p[i]] = '(';
                nDp[j + 1] = best(nDp[j + 1], new String(cs));
            }
            dp = nDp;
        }
        return valid(dp[0]) ? dp[0] : "Impossible";
    }

    private boolean valid(String s) {
        int t = 0;
        for(char c : s.toCharArray()) {
            if (c == '(') t++;
            else {
                t--;
                if (t < 0) return false;
            }
        }
        return t == 0;
    }

    private String best(String a, String b) {
        if (a == null) return b;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return a.charAt(i) == '(' ? a : b;
            }
        }
        return a;
    }
}
