package on2017_08.on2017_08_05_TopCoder_Open_Round__3B.MaterialImplication;



import java.util.Arrays;

public class MaterialImplication {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    String F;

    public String construct(int n, int k) {
        if (1 << n - 1 > k) return "Impossible";
        F = "" + (char) ('A' + n - 1);
        return impl(falseCount(n - 1, k - (1 << n - 1)), F);
    }

    private String falseCount(int n, int k) {
        if (k < 0 || k > 1 << n) throw new RuntimeException();
        if (k == 0) return impl(F, F);
        if (k == (1 << n)) return F;
        if (k <= 1 << n - 1) {
            return impl(falseCount(n - 1, (1 << n - 1) - k), "" + (char) ('A' + n - 1));
        } else {
            return impl(falseCount(n, (1 << n) - k), F);
        }
    }

    String impl(String a, String b) {
        return "(" + a + "->" + b + ")";
    }
}