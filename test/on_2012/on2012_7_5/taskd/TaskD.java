package on_2012.on2012_7_5.taskd;


import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class TaskD {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    int n;
    char[][] map;
    int[][] sizes;
    Stack<Integer> ends = new Stack<Integer>();
    int endCount = 0;
    HashMap<Array, Double> memo;

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        n = in.nextInt();
        map = new char[n][n];
        sizes = new int[n][n];
        for (int i = 0; i < n; i++) map[i] = in.next().toCharArray();
        dfs(0, 0, -1);
        int[] is = new int[ends.size()];
        for (int i = 0; i < is.length; i++) is[i] = ends.pop();
        memo = new HashMap<Array, Double>();
        double res = endCount + calc(is);
        out.printf("%.9f\n", res);
    }

    private double calc(int[] is) {
        Array key = new Array(is.clone());
        Double value = memo.get(key);
        if (value != null) return value;
        double prob = 1;
        double res = 1;
        for (int i = 0; i < is.length; i++) {
            if (is[i] > 0) {
                if ((i & 1) == 0) {
                    prob /= 2;
                    is[i]--;
                    res += prob * calc(is);
                    is[i]++;
                } else {
                    is[i]--;
                    res += prob * calc(is);
                    is[i]++;
                    break;
                }
            }
        }
        memo.put(key, res);
        return res;
    }
    class Array {
        Array(int[] is) {
            this.is = is;
        }

        @Override
        public boolean equals(Object o) {
            Array array = (Array) o;

            return Arrays.equals(is, array.is);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(is);
        }

        int[] is;
    }

    private int dfs(int x, int y, int befDir) {
        sizes[x][y] = 1;
        if (map[x][y] == 'G') return 4;
        int goalDir = -1;
        int nextDir = -1;
        for (int d = 0; d < 4; d++)
            if ((d ^ 2) != befDir) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (0 <= nx && nx < n && 0 <= ny && ny < n && map[nx][ny] != '#') {
                    int tmp = dfs(nx, ny, d);
                    if (tmp != -1) {
                        nextDir = tmp;
                        goalDir = d;
                    }
                    sizes[x][y] += sizes[nx][ny];
                }
            }
        if (goalDir != -1 && nextDir != 4 && goalDir != nextDir) {
            {
                int nx = x + dx[goalDir] + dx[nextDir ^ 2];
                int ny = y + dy[goalDir] + dy[nextDir ^ 2];
                if (0 <= nx && nx < n && 0 <= ny && ny < n) {
                    if (sizes[nx][ny] > 0) {
                        ends.push(endCount);
                        ends.push(sizes[nx][ny]);
                        endCount = 0;
                    }
                }
            }
            {
                int nx = x + 2 * dx[goalDir];
                int ny = y + 2 * dy[goalDir];
                if (0 <= nx && nx < n && 0 <= ny && ny < n) {
                    endCount += sizes[nx][ny];
                }
            }

        }
        return goalDir;
    }
}
