package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class Task {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.nextLine();
        if (s.equals("The next line contains four real numbers x1, y1, x2, and y2. Compute the Euclidean distance between two points (x1, y1) and (x2, y2) rounded to 5 digits after the decimal point.")) {
            double x1 = in.nextDouble(), y1 = in.nextDouble(), x2 = in.nextDouble(), y2 = in.nextDouble();
            double res = Math.hypot(x1 - x2, y1 - y2);
            out.printf("%.5f\n", res);
        } else if (s.equals("Compute the sum of the two positive integers written in the next line. You can assume that the sum never exceeds 10^18.")) {
            long a = in.nextLong(), b = in.nextLong();
            out.println(a + b);
        } else if (s.equals("Each of the next 1,000,000 lines contain positive integer at most 10^6. Sort and output them in ascending order.")) {
            int n = 1000000;
            int[] is = new int[n];
            for (int i = 0; i < n; i++)
                is[i] = in.nextInt();
            Arrays.sort(is);
            for (int i : is) out.println(i);
        }
    }
}
