package tmp;

import net.ogiekako.algorithm.misc.dice.Dice;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class A {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int t = in.nextInt();
        while (t-- > 0) {
            Dice dice = new Dice(1, 3, 2, 5, 4, 6);
            int[] is = new int[6];
            for (int i = 0; i < 6; i++) is[i] = in.nextInt();
            Dice dice2 = new Dice(is[0], is[1], is[5], is[2], is[4], is[3]);
            out.println(dice.eq(dice2) ? "YES" : "NO");
        }
    }
}
