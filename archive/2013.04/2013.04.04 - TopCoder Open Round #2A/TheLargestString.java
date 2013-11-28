package tmp;

public class TheLargestString {
    String s, t;

    public String find(String s, String t) {
        this.s = s; this.t = t;
        String res = "";
        for (; ; ) {
            for (char c = 'z'; c >= 'a' - 1; c--) {
                if (c < 'a') return res;
                if (possible(res + c)) {
                    res += c;
                    System.err.println(res);
                    break;
                }
            }
        }
    }

    private boolean possible(String p) {
        for (int i = 0; i <= p.length(); i++) {
            String a = p.substring(0, i);
            String b = p.substring(i);
            if (possible(a, b)) return true;
        }
        return false;
    }

    private boolean possible(String a, String b) {
        if (a.length() < b.length()) return false;
        for (int i = 0, j = 0; i < a.length(); i++, j++) {
            while (j < s.length() && (s.charAt(j) != a.charAt(i) || i < b.length() && t.charAt(j) != b.charAt(i))) j++;
            if (j >= s.length()) return false;
        }
        return true;
    }
}
