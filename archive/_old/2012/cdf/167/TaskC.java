package tmp;

import net.ogiekako.algorithm.misc.game.Stones;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        long a = in.nextLong(), b = in.nextLong();
        if (a > b) {
            long tmp = a; a = b; b = tmp;
        }
        String res = solve(a, b) ? "First" : "Second";
        out.println(res);
    }

    private boolean solve(long a, long b) {
        if (a == 0) return false;
        if (!solve(b % a, a)) return true;
        return Stones.winPowerStoneGame(b / a, a);
    }

}
