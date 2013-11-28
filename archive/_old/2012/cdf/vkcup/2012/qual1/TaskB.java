package tmp;


import java.io.PrintWriter;
import java.util.Scanner;

public class TaskB {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int one = 0, two = 0, three = 0, four = 0;
        for (int i = 0; i < n; i++) {
            int s = in.nextInt();
            if (s == 1) one++;
            if (s == 2) two++;
            if (s == 3) three++;
            if (s == 4) four++;
        }
        int res = three;
        one -= three;
        if (one < 0) one = 0;
        int all = one + two * 2 + four * 4;
        res += (all + 3) / 4;
        out.println(res);
    }
}
