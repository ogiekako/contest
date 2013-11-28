package tmp;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;

public class Euler346 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong();
        long res = 0;
        if (N > 1) res++;
        HashSet<Long> set = new HashSet<Long>();
        for (long b = 2; b * b + b + 1 < N; b++) {
            long pb = b * b;
            long tmp = b * b + b + 1;
            while (tmp < N) {
                set.add(tmp);
                pb *= b;
                tmp += pb;
            }
        }
        for (long l : set) {
            res += l;
        }
        out.println(res);
    }
}
