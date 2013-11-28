package tmp;

public class FoxAndMountainEasy {
    public String possible(int n, int h0, int hn, String history) {
        if (n % 2 != Math.abs(hn - h0) % 2) return "NO"; // test7
        int cur = 0;
        int min = 0;
        for (char c : history.toCharArray()) {
            if (c == 'U') cur++;
            else cur--;
            min = Math.min(min, cur);
        }
        int need = -min;
        n -= history.length();
        while (h0 < need) {
            h0++; n--;
        }
        if (n < 0) return "NO";
        h0 += cur;
        return Math.abs(h0 - hn) <= n ? "YES" : "NO";
    }
}
