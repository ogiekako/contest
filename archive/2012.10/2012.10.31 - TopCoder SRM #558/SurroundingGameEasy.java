package tmp;

public class SurroundingGameEasy {
    int[] dx = {1, 0, -1, 0};
    int[] dy = {0, 1, 0, -1};
    public int score(String[] cost, String[] benefit, String[] stone) {
        int h = cost.length, w = cost[0].length();
        int res = 0;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                boolean sur = true;
                for (int d = 0; d < 4; d++) {
                    int nx = i + dx[d];
                    int ny = j + dy[d];
                    if (0 <= nx && nx < h && 0 <= ny && ny < w) {
                        if (stone[nx].charAt(ny) == '.') sur = false;
                    }
                }
                if (stone[i].charAt(j) == 'o') res -= cost[i].charAt(j) - '0';
                if (sur || stone[i].charAt(j) == 'o') res += benefit[i].charAt(j) - '0';
            }
        return res;
    }
}
