package on2012_6_7.orthogonalanagram;



// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.graph.denseGraph.BipartiteGraphAlgorithm;

public class OrthogonalAnagram {
    public String solve(String S) {
        int N = S.length();
        int[] is = new int[26];
        for (char c : S.toCharArray()) is[c - 'a']++;
        int[] js = is.clone();
        if (!can(is, js, N)) return "";
        String R = "";
        for (int i = 0; i < N; i++) {
            is[S.charAt(i) - 'a']--;
            for (int j = 0; j < 26; j++)
                if (js[j] > 0 && j != S.charAt(i)-'a') {
                    js[j]--;
                    if (can(is, js, N - 1 - i)) {
                        R += (char) (j + 'a');
                        break;
                    }
                    js[j]++;
                }
        }
        return R;
    }

    private boolean can(int[] as, int[] bs, int N) {
        as = as.clone(); bs = bs.clone();
        boolean[][] graph = new boolean[N][N];
        int i = 0;
        for (int k = 0; k < 26; k++) {
            int a = as[k];
            for (int p = 0; p < a; p++) {
                int j = 0;
                for (int l = 0; l < 26; l++) {
                    int b = bs[l];
                    for (int q = 0; q < b; q++) {
                        if (k != l) graph[i][j] = true;
                        j++;
                    }
                }
                i++;
            }
        }
        return BipartiteGraphAlgorithm.maximumMatching(graph) == N;
    }
}

