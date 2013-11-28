package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.misc.TwoSAT;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TaskH {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        for (int tc = 1; ; tc++) {
            int B = in.nextInt();
            int M = in.nextInt();
            if ((B | M) == 0) return;
            int[][] ids = new int[M][];
            for (int i = 0; i < M; i++) {
                int k = in.nextInt();
                ids[i] = new int[k];
                for (int j = 0; j < k; j++) {
                    int p = in.nextInt() - 1;
                    ids[i][j] = p * 2 + (in.nextChar() == 'y' ? 0 : 1);
                }
            }
            String res = solve(B, M, ids);
            out.printf("Case %d: %s\n", tc, res);
        }
    }

    private String solve(int B, int M, int[][] ids) {
        ArrayList<int[]> clauseList = new ArrayList<int[]>();
        for (int i = 0; i < M; i++) {
            int K = ids[i].length;
            if (K <= 2) {
                for (int id : ids[i]) clauseList.add(new int[]{id, id});
            } else {
                for (int j = 0; j < K; j++)
                    for (int k = 0; k < j; k++) {
                        clauseList.add(new int[]{ids[i][j], ids[i][k]});
                    }
            }
        }
        if (TwoSAT.twoSAT(B, clauseList.toArray(new int[clauseList.size()][])) == null) return "impossible";
        char[] res = new char[B];
        clauseList.add(null);
        int[][] clauses = clauseList.toArray(new int[clauseList.size()][]);
        int last = clauses.length - 1;
        for (int i = 0; i < B; i++) {
            res[i] = '?';
            clauses[last] = new int[]{i * 2, i * 2};
            if (TwoSAT.twoSAT(B, clauses) == null) {
                res[i] = 'n';
            } else {
                clauses[last] = new int[]{i * 2 + 1, i * 2 + 1};
                if (TwoSAT.twoSAT(B, clauses) == null) {
                    res[i] = 'y';
                }
            }
        }
        return new String(res);
    }
}
