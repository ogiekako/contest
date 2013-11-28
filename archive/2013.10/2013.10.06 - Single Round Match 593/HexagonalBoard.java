package src;

public class HexagonalBoard {
    int[] dx = {0, 1, 1, -1, -1, 0};
    int[] dy = {1, 0, -1, 0, 1, -1};
    int[][] col;
    String[] board;
    public int minColors(String[] board) {
        this.board = board;
        int res = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length(); j++)
                if (board[i].charAt(j) == 'X') {
                    res = Math.max(res, 1);
                    for (int d = 0; d < 3; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (0 <= nx && nx < board.length && 0 <= ny && ny < board[0].length()) {
                            if (board[nx].charAt(ny) == 'X') {
                                res = Math.max(res, 2);
                            }
                        }
                    }
                }
        if(res < 2)return res;
        col = new int[board.length][board[0].length()];
        for(int i=0;i<board.length;i++)for(int j=0;j<board[0].length();j++)if(board[i].charAt(j) == 'X' && col[i][j] == 0){
            if(!bip(i, j, 1))return 3;
        }
        return 2;
    }
    boolean bip(int x, int y, int c) {
        if(col[x][y] == c)return true;
        if(col[x][y] != 0)return false;
        col[x][y] = c;
        for (int d = 0; d < 3; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (0 <= nx && nx < board.length && 0 <= ny && ny < board[0].length()) {
                if (board[nx].charAt(ny) == 'X') {
                    if(!bip(nx,ny,3-c))return false;
                }
            }
        }
        return true;
    }
}
