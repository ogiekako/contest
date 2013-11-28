package tmp;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Euler088 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        for (int n = 2; n <= 2 * N; n++) {
            dfs(n, n, 2, 0, 0);
        }
        HashSet<Integer> deja = new HashSet<Integer>();
        long res = 0;
        for (int i = 2; i <= N; i++) {
            int v = map.get(i);
            if (!deja.contains(v)) {
                res += v;
                deja.add(v);
            }
        }
        out.println(res);
    }

    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

    private void dfs(int N, int n, int bef, int sum, int k) {
        int nsum = sum + n;
        int nk = k + 1 + (N - nsum);
        if (!map.containsKey(nk) || map.get(nk) > N) map.put(nk, N);

        for (int nxt = bef; nxt * nxt <= n; nxt++)
            if (n % nxt == 0) {
                dfs(N, n / nxt, nxt, sum + nxt, k + 1);
            }
    }
}
