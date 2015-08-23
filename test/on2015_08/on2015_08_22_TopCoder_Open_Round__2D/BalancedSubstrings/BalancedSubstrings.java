package on2015_08.on2015_08_22_TopCoder_Open_Round__2D.BalancedSubstrings;



import java.util.Arrays;

public class BalancedSubstrings {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int countSubstrings(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            int cnt = 0;
            int sum = 0;
            for (int j = i; j < s.length(); j++) {
                if (s.charAt(j) == '1') {
                    cnt++;
                    sum += j;
                }
                if (cnt == 0 || sum % cnt == 0) {
                    res++;
                }
            }
        }
        return res;
    }
}
