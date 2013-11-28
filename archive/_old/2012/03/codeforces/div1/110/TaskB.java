package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskB {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int n = in.nextInt(), m = n - in.nextInt();
        boolean[] bs = new boolean[n];
        int[] is = new int[n];
        int numPlus = 0;
        for (int i = 0; i < n; ++i) {
            String s = in.next();
            bs[i] = s.charAt(0) == '+';
            if (bs[i]) numPlus++;
            is[i] = Integer.valueOf(s.substring(1)) - 1;
        }
        int numCand = 0;
        boolean[] canCriminal = new boolean[n];
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i = 0; i < n; ++i) {
            if (bs[i]) a[is[i]]++;
            else b[is[i]]++;
        }
        for (int i = 0; i < n; ++i) {
            int liar = numPlus - a[i] + b[i];
            if (liar == m) {
                canCriminal[i] = true;
                numCand++;
            }
        }
        for (int i = 0; i < n; ++i) {
            if (canCriminal[is[i]] && numCand > 1) {
                out.println("Not defined");
            } else if (canCriminal[is[i]]) {
                out.println(bs[i] ? "Truth" : "Lie");
            } else {
                out.println(bs[i] ? "Lie" : "Truth");
            }
        }
    }
}
