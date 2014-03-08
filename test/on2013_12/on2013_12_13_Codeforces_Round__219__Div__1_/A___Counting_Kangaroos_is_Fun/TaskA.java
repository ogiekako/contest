package on2013_12.on2013_12_13_Codeforces_Round__219__Div__1_.A___Counting_Kangaroos_is_Fun;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] S = new int[n];
        for (int i = 0; i < n; i++) {
            S[i] = in.nextInt();
        }
        ArrayUtils.sort(S);
        int res = 0;
        for (int b = 1 << 30; b > 0; b >>= 1) {
            int mid = res + b;
            if (possible(S, mid)) res = mid;
        }
        out.println(n - res);
    }
    private boolean possible(int[] s, int m) {
        int n = s.length;
        for (int i = 0, j = m; i < m; i++,j++) {
            while (j < n && s[i] * 2 > s[j]) j++;
            if (j >= n) return false;
        }
        return true;
    }
}
