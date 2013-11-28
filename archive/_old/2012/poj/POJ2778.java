package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.string.AhoCorasick;
import net.ogiekako.algorithm.utils.StringUtils;

import java.io.PrintWriter;

public class POJ2778 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int m = in.nextInt();
        long n = in.nextLong();
        String[] ss = in.nextStringArray(m);
        int res = solve(n, ss);
        out.println(res);
    }

    private int solve(long n, String[] ss) {
        AhoCorasick ac = new AhoCorasick(StringUtils.ACGT);
        ac.construct(ss);
        return ac.countUnmatchStringOf(n, 100000);
    }
}
