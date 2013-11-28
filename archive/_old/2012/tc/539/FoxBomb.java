package tmp;

// Paste me into the FileEdit configuration dialog

public class FoxBomb {
    int h, w;
    char[][] grid;

    public int getMinimumCost(String[] _grid) {
        h = _grid.length; w = _grid[0].length();
        grid = new char[h][];
        for (int i = 0; i < h; i++) grid[i] = _grid[i].toCharArray();

        int total = 0;
        for (int i = 0; i < h; i++) for (int j = 0; j < w; j++) if (grid[i][j] == '.') total++;
        if (total == 1) return 1;

        for (int x = 0; x < h; x++)
            for (int y = 0; y < w; y++)
                if (grid[x][y] == '.') {
                    int degree = deg(x, y);
                    if (degree == 1) {
                        for (int d = 0; d < 4; d++) {
                            int nx = x + dx[d];
                            int ny = y + dy[d];
                            if (0 <= nx && nx < h && 0 <= ny && ny < w && grid[nx][ny] == '.') {
                                int res = dfs(nx, ny, d ^ 2);
//                        show();
                                if (grid[x][y] == '.') res++;
                                return res;
                            }
                        }
                    }
                }
        throw new RuntimeException();
    }

//    private void show() {
//        for(char[] c:grid){
//            System.err.println(Arrays.toString(c));
//        }
//        System.err.println("");
//    }

    private int dfs(int x, int y, int pDir) {
        int res = 0;
        boolean end = true;
        for (int d = 0; d < 4; d++)
            if (d != pDir) {
                int nx = x + dx[d];
                int ny = y + dy[d];
                if (0 <= nx && nx < h && 0 <= ny && ny < w && grid[nx][ny] != '#') {
                    end = false;
                    res += dfs(nx, ny, d ^ 2);
                }
            }
        if (!end) {
            boolean ex = false;
            for (int d = 0; d < 4; d++)
                if (d != pDir && d != (pDir ^ 2)) {
                    int nx = x + dx[d];
                    int ny = y + dy[d];
                    while (0 <= nx && nx < h && 0 <= ny && ny < w && grid[nx][ny] != '#') {
                        if (grid[nx][ny] == '.') ex = true;
                        nx += dx[d];
                        ny += dy[d];
                    }
                }
            if (ex) {
                res++;
                put(x, y);
            }
        }
        return res;
    }

    private void put(int x, int y) {
        grid[x][y] = 'R';
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            while (0 <= nx && nx < h && 0 <= ny && ny < w && grid[nx][ny] != '#') {
                grid[nx][ny] = 'R';
                nx += dx[d];
                ny += dy[d];
            }
        }
    }

    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};

    private int deg(int x, int y) {
        int res = 0;
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (0 <= nx && nx < h && 0 <= ny && ny < w && grid[nx][ny] != '#') {
                res++;
            }
        }
        return res;
    }


}

