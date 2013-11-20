package on2013_08.on2013_08_27_Single_Round_Match_589.GooseTattarrattatDiv1;


import net.ogiekako.algorithm.dataStructure.UnionFind;
public class GooseTattarrattatDiv1 {
    public int getmin(String S) {
        UnionFind uf = new UnionFind(26);
        for (int i = 0; i < S.length(); i++) uf.union(S.charAt(i) - 'a', S.charAt(S.length() - 1 - i) - 'a');
        int res = 0;
        int[] count = new int[26];
        for (char c : S.toCharArray()) count[c - 'a']++;
        for (int i = 0; i < 26; i++) {
            if (uf.root(i) == i) {
                int sum = 0;
                int max = Integer.MIN_VALUE;
                for (int j = 0; j < 26; j++)
                    if (uf.root(j) == i) {
                        sum += count[j];
                        max = Math.max(max, count[j]);
                    }
                res += sum - max;
            }
        }
        return res;
    }
}
