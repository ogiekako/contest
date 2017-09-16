package on2017_09.on2017_09_11_TopCoder_Open_Round__Wildcard.SixDegreesOfSeparation;



import java.util.*;

public class SixDegreesOfSeparation {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    public int[] getAdditionalEdges(int n, int[] a, int[] b) {
        int[] dist = new int[n];
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < a.length; i++) {
            graph[a[i]][b[i]] = graph[b[i]][a[i]] = true;
        }
        Arrays.fill(dist, 10000);
        dist[0] = 0;
        Queue<Integer> que = new LinkedList<>();
        que.offer(0);
        que.offer(0);
        while (!que.isEmpty()) {
            int i = que.poll();
            int d = que.poll();
            for (int v = 0; v < n; v++)
                if (graph[i][v]) {
                    if (v >= 0 && dist[v] > d + 1) {
                        dist[v] = d + 1;
                        que.offer(v);
                        que.offer(d + 1);
                    }
                }
        }

        for (int i = 0; i < 3; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (dist[j] % 3 == i && !graph[0][j]) cnt++;
            }
            if (cnt * 3 <= n) {
                if (cnt == 0) return new int[0];
                int[] res = new int[i == 0 ? cnt * 2 - 2 : cnt * 2];
                cnt = 0;
                for (int j = 1; j < n; j++) {
                    if (dist[j] % 3 == i && !graph[0][j]) {
                        res[cnt * 2] = 0;
                        res[cnt * 2 + 1] = j;
                        cnt++;
                    }
                }
                return res;
            }
        }
        throw new AssertionError();
    }
}
