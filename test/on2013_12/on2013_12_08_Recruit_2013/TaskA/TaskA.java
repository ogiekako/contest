package on2013_12.on2013_12_08_Recruit_2013.TaskA;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

// 部分和
public class TaskA {
    // taska.in -> TaskA.out
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        for (int o = 0; o < T; o++) {
            int[] res = new int[10];
            int n = in.nextInt();
            for (int i = 0; i < n; i++) {
                String s = in.next();
                res[s.charAt(0) - '0'] += Integer.valueOf(s);
            }
            Arrays.sort(res);
            out.println(res[9]);
        }
    }
}
