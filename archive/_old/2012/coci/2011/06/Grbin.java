package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Grbin {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int K = in.nextInt();
        int X = 5000;
        int Y = -4999;
        int N = 1;
        ArrayList<String> res = new ArrayList<String>();
        while (N < K) {
            N += res.size() + 1;
            res.add(String.format("%d %d %d %d", X, -5000, -5000, Y));
            X--;
            Y++;
        }
        out.println(res.size());
        for (String s : res) out.println(s);
    }
}
