package on2017_09.on2017_09_09_Fun_SRM_2017_09_08.UnclearNotes;



import java.util.Arrays;

public class UnclearNotes {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String isMatch(String S, String T) {
        for (int i = 0; i < S.length(); i++) {
            if (!same(S.charAt(i), T.charAt(i))) return "Impossible";
        }
        return "Possible";
    }

    private boolean same(char a, char b) {
        if (a == b) return true;
        String A = "0ol1mn";
        String B = "o01lnm";
        for (int i = 0; i < A.length(); i++) {
            if (a == A.charAt(i) && b == B.charAt(i)) return true;
        }
        return false;
    }
}
