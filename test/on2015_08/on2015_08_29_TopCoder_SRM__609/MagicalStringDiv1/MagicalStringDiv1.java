package on2015_08.on2015_08_29_TopCoder_SRM__609.MagicalStringDiv1;



import java.util.Arrays;

public class MagicalStringDiv1 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int getLongest(String S) {
        int res = 0;
        for (int i = 0; i < S.length() + 1; i++) {
            int a=0;
            for (int j = 0; j < i; j++) {
                if(S.charAt(j) == '>')a++;
            }
            int b=0;
            for (int j = i; j < S.length(); j++) {
                if(S.charAt(j) == '<')b++;
            }
            res = Math.max(res, Math.min(a,b));
        }
        return res * 2;
    }
}
