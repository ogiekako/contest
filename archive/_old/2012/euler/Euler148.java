package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler148 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int prime = 7;
        int MX = 20;
        long[] ns = new long[MX];
        {
            int N = in.nextInt();
            for (int i = 0; i < MX; i++) {
                ns[i] = N % prime;
                N /= prime;
            }
        }
        long[] combPowers = new long[MX];
        for (int i = 0; i < MX; i++) combPowers[i] = i == 0 ? 1 : combPowers[i - 1] * prime * (prime + 1) / 2;
        long res = 0;
        long comb = 1;
        for (int i = MX - 1; i >= 0; i--) {
            res += comb * ns[i] * (ns[i] + 1) / 2 * combPowers[i];
            comb *= ns[i] + 1;
        }
        out.println(res);
    }
}
