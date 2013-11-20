package report;
import java.util.Scanner;
public class BWT {
    public static void main(String[] args) {
        new BWT().run();
    }
    int[] sa_r;
    private void run() {
        char[] cs = read();
        int[] sa = sa(cs);
    }
    /**
     * M&Mのアルゴリズム．
     * S[sa[0]...] < S[sa[1]...] < ... < S[sa[n]...]
     * が成り立っている．
     * @param cs
     * @return
     */
    private int[] sa(char[] cs) {
        int n = cs.length;
        sa_r = new int[n];
        int[] rank = new int[Math.max(n + 1, 256)];
        for (int i = 0; i < n; i++) rank[cs[i] + 1]++;
        boolean finished = true;
        if (rank[0] > 1) finished = false;
        for (int i = 0; i < rank.length; i++) {
            if (rank[i + 1] > 1) finished = false;
            rank[i + 1] += rank[i];
        }
        for (int i = 0; i < n; i++) sa_r[i] = rank[cs[i]];
        int d = 1;
        while (!finished) {
            finished = update(d);
        }
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[sa_r[i]] = i;
        }
        return sa;
    }
    private boolean update(int d) {
        int n = sa_r.length;
        int[] two = new int[n];
        for (int i = 0; i < n; i++) {
            two[i] = i + d < n ? sa_r[i + d] : 0;
        }

        int[] order = new int[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        sort(order, two);
        sort(order, sa_r);
        int[] order_r = new int[n];
        for (int i = 0; i < n; i++) order_r[order[i]] = i;
        boolean finished = true;
        for (int i = 0; i < n; ) {
            int j = i;
            while (j < n && two[order_r[i]] == two[order_r[j]] && sa_r[order_r[i]] == sa_r[order_r[j]]) {
                j++;
            }
            if (i - j > 1) finished = false;
            for (int k = i; k < j; k++) {
                order_r[k] += j - i;
            }
        }
        sa_r = order_r;
        return finished;
    }
    private void sort(int[] order, int[] key) {
        int n = order.length;
        int[] rank = new int[n + 1];
        for (int i = 0; i < n; i++) rank[key[i]]++;
        for (int i = 0; i < n; i++) rank[i + 1] += rank[i];
        for (int i = n - 1; i >= 0; i--) {
            order[i] = --rank[key[i]];
        }
    }

    private char[] read() {
        StringBuilder b = new StringBuilder();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            b.append(sc.nextLine());
            b.append('\n');
        }
        b.append((char) 0);
        return b.toString().toCharArray();
    }
}
