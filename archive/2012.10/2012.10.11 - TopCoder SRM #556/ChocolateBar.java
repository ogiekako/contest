package tmp;

public class ChocolateBar {
    public int maxLength(String letters) {
        int res = 0;
        for (int i = 0; i < letters.length(); i++)
            for (int j = i + 1; j < letters.length() + 1; j++) {
                String s = letters.substring(i, j);
                if (ok(s)) res = Math.max(res, s.length());
            }
        return res;
    }

    private boolean ok(String s) {
        boolean[] bs = new boolean[256];
        for (char c : s.toCharArray()) {
            if (bs[c]) return false;
            bs[c] = true;
        }
        return true;
    }
}
