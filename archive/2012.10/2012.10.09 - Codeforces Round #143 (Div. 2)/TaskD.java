package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskD {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int x = in.nextInt(), y = in.nextInt(), z = in.nextInt();
        int x1 = in.nextInt(), y1 = in.nextInt(), z1 = in.nextInt();
        int[] a = new int[6];
        for (int i = 0; i < 6; i++) a[i] = in.nextInt();
        int res = 0;
        if (y < 0) res += a[0];
        if (y > y1) res += a[1];
        if (z < 0) res += a[2];
        if (z > z1) res += a[3];
        if (x < 0) res += a[4];
        if (x > x1) res += a[5];
        out.println(res);
    }
}
