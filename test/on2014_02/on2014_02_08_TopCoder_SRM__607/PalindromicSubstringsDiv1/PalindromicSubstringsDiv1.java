package on2014_02.on2014_02_08_TopCoder_SRM__607.PalindromicSubstringsDiv1;



public class PalindromicSubstringsDiv1 {
    public double expectedPalindromes(String[] S1, String[] S2) {
        String S = cat(S1) + cat(S2);
        int n = S.length();
        double res = 0;
        for (int i = 0; i < n; i++) {
            double prob = 1;
            for (int j = 0; i - j >= 0 && i + j < n; j++) {
                if (j > 0)
                    prob *= calc(S.charAt(i - j), S.charAt(i + j));
                res += prob;
            }
        }
        for (int i = 1; i < n; i++) {
            double prob = 1;
            for (int j = 1; i - j >= 0 && i + j - 1 < n; j++) {
                prob *= calc(S.charAt(i - j), S.charAt(i + j - 1));
                res += prob;
            }
        }
        return res;
    }
    private double calc(char a, char b) {
        if (a != '?' && b != '?') return a == b ? 1 : 0;
        return 1.0 / 26;
    }
    private String cat(String[] A) {
        StringBuilder b = new StringBuilder();
        for (String a : A) b.append(a);
        return b.toString();
    }
}
