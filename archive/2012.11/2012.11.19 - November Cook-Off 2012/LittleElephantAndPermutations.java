package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class LittleElephantAndPermutations {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int[] is = in.nextIntArray(n);
        for (int i = 0; i < n; i++)
            for (int j = i + 2; j < n; j++)
                if (is[i] > is[j]) {
                    out.println("NO"); return;
                }
        out.println("YES");
    }
}
