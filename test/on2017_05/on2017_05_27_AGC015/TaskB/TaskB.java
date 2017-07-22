package on2017_05.on2017_05_27_AGC015.TaskB;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        String s = in.next();
        long res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'U') res += s.length() - 1 + i;
            else res += s.length() - 1 + (s.length() - 1 - i);
        }
        out.println(res);
    }
}
