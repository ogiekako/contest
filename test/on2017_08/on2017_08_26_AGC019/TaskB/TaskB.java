package on2017_08.on2017_08_26_AGC019.TaskB;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String A = in.next();
        int[] count = new int[26];
        long res = 1;
        for (int i = 0; i < A.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (A.charAt(i) - 'a' != j) {
                    res += count[j];
                }
            }
            count[A.charAt(i) - 'a']++;
        }
        out.println(res);
    }
}
