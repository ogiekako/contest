package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.TotientFuction;

import java.io.PrintWriter;

public class Euler133 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] totTable = TotientFuction.generateTotientTable(N * 9);
        long res = 0;
        for (int prime = 2; prime < N; prime++)
            if (totTable[prime] == prime - 1) {
                int tot = totTable[prime * 9];
                boolean[] bs = new boolean[tot * 2];
                int pow = 1;
                for (; ; ) {
                    if (bs[pow]) {
                        res += prime;
                        break;
                    }
                    bs[pow] = true;
                    if (MathUtils.powMod(10, pow, prime * 9) == 1) {
                        break;
                    }
                    pow = pow * 10;
                    if (pow >= tot) pow %= tot;
                }
            }
        out.println(res);
    }
}
