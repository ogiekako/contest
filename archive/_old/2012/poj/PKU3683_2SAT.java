package tmp;

import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.misc.TwoSAT;

import java.io.PrintWriter;
import java.util.ArrayList;

public class PKU3683_2SAT {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        Interval[][] is = new Interval[n][2];
        for (int i = 0; i < n; i++) {
            int s = timeToInt(in.next());
            int t = timeToInt(in.next());
            int d = in.nextInt();
            is[i][0] = new Interval(s, s + d);
            is[i][1] = new Interval(t - d, t);
        }
        ArrayList<int[]> clauseList = new ArrayList<int[]>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < i; j++) {
                boolean can = false;
                for (int k = 0; k < 2; k++)
                    for (int l = 0; l < 2; l++) {
                        if (is[i][k].intersection(is[j][l]).length() > 0) {
                            int x = i * 2 + k;
                            int y = j * 2 + l;
                            clauseList.add(new int[]{x ^ 1, y ^ 1});
                        } else can = true;
                    }
                if (!can) {
                    out.println("NO");
                    return;
                }
            }
        int[][] clauses = clauseList.toArray(new int[clauseList.size()][]);
        boolean[] assignment = TwoSAT.twoSAT(n, clauses);
        if (assignment == null) {
            out.println("NO");
        } else {
            out.println("YES");
            for (int i = 0; i < n; i++) {
                int j = assignment[i] ? 0 : 1;
                out.println(intToTime(is[i][j].left) + " " + intToTime(is[i][j].right));
            }
        }
    }

    private String intToTime(long i) {
        return String.format("%02d:%02d", i / 60, i % 60);
    }

    private int timeToInt(String str) {
        return Integer.valueOf(str.split(":")[0]) * 60 + Integer.valueOf(str.split(":")[1]);
    }
}
