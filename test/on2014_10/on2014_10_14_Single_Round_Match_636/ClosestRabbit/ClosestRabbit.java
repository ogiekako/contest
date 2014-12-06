package on2014_10.on2014_10_14_Single_Round_Match_636.ClosestRabbit;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ClosestRabbit {

    public double getExpected(String[] board, int r) {
        double res = 0;
        int h = board.length, w = board[0].length();
        List<Integer> xs = new ArrayList<>(), ys = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (board[i].charAt(j) == '.') {
                    xs.add(i);
                    ys.add(j);
                }
            }
        }
        int m = xs.size();
        int[][] order = new int[m][m];
        for (int i = 0; i < m; i++) {
            E[] es = new E[m];
            for (int j = 0; j < m; j++) {
                es[j] = new E(dist2(xs.get(i) - xs.get(j), ys.get(i) - ys.get(j)), j, xs.get(j), ys.get(j));
            }
            Arrays.sort(es, new Comparator<E>() {
                @Override
                public int compare(E o1, E o2) {
                    if (o1.d != o2.d) return o1.d - o2.d;
                    if (o1.x != o2.x) return o1.x - o2.x;
                    return o1.y - o2.y;
                }
            });
            for (int j = 0; j < m; j++) {
                order[i][es[j].i] = j;
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < i; j++) {
                int near = 0;
                for (int k = 0; k < m; k++)
                    if (k != i && k != j) {
                        if (order[i][k] < order[i][j] || order[j][k] < order[j][i]) {
                            near++;
                        }
                    }
                if (near + r > m) continue;
                double value = (double) r * (r - 1) / m / (m - 1);
                for (int k = 0; k < near; k++) {
                    value *= (double) (m - r - k) / (m - 2 - k);
                }
                res += value;
            }
        }
        return res;
    }

    private int dist2(int x, int y) {
        return x * x + y * y;
    }

    class E {
        int d;
        int i;
        int x, y;

        E(int d, int i, int x, int y) {
            this.d = d;
            this.i = i;
            this.x = x;
            this.y = y;
        }
    }
}
