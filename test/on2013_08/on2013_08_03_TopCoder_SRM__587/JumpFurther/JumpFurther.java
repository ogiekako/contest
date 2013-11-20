package on2013_08.on2013_08_03_TopCoder_SRM__587.JumpFurther;


public class JumpFurther {
    public int furthest(int N, int badStep) {
        int res = 0;
        for (int i = 1; i <= N; i++) {
            res += i;
            if (res == badStep) res--;
        }
        return res;
    }
}
