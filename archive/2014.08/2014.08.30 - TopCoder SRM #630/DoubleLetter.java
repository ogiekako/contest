package src;

public class DoubleLetter {
    public String ableToSolve(String S) {
        while(!S.isEmpty()) {
            boolean replaced = false;
            for (char c = 'a'; c <= 'z'; c++) {
                String t = "" + c + c;
                if (S.contains(t)) {
                    S = S.replaceFirst(t, "");
                    replaced = true;
                }
            }
            if (!replaced) {
                return "Impossible";
            }
        }
        return "Possible";
    }
}
