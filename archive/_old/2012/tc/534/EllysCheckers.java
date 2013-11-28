package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.StringUtils;

public class EllysCheckers {
    public String getWinner(String board) {
        board = StringUtils.reverse(board);
        int cnt = 0;
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) == 'o') cnt += i;
        }
        return cnt % 2 == 1 ? "YES" : "NO";
    }
}

