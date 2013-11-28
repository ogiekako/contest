package tmp;

// Paste me into the FileEdit configuration dialog

public class AkariDaisukiDiv1 {
    private static final int MOD = (int) (1e9 + 7);

    int count(String X, String F) {
        int res = 0;
        for (int i = 0; i <= X.length() - F.length(); i++) if (X.substring(i, i + F.length()).equals(F)) res++;
        return res;
    }
    public int countF(String A, String B, String C, String S, String F, int k) {
        String X = S;
        int last = count(X, F);
        int a = -1, b = -1, c = -1;
        String pref = null, suff = null;
        for (int iter = 0; iter < k; iter++) {
            if (X.length() < F.length()) {
                X = A + X + B + X + C;
                last = count(X, F);
            } else {
                if (iter < F.length() + 2) {
                    if (pref == null) {
                        pref = X.substring(0, F.length() - 1);
                        suff = X.substring(X.length() - F.length() + 1);
                    }
                    String nPref = A + pref;
                    a = count(nPref, F);
                    b = count(suff + B + pref, F);
                    String nSuff = suff + C;
                    c = count(nSuff, F);
                    pref = nPref.substring(0, F.length() - 1);
                    suff = nSuff.substring(nSuff.length() - F.length() + 1);
                }
                last = last * 2 + a + b + c;
                while (last >= MOD) last -= MOD;
            }
        }
        return last;
    }
}

