package tmp;

// Paste me into the FileEdit configuration dialog

public class AntsMeet {
    String DIR = "NSEW";
    int[] dx = {0, 0, 1, -1};
    int[] dy = {1, -1, 0, 0};
    public int countAnts(int[] x, int[] y, String direction) {
        for (int i = 0; i < x.length; i++) x[i] += x[i];
        for (int i = 0; i < y.length; i++) y[i] += y[i];
        boolean[] hit = new boolean[x.length];
        for (int _ = 0; _ < 5000; _++) {
            boolean[] nHit = hit.clone();
            for (int i = 0; i < x.length; i++) {
                x[i] += dx[DIR.indexOf(direction.charAt(i))];
                y[i] += dy[DIR.indexOf(direction.charAt(i))];
            }
            for (int i = 0; i < x.length; i++)
                for (int j = 0; j < i; j++)
                    if (!hit[i] && !hit[j] && x[i] == x[j] && y[i] == y[j]) {
                        nHit[i] = nHit[j] = true;
                    }
            hit = nHit;
        }
        int res = 0;
        for (boolean b : hit) if (!b) res++;
        return res;
    }
}