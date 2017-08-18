package on2017_08.on2017_08_05_TopCoder_Open_Round__2.SpecialClique;



import java.util.Arrays;

public class SpecialClique {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String exist(long[] s) {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < 60; j++) {
                if (((s[i] >> j) & 1) == 0) {
                    long and = s[i];
                    for (int k = 0; k < s.length; k++) {
                        if ((s[k] >> j & 1) == 1 && (s[k] & s[i]) > 0) {
                            and &= s[k];
                        }
                    }
                    if (and == 0) {
                        return "Possible";
                    }
                }
            }
        }
        return "Impossible";
    }
}
