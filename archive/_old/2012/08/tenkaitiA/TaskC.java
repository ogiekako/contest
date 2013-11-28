package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        ArrayList<Integer>[] es = new ArrayList[n];
        ArrayList<Integer>[] rs = new ArrayList[n];
        for (int i = 0; i < n; i++) es[i] = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) rs[i] = new ArrayList<Integer>();
        for (int i = 0; i < m; i++) {
            int from = in.nextInt() - 1, to = in.nextInt() - 1;
            es[from].add(to);
            rs[to].add(from);
        }
        String s = in.next();
        String[] ss = s.split("\"", -1);
        ArrayList<Boolean> list = new ArrayList<Boolean>();
        boolean started = false;
        boolean[][] dp = new boolean[2][n];
        for (String t : ss) {
            if (t.startsWith("g")) {
                started = true;
                if (t.endsWith("w")) {
                    list.add(true);
                    t = t.substring(0, t.length() - 1);
                } else {
                    list.add(false);
                }
                int id = Integer.valueOf(t.substring(5)) - 1;
                dp[0][id] = true;
            } else if (started) {
                list.add(t.startsWith("ww"));
            }
        }
        int cur = 0, nxt = 1;
        for (boolean b : list) {
            Arrays.fill(dp[nxt], false);
            if (b) {
                for (int i = 0; i < n; i++)
                    if (dp[cur][i]) {
                        for (int j : rs[i]) dp[nxt][j] = true;
                    }
            } else {
                int all = 0;
                for (boolean c : dp[cur]) if (c) all++;
                for (int i = 0; i < n; i++) {
                    int cnt = 0;
                    for (int j : es[i]) if (dp[cur][j]) cnt++;
                    if (cnt < all) dp[nxt][i] = true;
                }
            }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        int res = 0;
        for (boolean b : dp[cur]) if (b) res++;
        out.println(res);
    }
}
