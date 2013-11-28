package tmp;

public class ColorfulStrings {
    public String getKth(int n, int k) {
        if (n == 1) {
            if (k <= 10) return "" + (k - 1);
            return "";
        }
        if (n > 8) return "";
        this.n = n;
        this.k = k;
        boolean[] used = new boolean[8];// 2..9
        dfs(0, new int[n], 0, used, new boolean[2 * 3 * 4 * 5 * 6 * 7 * 8 * 9 + 1]);
        return res;
    }

    private void dfs(int number, int[] ns, int p, boolean[] used, boolean[] table) {
        if (k <= 0) return;
        if (p == n) {
            k--;
            if (k == 0) res = "" + number;
            return;
        }
        for (int i = 0; i < used.length; i++)
            if (!used[i]) {
                int v = i + 2;
                boolean ok = true;
                int mul = 1;
                ns[p] = v;
                for (int j = p; j >= 0; j--) {
                    mul *= ns[j];
                    if (table[mul]) ok = false;
                }
                if (!ok) continue;

                mul = 1;
                for (int j = p; j >= 0; j--) {
                    mul *= ns[j];
                    table[mul] = true;
                }
                used[i] = true;
                dfs(number * 10 + v, ns, p + 1, used, table);
                used[i] = false;
                mul = 1;
                for (int j = p; j >= 0; j--) {
                    mul *= ns[j];
                    table[mul] = false;
                }
            }
    }
    int n;
    int k;
    String res = "";
}
