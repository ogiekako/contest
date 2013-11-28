package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler301 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int res = 0;
        for (int i = 1; i <= 1 << n; i++) {
            if ((i ^ 2L * i ^ 3L * i) == 0) res++;
        }
        out.println(res);
    }
}
