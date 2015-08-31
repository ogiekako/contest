package on2015_08.on2015_08_29_TopCoder_Open_Round__1.EqualizeStrings;



import java.util.Arrays;

public class EqualizeStrings {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String getEq(String s, String t) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            res += f(s.charAt(i), t.charAt(i));
        }
        return res;
    }

    private char f(char a, char b) {
        char res = 'a';
        int min = Integer.MAX_VALUE;
        for (char c = 'a'; c <= 'z'; c++) {
            int v = Math.min((c - a + 26) % 26, (a - c + 26) % 26);
            v += Math.min((c - b + 26) % 26, (b - c + 26) % 26);
            if (v < min) {
                min = v;
                res = c;
            }
        }
        return res;
    }
}
