package on2015_09.on2015_09_01_TopCoder_Open_Round__DIV.FuzzyLife;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class FuzzyLife {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int survivingCells(String[] grid) {
        int h = grid.length, w = grid[0].length();
        char[][] map = new char[h + 4][w + 4];
        ArrayUtils.fill(map, '0');
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                map[i + 2][j + 2] = grid[i].charAt(j);
            }
        }
        for (int i = 0; i < h + 4; i++) {
            for (int j = 0; j < w + 4; j++) {
                if (map[i][j] != '?') continue;
                map[i][j] = '0';
                int c = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        c += next(map, i + dx, j + dy);
                    }
                }
                map[i][j] = '1';
                int d = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        d += next(map, i + dx, j + dy);
                    }
                }
                if (c > d) map[i][j] = '0';
            }
        }

        int res = 0;
        for (int i = 1; i < h + 3; i++) {
            for (int j = 1; j < w + 3; j++) {
                res += next(map, i, j);
            }
        }

        return res;
    }

    private int next(char[][] map, int x, int y) {
        int c = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                c += map[x + dx][y + dy] - '0';
            }
        }
        return (map[x][y] == '0' && c == 3 || map[x][y] == '1' && 2 <= c && c <= 3) ? 1 : 0;
    }
}
