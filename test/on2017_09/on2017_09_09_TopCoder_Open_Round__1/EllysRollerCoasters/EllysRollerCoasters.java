package on2017_09.on2017_09_09_TopCoder_Open_Round__1.EllysRollerCoasters;



import net.ogiekako.algorithm.misc.TwoSAT;

import java.util.Arrays;

public class EllysRollerCoasters {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    String[] IMP = new String[0];

    public String[] getPlan(String[] field) {
        int U = 0, L = 1, D = 2, R = 3;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        int h = field.length, w = field[0].length();
        char[][] state = new char[h + 1][w + 1];

        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) {
                if (field[x].charAt(y) == '.') {
                    if (state[x][y] != 0) return IMP;
                } else if (field[x].charAt(y) == 'S') {
                    if (state[x][y] << 31 - U < 0) {
                        if (state[x][y] << 31 - L < 0) return IMP;
                        state[x][y] |= 1 << D;
                        state[x + 1][y] |= 1 << U;
                    } else if (state[x][y] << 31 - L < 0) {
                        if (state[x][y] << 31 - U < 0) return IMP;
                        state[x][y] |= 1 << R;
                        state[x][y + 1] |= 1 << L;
                    } else {
                        return IMP;
                    }
                } else {
                    for (int d = 0; d < 2; d++) {
                        if (state[x][y] << 31 - d >= 0) {
                            state[x][y] |= 1 << d + 2;
                            state[x + dx[d + 2]][y + dy[d + 2]] |= 1 << d;
                        }
                    }
                    int dir = "ULDR".indexOf(field[x].charAt(y));
                    if (state[x][y] << 31 - dir >= 0) {
                        return IMP;
                    }
                }
            }
        }
        for (int x = 0; x < h; x++) {
            if (state[x][w] > 0) return IMP;
        }
        for (int y = 0; y < w; y++) {
            if (state[h][w] > 0) return IMP;
        }

        String[] res = new String[h];
        for (int i = 0; i < h; i++) {
            res[i] = "";
            for (int j = 0; j < w; j++) {
                int s = state[i][j];
                res[i] += s << 31 - L < 0 && s << 31 - U < 0 || s << 31 - R < 0 && s << 31 - D < 0 ? '/'
                        : s << 31 - L < 0 && s << 31 - D < 0 || s << 31 - R < 0 && s << 31 - U < 0 ? '\\'
                        : s << 31 - L < 0 && s << 31 - R < 0 ? '-' : s << 31 - U < 0 && s << 31 - D < 0 ? '|'
                        : s == 0 ? '.'
                        : '?';
            }
        }

        return res;
    }
}
