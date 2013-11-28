package tmp;

public class EllysFigurines {
    public int getMoves(String[] board, int R, int C) {
        int h = board.length, w = board[0].length();
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 1 << w; i++) {
            int mask = 0;
            for (int j = 0; j < C; j++) mask |= i << j;
            int row = 0;
            for (int j = 0; j < w; j++)
                if (mask << 31 - j >= 0) {
                    for (int k = 0; k < h; k++) if (board[k].charAt(j) == 'X') row |= 1 << k;
                }
            int val = 0;
            for (int j = 0; j < h; ) {
                if (row << 31 - j < 0) {
                    val++;
                    j += R;
                } else j++;
            }
            res = Math.min(res, val + Integer.bitCount(i));
        }
        return res;
    }
}
