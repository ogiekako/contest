package tmp;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
// 13'38
public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        char[][] board = new char[4][];
        for (int i = 0; i < 4; i++) board[i] = in.next().toCharArray();
        boolean filled = true;
        for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) if (board[i][j] == '.') filled = false;
        boolean oWon = won(board, 'O');
        boolean xWon = won(board, 'X');
        String res = null;
        if (oWon) {
            res = "O won";
        } else if (xWon) {
            res = "X won";
        } else if (filled) {
            res = "Draw";
        } else {
            res = "Game has not completed";
        }
        out.printFormat("Case #%d: %s\n", testNumber, res);
    }

    private boolean won(char[][] board, char c) {
        for (int i = 0; i < 4; i++) {
            boolean won = true;
            for (int j = 0; j < 4; j++) if (board[i][j] != 'T' && board[i][j] != c) won = false;
            if (won) return true;
        }
        for (int j = 0; j < 4; j++) {
            boolean won = true;
            for (int i = 0; i < 4; i++) if (board[i][j] != 'T' && board[i][j] != c) won = false;
            if (won) return true;
        }
        boolean won = true;
        for (int i = 0; i < 4; i++) if (board[i][i] != 'T' && board[i][i] != c) won = false;
        if (won) return true;
        won = true;
        for (int i = 0; i < 4; i++) if (board[i][3 - i] != 'T' && board[i][3 - i] != c) won = false;
        if (won) return true;
        return false;
    }
}
