package tmp;

public class PenguinSledding {
    public long countDesigns(int numCheckpoints, int[] checkpoint1, int[] checkpoint2) {
        long res = 0;
        for (int i = 0; i < numCheckpoints; i++) {
            int count = 0;
            for (int c : checkpoint1) if (c == i + 1) count++;
            for (int c : checkpoint2) if (c == i + 1) count++;
            res += 1L << count;
        }
        res -= checkpoint1.length;
        boolean[][] graph = new boolean[numCheckpoints][numCheckpoints];
        for (int i = 0; i < checkpoint1.length; i++)
            graph[checkpoint1[i] - 1][checkpoint2[i] - 1] =
                    graph[checkpoint2[i] - 1][checkpoint1[i] - 1] =
                            true;
        for (int i = 0; i < numCheckpoints; i++)
            for (int j = 0; j < i; j++)
                for (int k = 0; k < j; k++) {
                    if (graph[i][j] && graph[j][k] && graph[k][i]) res++;
                }
        return res - numCheckpoints + 1;
    }
}
