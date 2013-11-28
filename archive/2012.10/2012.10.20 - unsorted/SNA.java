package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class SNA {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long A = in.nextLong(), B = in.nextLong();
        out.println(solve(B) - solve(A - 1));
    }

    private long solve(long A) {
        long lcm = 1;
        long res = 0;
        int[] lens = new int[50];
        lens[2] = 1;
        for (int i = 3; i < 50; i++) {
            for (int j = 2; ; j++)
                if (i % j != 0) {
                    lens[i] = lens[j] + 1;
                    break;
                }
        }
        for (int i = 2; lcm <= A; i++) {
            int j = i;
            int p;
            for (p = 2; ; p++) {
                if (j % p == 0) {
                    while (j % p == 0) j /= p;
                    break;
                }
            }
            if (j != 1) continue;
            long rest = A / lcm;
            long add = rest - rest / p;
            res += add * (lens[i] + 1);
            lcm *= p;
        }
        return res;
    }
}
