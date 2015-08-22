package on2015_08.on2015_08_21_TopCoder_Open_Round__2C.LongSeat;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.BellmanFord;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LongSeat {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    int L;
    int n;
    int[] weight;
    boolean[] canSit, canStand;
    // From left side.
    LinkedList<Integer> sitting;

    public String[] canSit(int L, int[] width) {
        n = width.length;
        this.L = L;
        this.weight = width;
        canSit = new boolean[n];
        canStand = new boolean[n];
        sitting = new LinkedList<Integer>();
        dfs();
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            if (!canSit[i]) res[i] = "Stand";
            else if (!canStand[i]) res[i] = "Sit";
            else res[i] = "Unsure";
        }
        return res;
    }

    private void dfs() {
        if (possible()) {
            for (int i = 0; i < n; i++) {
                if (sitting.contains(i)) canSit[i] = true;
                else canStand[i] = true;
            }
        }
        for (int i = 0; i < n; i++) {
            if (sitting.contains(i)) continue;
            sitting.push(i);
            dfs();
            sitting.pop();
        }
    }

    double EPS = 1e-3;

    private boolean possible() {
        // x - y <= a   --->   y --a--> x
        Graph G = new Graph(n + 1);
        int s = n;
        // Sit
        for (int i = 0; i < sitting.size(); i++) {
            int x = sitting.get(i);
            // 0 <= x  <=>  s - x <= 0
            if (i == 0) G.addWeighted(x, s, 0);
            else {
                int y = sitting.get(i - 1);
                // y + w <= x  <=>  y - x <= -w
                G.addWeighted(x, y, -weight[y]);
            }

            // x + w <= L  <=>  x - s <= L - w
            if (i == sitting.size() - 1) {
                G.addWeighted(s, x, L - weight[x]);
            }
        }
        // Stand
        for (int i = 0; i < n; i++) {
            if (sitting.contains(i)) continue;
            int w = weight[i];
            List<Integer> sub = new LinkedList<Integer>();
            for (int x : sitting) if (x < i) sub.add(x);

            if (sub.isEmpty() && w <= L) return false;
            for (int j = 0; j < sub.size(); j++) {
                int x = sub.get(j);
                if (j == 0) {
                    // x < w  <=>  x - s < w
                    G.addWeighted(s, x, w - EPS);
                } else {
                    int y = sub.get(j - 1);
                    // x - y - w[y] < w  <=>  x - y < w + w[y]
                    G.addWeighted(y, x, w + weight[y] - EPS);
                }

                if (j == sub.size() - 1) {
                    // L - x - w[x] < w  <=>  s - x < w + w[x] - L
                    G.addWeighted(x, s, w + weight[x] - L - EPS);
                }
            }
        }
        return !new BellmanFord(G).hasNegativeCycle();
    }

}
