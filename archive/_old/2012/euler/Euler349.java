package tmp;

import net.ogiekako.algorithm.misc.simulation.CellAutomaton;

import java.io.PrintWriter;
import java.util.Scanner;

public class Euler349 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long N = in.nextLong();
        long res = CellAutomaton.blackCellCountOfLangtonAnt(N);
        out.println(res);
    }

}
