package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskD {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] v = new int[n];
        for (int i = 0; i < n; i++) v[i] = in.nextInt();
        int res = Integer.MIN_VALUE;
        for (int k = 3; k <= n; k++)
            if (n % k == 0) {
                int d = n / k;
                for (int i = 0; i < d; i++) {
                    int val = 0;
                    for (int j = 0; j < k; j++) {
                        val += v[i + j * d];
                    }
                    res = Math.max(res, val);
                }
            }
        out.println(res);
    }
}
