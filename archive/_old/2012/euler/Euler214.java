package tmp;

import net.ogiekako.algorithm.math.TotientFuction;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler214 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int mx = in.nextInt();
        int N = in.nextInt();
        int[] tot = TotientFuction.generateTotientTable(mx);
        long res = 0;
        for (int prime = 2; prime <= mx; prime++)
            if (tot[prime] == prime - 1) {
                int p = prime;
                int cnt = 1;
                while (p > 1) {
                    cnt++;
                    p = tot[p];
                }
                if (cnt == N) {
                    res += prime;
                }
            }
        out.println(res);
    }
}
