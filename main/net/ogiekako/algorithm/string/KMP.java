package net.ogiekako.algorithm.string;

public class KMP {
    /**
     * generate the failure link T, which is used by the KMP algorithm, for the given string s. <br></br>
     * <p/>
     * <h3>definition:<h3>
     * T[0] = -1 <br></br>
     * T[i] = max{ u : s[0,u) = s[i-u,i) } (0 < i <= |s|).
     */
    public static int[] generateFailureLink(CharSequence s) {
        int[] T = new int[s.length() + 1];
        T[0] = -1;
        for (int i = 1, j = -1; i <= s.length(); i++) {
            while (j >= 0 && s.charAt(i - 1) != s.charAt(j)) j = T[j];
            T[i] = ++j;
        }
        return T;
    }

    /*
     * text に対し,pattern がマッチする最小のindex を返す. ただし,linkは,前計算した failureTable.
     * text[i..] = pattern[i..].
     * 存在しない場合は, -1 を返す.
     */
    public static int match(CharSequence text, CharSequence pattern, int[] link) {
        for (int i = 0, j = 0; i < text.length(); i++) {
            while (j >= 0 && text.charAt(i) != pattern.charAt(j)) {
                j = link[j];
            }
            j++;
            if (j == pattern.length()) return i - pattern.length() + 1;
        }
        return -1;
    }
}
