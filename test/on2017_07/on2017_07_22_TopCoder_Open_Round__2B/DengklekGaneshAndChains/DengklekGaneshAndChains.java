package on2017_07.on2017_07_22_TopCoder_Open_Round__2B.DengklekGaneshAndChains;



import java.util.Arrays;
import java.util.List;

public class DengklekGaneshAndChains {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public String getBestChains(String[] chains, int[] lengths) {
        int n = chains.length;
        String[] best = new String[n];
        for (int i = 0; i < chains.length; i++) {
            best[i] = chains[i];
            for (int j = 0; j < chains[i].length(); j++) {
                String s = chains[i].substring(j) + chains[i].substring(0, j);
                if (best[i].compareTo(s) < 0) {
                    best[i] = s;
                }
            }
        }
        StringBuilder res = new StringBuilder();
        boolean[] used = new boolean[n];
        for (int len : lengths) {
            int use = -1;
            for (int i = 0; i < n; i++) {
                if (used[i]) continue;
                if (use == -1 || better(best[i], best[use], len)) {
                    use = i;
                }
            }
            res.append(best[use].substring(0, len));
            used[use] = true;
        }
        return res.toString();
    }

    private boolean better(String a, String b, int len) {
        int cmp = a.substring(0, len).compareTo(b.substring(0, len));
        if (cmp != 0)  {
            return cmp > 0;
        }
        return a.substring(len).compareTo(b.substring(len)) < 0;
    }
}
