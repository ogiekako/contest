package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.AhoCorasick;
import net.ogiekako.algorithm.utils.interfaces.Function;

import java.io.PrintWriter;
import java.util.List;

/*
http://uva.onlinejudge.org/index.php?option=onlinejudge&page=show_problem&problem=1620
*/
public class UVa10679_ILoveStrings {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        int n = in.nextInt();
        String[] ss = in.nextStringArray(n);
        AhoCorasick aho = new AhoCorasick(ss);
        final boolean[] res = new boolean[ss.length];
        aho.match(s, new Function<List<Integer>, Void>() {
            public Void doIt(List<Integer> argument) {
                for (int i : argument) {
                    res[i] = true;
                }
                argument.clear();
                return null;
            }
        });
        for (boolean b : res) {
            out.println(b ? "y" : "n");
        }
    }
}
