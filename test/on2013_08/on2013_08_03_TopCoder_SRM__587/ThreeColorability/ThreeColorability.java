package on2013_08.on2013_08_03_TopCoder_SRM__587.ThreeColorability;


import net.ogiekako.algorithm.dataStructure.UnionFind;

import java.util.Arrays;
public class ThreeColorability {
    public String[] lexSmallest(String[] cells) {
        int h = cells.length, w = cells[0].length();
        UnionFind uf = new UnionFind(w * 2);
        String S = "NZ";
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++)
                for (int k = 0; k < j; k++) {
                    char a = cells[i].charAt(j), b = cells[i].charAt(k);
                    if (a != '?' && b != '?') {
                        uf.union(j * 2 + S.indexOf(a), k * 2 + S.indexOf(b));
                        uf.union(j * 2 + 1 - S.indexOf(a), k * 2 + 1 - S.indexOf(b));
                    }
                }
        }
        for (int j = 0; j < w; j++) if (uf.find(j * 2, j * 2 + 1)) return new String[0];
        char[][] res = new char[h][w];
        Arrays.fill(res[0], '?');
        for (int i = 0; i < w; i++) {
            if (cells[0].charAt(i) != '?' && res[0][i] == '?') {
                int p = i * 2 + S.indexOf(cells[0].charAt(i));
                for (int j = 0; j < w; j++)
                    for (int k = 0; k < 2; k++) {
                        if (uf.find(p, j * 2 + k)) {
                            res[0][j] = S.charAt(k);
                        }
                    }
            }
        }
        for (int i = 0; i < w; i++)
            if (res[0][i] == '?') {
                int p = i * 2;
                for (int j = 0; j < w; j++)
                    for (int k = 0; k < 2; k++) if (uf.find(p, j * 2 + k)) res[0][j] = S.charAt(k);
            }
        for (int i = 0; i < w; i++)
            for (int j = 0; j < w; j++) {
                uf.union(i * 2 + S.indexOf(res[0][i]), j * 2 + S.indexOf(res[0][j]));
                uf.union(i * 2 + 1 - S.indexOf(res[0][i]), j * 2 + 1 - S.indexOf(res[0][j]));
            }
        String A = String.valueOf(res[0]);
        String B = rev(A);
        if (A.charAt(0) == 'Z') {
            String tmp = A; A = B; B = tmp;
        }
        for (int i = 1; i < h; i++) {
            boolean rev = false;
            for (int j = 0; j < w; j++) if (cells[i].charAt(j) != '?' && cells[i].charAt(j) != A.charAt(j)) rev = true;
            res[i] = rev ? B.toCharArray() : A.toCharArray();
        }
        String[] rr = new String[h];
        for (int i = 0; i < h; i++) rr[i] = String.valueOf(res[i]);
        return rr;
    }
    private String rev(String a) {
        String res = "";
        for (int i = 0; i < a.length(); i++) res += a.charAt(i) == 'N' ? 'Z' : 'N';
        return res;
    }
}
