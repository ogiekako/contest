package on2017_08.on2017_08_05_TopCoder_Open_Round__3B.RemoveCharacters;



import java.util.Arrays;

public class RemoveCharacters {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int minimalDistinct(String A, String B) {
        boolean[][] graph = new boolean[26][26];
        boolean zero = true;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                String a = "", b = "";
                for (char c : A.toCharArray())
                    if (c - 'a' == i || c - 'a' == j) {
                        a += c;
                    }
                for (char c : B.toCharArray())
                    if (c - 'a' == i || c - 'a' == j) {
                        b += c;
                    }
                if (a.equals(b)) {
                    graph[i][j] = true;
                    if (i == j) zero = false;
                }
            }
        }
        return zero ? 26 : 26 - maxClique(graph);
    }

    /**
     * Computes the size of the max clique in the given graph.
     * O(2^(n/2) n^2).
     * Verified at TCO 16 R3B 250. (n = 26).
     */
    private int maxClique(boolean[][] graph) {
        int n = graph.length;
        int nn = n / 2;
        int[] maxClique = new int[1 << nn];
        for (int i = 0; i < 1 << nn; i++) {
            boolean ok = true;
            for (int j = 0; j < nn; j++)
                if (i << 31 - j < 0) {
                    for (int k = 0; k < nn; k++)
                        if (j != k && i << 31 - k < 0) {
                            if (!graph[j][k]) ok = false;
                        }
                }
            if (ok) {
                maxClique[i] = Integer.bitCount(i);
            }
            for (int j = 0; j < nn; j++)
                if (i << 31 - j >= 0) {
                    maxClique[i | 1 << j] = Math.max(maxClique[i | 1 << j], maxClique[i]);
                }
        }

        int res = 0;
        for (int i = 0; i < 1 << n - nn; i++) {
            boolean ok = true;
            for (int j = nn; j < n; j++)
                if (i << 31 - (j - nn) < 0) {
                    for (int k = nn; k < n; k++)
                        if (j != k && i << 31 - (k - nn) < 0) {
                            if (!graph[j][k]) ok = false;
                        }
                }
            if (ok) {
                int possible = 0;
                for (int j = 0; j < nn; j++) {
                    ok = true;
                    for (int k = nn; k < n; k++)
                        if (i << 31 - (k - nn) < 0) {
                            if (!graph[k][j]) ok = false;
                        }
                    if (ok) possible |= 1 << j;
                }
                res = Math.max(res, maxClique[possible] + Integer.bitCount(i));
            }
        }
        return res;
    }
}
