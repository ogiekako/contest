package tmp;

public class FoxAndGame {
    public int countStars(String[] result) {
        int res = 0;
        for (String stars : result) {
            for (char c : stars.toCharArray()) {
                if (c == 'o') res++;
            }
        }
        return res;
    }
}
