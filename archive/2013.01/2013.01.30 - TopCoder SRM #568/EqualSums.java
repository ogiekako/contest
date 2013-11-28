package tmp;

public class EqualSums {
    public int count(String[] board) {
        int n = board.length;
        int[][] field = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) field[i][j] = board[i].charAt(j) == '-' ? -1 : board[i].charAt(j) - '0';
        for (; ; ) {
            boolean changed = false;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (i != j) {
                        boolean found = false;
                        int diff = 0;
                        for (int k = 0; k < n; k++)
                            if (field[i][k] != -1 && field[j][k] != -1) {
                                found = true;
                                diff = field[i][k] - field[j][k];
                            }
                        if (found) {
                            for (int k = 0; k < n; k++)
                                if (field[i][k] != -1) {
                                    if (field[j][k] != -1 && field[j][k] != field[i][k] - diff || field[i][k] - diff < 0)
                                        return 0;
                                    if (field[j][k] == -1) changed = true;
                                    field[j][k] = field[i][k] - diff;
                                }
                        }
                        found = false;
                        diff = 0;
                        for (int k = 0; k < n; k++)
                            if (field[k][i] != -1 && field[k][j] != -1) {
                                found = true;
                                diff = field[k][i] - field[k][j];
                            }
                        if (found) {
                            for (int k = 0; k < n; k++)
                                if (field[k][i] != -1) {
                                    if (field[k][j] != -1 && field[k][j] != field[k][i] - diff || field[k][i] - diff < 0)
                                        return 0;
                                    if (field[k][j] == -1) changed = true;
                                    field[k][j] = field[k][i] - diff;
                                }
                        }
                    }
            if (!changed) break;
        }
        debug("field");
        for (int[] a : field) debug(a);
        long[] dp = new long[2];
        dp[0] = 1;
        boolean[] rowVisited = new boolean[n];
        boolean[] colVisited = new boolean[n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (!rowVisited[i] && !colVisited[j] && field[i][j] != -1) {
                    debug("row");
                    for (int k = 0; k < n; k++)
                        if (field[k][j] != -1) {
                            debug(k);
                            rowVisited[k] = true;
                        }
                    debug("col");
                    for (int k = 0; k < n; k++)
                        if (field[i][k] != -1) {
                            debug(k);
                            colVisited[k] = true;
                        }
                    int numRow = 0;
                    for (int k = 0; k < n; k++) if (field[k][j] != -1) numRow++;
                    int[] rows = new int[numRow];
                    numRow = 0;
                    for (int k = 0; k < n; k++) if (field[k][j] != -1) rows[numRow++] = k;
                    int numCol = 0;
                    for (int k = 0; k < n; k++) if (field[i][k] != -1) numCol++;
                    int[] cols = new int[numCol];
                    numCol = 0;
                    for (int k = 0; k < n; k++) if (field[i][k] != -1) cols[numCol++] = k;
                    int minimum = Integer.MAX_VALUE;
                    for (int row : rows)
                        for (int col : cols) {
                            minimum = Math.min(minimum, field[row][col]);
                        }
                    if (minimum < 0) throw new AssertionError();
                    long[] nDp = new long[2];
                    nDp[0] = dp[0] * minimum % MOD;
                    nDp[1] = (dp[0] + dp[1] * (minimum + 1)) % MOD;
                    dp = nDp;
                }
        return (int) dp[1];
    }
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }
    int MOD = (int) (1e9 + 7);
}
