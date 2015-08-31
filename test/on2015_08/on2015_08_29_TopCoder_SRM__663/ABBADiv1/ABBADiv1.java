package on2015_08.on2015_08_29_TopCoder_SRM__663.ABBADiv1;



import java.util.Arrays;
import java.util.HashMap;

public class ABBADiv1 {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    HashMap<String, String> memo = new HashMap<String, String>();
    String ok="Possible", ng = "Impossible";
    public String canObtain(String initial, String target) {
        if (initial.equals(target)) return ok;
        if (target.length() <= initial.length()) return ng;
        if (memo.containsKey(target)) return memo.get(target);
        String res = ng;
        if (target.charAt(target.length() - 1) == 'A') {
            String s = canObtain(initial, target.substring(0, target.length() - 1));
            if (s == ok) res = ok;
        }
        if (target.charAt(0) == 'B') {
            String s = canObtain(initial, new StringBuilder(target.substring(1)).reverse().toString());
            if (s == ok) res= ok;
        }
        memo.put(target, res);
        return res;
    }
}
