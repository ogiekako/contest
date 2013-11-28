package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskA {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int res = 0;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = 0; j < 3; j++) sum += in.nextInt();
            res += sum / 2;
        }
        out.println(res);
    }
}
