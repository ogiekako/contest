package on2015_08.on2015_08_19_TopCoder_Open_Round__2B.Balance;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Balance {
    int n;
    public String canTransform(String[] initial, String[] target) {
        initial = sur(initial);
        debug(initial);
        List<Integer>[] tree = tree(initial);
        String A = str(tree, 0);
        target = sur(target);
        String B = str(tree(target), 0);
        return A.equals(B) ? "Possible" : "Impossible";
    }

    private String str(List<Integer>[] tree, int root) {
        List<String> cs = new ArrayList<String>();
        for (int c : tree[root]) {
            cs.add(str(tree, c));
        }
        Collections.sort(cs);
        StringBuilder b = new StringBuilder();
        b.append('(');
        for (String c : cs) {
            b.append(c);
        }
        b.append(')');
        return b.toString();
    }

    private List<Integer>[] tree(String[] initial) {
        n = initial.length;
        int[][] comp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(comp[i], -1);
        }
        int m = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (comp[i][j] >= 0)continue;
                fill(initial, comp, i, j, m);
                m++;
            }
        }
        debug(comp);
        int[] parents = new int[m];
        parents[0] = -1;
        for (int k = 1; k < m; k++) {
            loop: for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (comp[i][j] == k) {
                        parents[k] = comp[i-1][j];
                        break loop;
                    }
                }
            }
        }
        List<Integer>[] cs = new List[m];
        for (int i = 0; i < m; i++) {
            cs[i] = new ArrayList<Integer>();
        }
        for (int i = 1; i < m; i++) {
            cs[parents[i]].add(i);
        }
        return cs;
    }

    int[] dx = {1,0,-1,0};
    int[] dy = {0,1,0,-1};
    private void fill(String[] map, int[][] comp, int i, int j, int p) {
        if(comp[i][j] >= 0)return;
        comp[i][j] = p;
        for (int d = 0; d < 4; d++) {
            int ni = i + dx[d];
            int nj = j + dy[d];
            if (0 <= ni && ni < n && 0 <= nj && nj < n && map[i].charAt(j) == map[ni].charAt(nj)) {
                fill(map, comp, ni, nj, p);
            }
        }
    }

    private static void debug(Object... os) {
//        System.out.println(Arrays.deepToString(os));
    }

    private String[] sur(String[] initial) {
        String[] res = new String[initial.length + 2];
        for (int i = 0; i < res.length; i++) {
            res[i] = "";
            for (int j = 0; j < initial.length + 2; j++) {
                if (i < 1 || j < 1 || i >= initial.length + 1 || j >= initial.length + 1) {
                    res[i] += '#';
                } else {
                    res[i] += initial[i - 1].charAt(j - 1);
                }
            }
        }
        return res;
    }
}
