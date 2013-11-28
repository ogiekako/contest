package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskA {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int k = in.nextInt() - 1;
        int[] as = new int[n];
        int pos = 0;
        for (int i = 0; i < n; i++) {
            as[i] = in.nextInt();
            if (as[i] > 0) pos++;
        }
        if (as[k] == 0) {
            out.println(pos);
        } else {
            int i = k;
            while (i < n && as[i] == as[k]) i++;
            out.println(i);
        }
    }
}
