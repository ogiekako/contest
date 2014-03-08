package src;

public class SimilarNames {
    int MOD = (int) (1e9+7);
    public int count(String[] names, int[] info1, int[] info2) {
        int n = names.length;
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(names[i].startsWith(names[j])){
                    graph[j][i] = true;
                }
            }
        }
        return 0;
    }
}
