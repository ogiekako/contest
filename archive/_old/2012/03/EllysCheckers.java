package tmp;

// Paste me into the FileEdit configuration dialog

public class EllysCheckers {
    public String getWinner(String board) {
        int sum = 0;
        for (int i = 0; i < board.length(); i++) {
            if (board.charAt(i) == 'o') {
                sum += board.length() - 1 - i;
            }
        }
        return sum % 2 == 0 ? "NO" : "YES";
    }
}

