package on2015_08.on2015_08_29_TopCoder_SRM__623.UniformBoard;



import java.util.Arrays;

public class UniformBoard {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int getBoard(String[] board, int K) {
        int n = board.length;
        int A = 0;
        boolean empty = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i].charAt(j) == 'A') A++;
                if (board[i].charAt(j) == '.') empty = true;
            }
        }
        int res = 0;
        for (int x0 = 0; x0 < n; x0++)
            for (int x1 = x0 + 1; x1 <= n; x1++)
                for (int y0 = 0; y0 < n; y0++)
                    for (int y1 = y0 + 1; y1 <= n; y1++) {
                        int s = (x1 - x0) * (y1 - y0);
                        if (s > A) continue;
                        int move = 0;
                        for (int x = x0; x < x1; x++)
                            for (int y = y0; y < y1; y++) {
                                if (board[x].charAt(y) == '.') move++;
                                if (board[x].charAt(y) == 'P') move += 2;
                            }
                        if (move > 0 && !empty) continue;
                        if (move > K) continue;
                        res = Math.max(res, s);
                    }
        return res;
    }
}
