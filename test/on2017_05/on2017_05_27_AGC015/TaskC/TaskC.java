package on2017_05.on2017_05_27_AGC015.TaskC;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.ArrayUtils;

public class TaskC {

    int[][][] P; // dir
    int[][] R;
    int[][] S;
    int[] dx = {0, -1, 0, 1};
    int[] dy = {-1, 0, 1, 0};

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), M = in.nextInt(), Q = in.nextInt();
        S = new int[N][M];
        for (int i = 0; i < N; i++) {
            String s = in.next();
            for (int j = 0; j < M; j++) {
                S[i][j] = s.charAt(j) == '0' ? -1 : -2;
            }
        }
        P = new int[4][N + 2][M + 2];
        R = new int[N + 1][M + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                R[i + 1][j + 1] = R[i + 1][j] + R[i][j + 1] - R[i][j];
                if (S[i][j] == -2) {
                    dfs(i, j);
                    R[i + 1][j + 1]++;
                }
            }
        }
        for (int d = 0; d < 4; d++) {
            for (int i = 0; i <= N; i++) {
                for (int j = 0; j <= M; j++) {
                    if (d % 2 == 0) { // x
                        P[d][i + 1][j] += P[d][i][j];
                    } else {
                        P[d][i][j + 1] += P[d][i][j];
                    }
                }
            }
        }
        for (int i = 0; i < Q; i++) {
            int x1 = in.nextInt() - 1, y1 = in.nextInt() - 1, x2 = in.nextInt(), y2 = in.nextInt();
            int res = R[x2][y2] - R[x2][y1] - R[x1][y2] + R[x1][y1];
            res += P[0][x2][y1 + 1] - P[0][x1][y1 + 1];
            res += P[1][x1 + 1][y2] - P[1][x1 + 1][y1];
            res += P[2][x2][y2] - P[2][x1][y2];
            res += P[3][x2][y2] - P[3][x2][y1];
            out.println(res);
        }
    }

    private void dfs(int x, int y) {
        S[x][y] = 0;
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (0 <= nx && nx < S.length && 0 <= ny && ny < S[0].length && S[nx][ny] == -2) {
                P[d ^ 2][nx + 1][ny + 1]++;
                dfs(nx, ny);
            }
        }
    }
}
