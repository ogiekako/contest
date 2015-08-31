package on2015_08.on2015_08_29_TopCoder_Open_Round__1A.EllysSortingTrimmer;



import java.util.Arrays;
import java.util.TreeSet;

public class EllysSortingTrimmer {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String getMin(String S, int L) {
        TreeSet<String> set = new TreeSet<String>();
        for(int i=S.length()-L;i>=0;i--){
            char[] cs = S.substring(S.length() - L).toCharArray();
            Arrays.sort(cs);
            S = S.substring(0, S.length() - L) + new String(cs);
            set.add(S);
            S = S.substring(0, S.length() - 1);
        }
        return set.first();
    }
}
