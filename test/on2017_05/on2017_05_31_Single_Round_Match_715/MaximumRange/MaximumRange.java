package on2017_05.on2017_05_31_Single_Round_Match_715.MaximumRange;



import java.util.Arrays;

public class MaximumRange {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int findMax(String s) {
        int a = 0, b = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '+') a++; else b++;
        }
        return Math.max(a,b);
    }
}
