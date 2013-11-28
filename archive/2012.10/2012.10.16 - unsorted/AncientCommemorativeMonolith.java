package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.LinkedList;

// 13:43 -
public class AncientCommemorativeMonolith {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), m = in.nextInt();
        if (n <= 0) throw new UnknownError();
        char[][][] glyphs = new char[n][][];
        char[] letters = new char[n];
        for (int i = 0; i < n; i++) {
            letters[i] = in.nextChar();
            int h = in.nextInt(), w = in.nextInt();
            glyphs[i] = new char[h][w];
            for (int x = 0; x < h; x++) glyphs[i][x] = in.next().toCharArray();
        }
        for (int i = 0; i < m; i++) {
            int h = in.nextInt(), w = in.nextInt();
            char[][] board = new char[h][w];
            for (int x = 0; x < h; x++) board[x] = in.next().toCharArray();
            String res = solve(glyphs, letters, board);
            out.println(res);
        }
        out.println("#");
    }

    int h, w;
    char[][] board;
    char[][][] glyphs;
    char[][][] revs;
    char[] letters;
    int maxHeight;

    private String solve(char[][][] glyphs, char[] letters, char[][] board) {
        h = board.length; w = board[0].length;
        this.board = board;
        this.glyphs = glyphs;
        revs = new char[glyphs.length][][];
        for (int i = 0; i < revs.length; i++) {
            revs[i] = rev(glyphs[i]);
        }
        this.letters = letters;
        maxHeight = 0;
        for (char[][] glyph : glyphs) maxHeight = Math.max(maxHeight, glyph.length);
        return multi(0, 0, h, w);
    }

    private char[][] rev(char[][] board) {
        char[][] res = new char[board.length][];
        for (int i = 0; i < res.length; i++)
            res[i] = new StringBuilder(String.valueOf(board[i])).reverse().toString().toCharArray();
        return res;
    }

    private String multi(int x0, int y0, int x1, int y1) {
        LinkedList<String> list = new LinkedList<String>();
        boolean canRight = true;// lower
        boolean canLeft = true;// upper
        while (y0 < y1) {
            int y = y0 + 1;
            while (y < y1 && !isEmpty(x0, y, x1, y + 1)) y++;
            String s = single(x0, y0, x1, y);
            if (!s.isEmpty() && s.charAt(0) == '(' && !hasUpper(s)) canLeft = false;
            if (!s.isEmpty() && s.charAt(0) == '(' && !hasLower(s)) canRight = false;
            list.add(s);
            y0 = y;
        }
        if (!canRight && !canLeft) throw new IllegalArgumentException();
        StringBuilder res = new StringBuilder();
        if (canRight) while (!list.isEmpty()) {
            String s = list.pollFirst();
            if (!s.isEmpty() && s.charAt(0) == '(') {
                for (char c : s.toCharArray()) if (Character.isLowerCase(c)) res.append(c);
            } else res.append(s);
        }
        else while (!list.isEmpty()) {
            String s = list.pollLast();
            if (!s.isEmpty() && s.charAt(0) == '(') {
                for (char c : s.toCharArray()) if (Character.isUpperCase(c)) res.append(("" + c).toLowerCase());
            } else res.append(s);
        }
        return res.toString();
    }

    private boolean hasLower(String s) {
        for (char c : s.toCharArray()) if (Character.isLowerCase(c)) return true;
        return false;
    }

    private boolean hasUpper(String s) {
        for (char c : s.toCharArray()) if (Character.isUpperCase(c)) return true;
        return false;
    }

    private String single(int x0, int y0, int x1, int y1) {
        if (x0 > x1 || y0 > y1) throw new AssertionError();
        while (x0 < x1 && isEmpty(x0, y0, x0 + 1, y1)) x0++;
        if (x0 == x1) return "";
        while (isEmpty(x1 - 1, y0, x1, y1)) x1--;
        while (isEmpty(x0, y0, x1, y0 + 1)) y0++;
        while (isEmpty(x0, y1 - 1, x1, y1)) y1--;
        int h = x1 - x0;
        if (h > maxHeight) {
            String inner = multi(x0 + 1, y0 + 1, x1 - 1, y1 - 1);
            return "[" + inner + "]";
        } else {
            StringBuilder res = new StringBuilder();
            res.append('(');
            for (int i = 0; i < glyphs.length; i++) {
                if (same(glyphs[i], x0, y0, x1, y1)) res.append(letters[i]);
                if (same(revs[i], x0, y0, x1, y1)) res.append(("" + letters[i]).toUpperCase());
            }
            res.append(')');
            if (res.length() == 2) throw new IllegalArgumentException();
            return res.toString();
        }
    }

    private boolean same(char[][] glyph, int x0, int y0, int x1, int y1) {
        int h = x1 - x0, w = y1 - y0;
        if (glyph.length != h || glyph[0].length != w) return false;
        for (int x = x0; x < x1; x++)
            for (int y = y0; y < y1; y++) if (glyph[x - x0][y - y0] != board[x][y]) return false;
        return true;
    }

    private boolean isEmpty(int x0, int y0, int x1, int y1) {
        for (int x = x0; x < x1; x++) for (int y = y0; y < y1; y++) if (board[x][y] != '.') return false;
        return true;
    }
}
