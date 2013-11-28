package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class ProblemC {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int A = in.nextInt(), B = in.nextInt();
        boolean[] visited = new boolean[B + 1];
        long res = 0;
        for (int i = A; i <= B; i++) {
            if (visited[i]) continue;
            int num = i;
            String str = num + "";
            int cnt = 0;
            for (int j = 0; j < str.length(); j++) {
                String nxtStr = str.substring(j) + str.substring(0, j);
                if (nxtStr.charAt(0) != '0') {
                    int nxt = Integer.valueOf(nxtStr);
                    if (A <= nxt && nxt <= B && !visited[nxt]) {
                        cnt++;
                        visited[nxt] = true;
                    }
                }
            }
            res += cnt * (cnt - 1) / 2;
        }
        out.printf("Case #%d: %d\n", testNumber, res);
    }
}
