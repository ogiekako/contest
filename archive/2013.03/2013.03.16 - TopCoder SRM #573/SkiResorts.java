package tmp;

import java.util.PriorityQueue;

public class SkiResorts {
    public long minCost(String[] road, int[] altitude) {
        PriorityQueue<E> que = new PriorityQueue<E>();
        int n = road.length;
        boolean[][] vis = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            que.offer(new E(0, i, Math.abs(altitude[0] - altitude[i])));
        }
        while (!que.isEmpty()) {
            E e = que.poll();
            if (vis[e.id][e.hi]) continue;
            vis[e.id][e.hi] = true;
            if (e.id == n - 1) return e.val;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (road[e.id].charAt(i) == 'Y' && altitude[e.hi] >= altitude[j] && !vis[i][j]) {
                        que.offer(new E(i, j, e.val + Math.abs(altitude[i] - altitude[j])));
                    }
        }
        return -1;
    }
    class E implements Comparable<E> {
        int id;
        int hi;
        long val;

        public E(int id, int hi, long val) {
            this.id = id;
            this.hi = hi;
            this.val = val;
        }

        public int compareTo(E o) {
            return val < o.val ? -1 : val > o.val ? 1 : 0;
        }
    }
}
