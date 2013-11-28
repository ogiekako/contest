package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class ChromaticNumber {
    public int minColors(String[] _graph) {
        int n = _graph.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] = _graph[i].charAt(j) == 'N';
        for (int i = 0; i < n; i++) graph[i][i] = true;
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) graph[i][j] |= graph[i][k] && graph[k][j];
        int[] id = new int[n];
        int m = 0;
        Arrays.fill(id, -1);
        for (int i = 0; i < n; i++)
            if (id[i] == -1) {
                for (int j = 0; j < n; j++) if (graph[i][j]) id[j] = m;
                m++;
            }
        int[] edge = new int[m];
        for (int i = 0; i < n; i++) for (int j = 0; j < i; j++) if (_graph[i].charAt(j) == 'N') edge[id[i]]++;
        int[] node = new int[m];
        for (int i = 0; i < n; i++) node[id[i]]++;
        int res = 0;
        for (int i = 0; i < m; i++) {
            if (node[i] == 3 && edge[i] == 3) res++;
            else res += (node[i] + 1) / 2;
        }
        return res;
    }


}

