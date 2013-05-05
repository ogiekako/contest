package on_2012.on2012_4_30.flipgame;


// Paste me into the FileEdit configuration dialog

import java.util.Random;

public class FlipGame {
    public int minOperations(String[] board) {
        int H = board.length, W = board[0].length();
        boolean[][] bs = new boolean[board.length][board[0].length()];
        for (int i = 0; i < bs.length; i++) for (int j = 0; j < bs[0].length; j++) bs[i][j] = board[i].charAt(j) == '1';
        for (int step = 0; ; step++) {
            boolean ok = true;
            for (boolean[] a : bs) for (boolean b : a) if (b) ok = false;
            if (ok) return step;
            int y = 0;
            for (int x = 0; x < bs.length; x++) {
                int ny;
                for (ny = W - 1; ny >= y; ny--) {
                    if (bs[x][ny]) break;
                }
                for (; y <= ny; y++) {
                    for (int i = x; i < H; i++) bs[i][y] ^= true;
                }
            }
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random(12048124);
        for (int o = 0; o < 100; o++) {
            System.err.println(o);
            String[] ss = new String[50];
            for (int i = 0; i < 50; i++) {
                ss[i] = "";
                for (int j = 0; j < 50; j++) {
                    ss[i] += (rnd.nextBoolean() ? "1" : "0");
                }
            }
            new FlipGame().minOperations(ss);
        }
    }
}

