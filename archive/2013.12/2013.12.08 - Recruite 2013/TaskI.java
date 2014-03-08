package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskI {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            String A = in.next();
            String B = in.next();
            String res = solve(A, B);
            out.println(res);
        }
    }
    String IMP = "impossible";
    private String solve(String A, String B) {
        if (A.length() != B.length()) return IMP;
        int[] to = new int[26];
        Arrays.fill(to, -1);
        for (int i = 0; i < A.length(); i++) {
            int a = A.charAt(i) - 'a';
            int b = B.charAt(i) - 'a';
            if (to[a] != -1 && to[a] != b) return IMP;
            to[a] = b;
        }
        boolean[][] graph = new boolean[26][26];
        for (int i = 0; i < to.length; i++) {
            if (to[i] != -1) graph[i][to[i]] = true;
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    graph[i][j] |= graph[i][k] && graph[k][j];
                }
            }
        }

        boolean zero = true;
        for (int i = 0; i < 26; i++) if (to[i] != -1 && to[i] != i) zero = false;
        if (zero) return "0";

        boolean can = false;
        for (int i = 0; i < 26; i++) {
            if (!graph[i][i]) can = true;
        }
        if (!can) return IMP;
        boolean[] loop = new boolean[26];
        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (graph[i][i] && !loop[i] && to[i] != i) {
                loop[i] = true;
                for (int j = 0; j < 26; j++) {
                    if (graph[i][j] && graph[j][i]) loop[j] = true;
                }
                boolean bad = true;

                for (int j = 0; j < 26; j++) {
                    if (graph[i][j] && graph[j][i]) {
                        for(int k=0;k<26;k++)if(!loop[k] && graph[k][j])bad= false;
                    }
                }
                if(bad)
                res++;
            }
        }
        for (int i = 0; i < 26; i++) {
            if (to[i] != i && to[i] != -1) res++;
        }

        return "" + res;
    }
}
