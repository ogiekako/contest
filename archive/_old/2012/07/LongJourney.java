package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.IntegerUtils;

import java.util.HashSet;
import java.util.PriorityQueue;

public class LongJourney {
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }
    int INF = 1 << 29;
    public long minimumCost(int[] fuelPrices, int fuelTank, String[] roads) {
        int n = fuelPrices.length;
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) dist[i][j] = INF;
        for (int i = 0; i < n; i++) dist[i][i] = 0;
        StringBuilder b = new StringBuilder();
        for (String s : roads) b.append(s);
        for (String s : b.toString().split(" ")) {
//           System.err.println(s);
            int i = Integer.valueOf(s.split(",")[0]);
            int j = Integer.valueOf(s.split(",")[1]);
            int fuel = Integer.valueOf(s.split(",")[2]);
            dist[i][j] = dist[j][i] = fuel;
        }
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) dist[i][j] = Math.min(dist[i][k] + dist[k][j], dist[i][j]);
        HashSet<E> set = new HashSet<E>();
        PriorityQueue<E> que = new PriorityQueue<E>();
        E cur = new E(0, 0, 0);
        que.offer(cur);
        while (!que.isEmpty()) {
            cur = que.poll();
            debug(cur.pos, cur.fuel, cur.cost);
            if (set.contains(cur)) continue;
            set.add(cur);
            if (cur.pos == 1) return cur.cost;
            for (int i = 0; i < n; i++)
                if (i != cur.pos) {
                    // maximize
                    long nCost = cur.cost + (long) (fuelTank - cur.fuel) * fuelPrices[cur.pos];
                    int nFuel = fuelTank - dist[cur.pos][i];
                    if (nFuel >= 0) {
                        E nxt = new E(i, nFuel, nCost);
                        que.offer(nxt);
                    }
                    // to 0
                    if (dist[cur.pos][i] <= fuelTank && dist[cur.pos][i] > cur.fuel) {
                        nCost = cur.cost + (long) (dist[cur.pos][i] - cur.fuel) * fuelPrices[cur.pos];
                        nFuel = 0;
                        E nxt = new E(i, nFuel, nCost);
                        que.offer(nxt);
                    }
                }
        }
        return -1;
    }
    class E implements Comparable<E> {
        int pos;
        int fuel;
        long cost;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            E e = (E) o;

            if (fuel != e.fuel) return false;
            if (pos != e.pos) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = pos;
            result = 31 * result + fuel;
            return result;
        }

        E(int pos, int fuel, long cost) {

            this.cost = cost;
            this.fuel = fuel;
            this.pos = pos;
        }

        public int compareTo(E o) {
            return IntegerUtils.compare(cost, o.cost);
        }
    }


}

