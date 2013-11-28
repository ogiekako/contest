package src;

public class EelAndRabbit {
    public int getmax(int[] l, int[] t) {
        int n = l.length;
        int[] c = new int[n * 2];
        for (int i = 0; i < n; i++) {
            c[i * 2] = -t[i];
            c[i * 2 + 1] = -t[i] - l[i];
        }
        int res = -0;
        for (int i : c) {
            for (int j : c) {
                int p = 0;
                for (int k = 0; k < n; k++) {
                    int from = -t[k] - l[k], to = -t[k];
                    if (from <= i && i <= to || from <= j && j <= to) p++;
                }
                res = Math.max(res, p);
            }
        }
        return res;
    }
}
