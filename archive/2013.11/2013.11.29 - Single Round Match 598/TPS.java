package src;

public class TPS {
    int n;
    char[][] graph;
    public int minimalBeacons(String[] linked) {
        n = linked.length;
        if(n == 1)return 0;
        graph = new char[n][];
        for (int i = 0; i < n; i++) {
            graph[i] = linked[i].toCharArray();
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.min(res, rec(i, -1));
        }
        return res + 1;
    }
    private int rec(int i, int p) {
        int zero = 0;
        int res = 0;
        for (int j = 0; j < n; j++) {
            if(j != p && graph[i][j] == 'Y'){
                int t = rec(j, i);
                if(t == 0)zero++;
                else res += t;
            }
        }
        return res + Math.max(zero - 1, 0);
    }
}
