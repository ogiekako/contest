package tmp;

import java.util.HashMap;

public class TheNumberGame {
    public String determineOutcome(int _A, int _B) {
        String A = "" + _A;
        String B = "" + _B;
        return solve(A, B, 0) ? "Manao wins" : "Manao loses";
    }

    HashMap<String, Boolean> map = new HashMap<String, Boolean>();
    private boolean solve(String A, String B, int turn) {
        if (A.equals(B)) return true;
        if (turn >= 100) {
            return false;
        }
        String key = A + " " + B + " " + turn;
        if (map.containsKey(key)) return map.get(key);
        boolean res;
        if ((turn & 1) == 0) {
            boolean res1 = solve(rev(A), B, turn + 1);
            boolean res2 = solve(sub(A), B, turn + 1);
            res = res1 || res2;
        } else {
            boolean res1 = solve(A, rev(B), turn + 1);
            boolean res2 = solve(A, sub(B), turn + 1);
            res = res1 && res2;
        }
        map.put(key, res);
        return res;
    }

    private String sub(String A) {
        if (A.length() == 0) return A;
        return A.substring(0, A.length() - 1);
    }

    private String rev(String A) {
        return new StringBuilder(A).reverse().toString();
    }
}
