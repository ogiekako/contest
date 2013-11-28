package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class TheCitiesAndRoadsDivOne {
    int M = 1234567891;
    public int find(String[] map) {
        int n = map.length;
        boolean[][] reachable = new boolean[n][n];
        for (int i = 0; i < n; i++) reachable[i][i] = true;
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (map[i].charAt(j) == 'Y') reachable[i][j] = true;
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) reachable[i][j] |= reachable[i][k] && reachable[k][j];
        int[] dagId = new int[n];
        Arrays.fill(dagId, -1);
        int m = 0;
        for (int i = 0; i < n; i++)
            if (dagId[i] == -1) {
                for (int j = 0; j < n; j++) if (reachable[i][j]) dagId[j] = m;
                m++;
            }
        int[] numEdge = new int[m];
        int[] numNode = new int[m];
        for (int i = 0; i < n; i++) numNode[dagId[i]]++;
        for (int i = 0; i < n; i++) for (int j = 0; j < i; j++) if (map[i].charAt(j) == 'Y') numEdge[dagId[i]]++;
        boolean loop = false;
        for (int i = 0; i < m; i++) {
            if (numEdge[i] > numNode[i]) return 0;
            if (numEdge[i] == numNode[i]) {
                if (loop) return 0;
                loop = true;
            }
        }
//       return recur();
        return 0;
    }
    class Array {
        int[] is;

    }


}

