package tmp;

public class FauxPalindromes {
    public String classifyIt(String word) {
        if (isPar(word)) return "PALINDROME";
        else if (isFaux(word)) return "FAUX";
        return "NOT EVEN FAUX";
    }

    private boolean isFaux(String word) {
        String s = "";
        for (int i = 0; i < word.length(); ) {
            int j = i;
            while (j < word.length() && word.charAt(i) == word.charAt(j)) j++;
            s += word.charAt(i);
            i = j;
        }
        return isPar(s);
    }

    private boolean isPar(String word) {
        String rev = rev(word);
        return rev.equals(word);
    }

    private String rev(String word) {
        return new StringBuilder(word).reverse().toString();
    }
}
