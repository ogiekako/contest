package on2015_08.on2015_08_31_TopCoder_SRM__635.StoryFromTCO;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;

import java.util.Arrays;

public class StoryFromTCO {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    /**
     * 枝の張り方に注意がひつよう。
     * <p/>
     * place 50
     * v
     * cut  100 -> 200 -> 300
     * v 1    v 1
     * cut2 100    200
     */
    class Entry implements Comparable<Entry> {
        int place, cutoff;

        public Entry(int place, int cutoff) {
            this.place = place;
            this.cutoff = cutoff;
        }

        @Override
        public int compareTo(Entry o) {
            return cutoff - o.cutoff;
        }
    }

    public int minimumChanges(int[] places, int[] cutoff) {
        int n = places.length;
        Entry[] es = new Entry[n];
        for (int i = 0; i < n; i++) {
            es[i] = new Entry(places[i], cutoff[i]);
        }
        Arrays.sort(es);
        for (int i = 0; i < es.length; i++) {
            places[i] = es[i].place;
            cutoff[i] = es[i].cutoff;
        }

        Graph G = new Graph(n * 3 + 2);
        int s = n * 3, t = s + 1;
        for (int i = 0; i < places.length; i++) {
            G.addFlow(s, i, 1, 0);
            G.addFlow(n + i, n * 2 + i, 1, 1);
            G.addFlow(n * 2 + i, t, 1, 0);
            if (cutoff[i] >= places[i]) {
                G.addFlow(i, n * 2 + i, 1, 0);
            }
            for (int j = 0; j < n; j++) {
                if (cutoff[j] >= places[i]) {
                    G.addFlow(i, n + j, 1, 0);
                    break;
                }
            }
            if (i > 0) {
                G.addFlow(n + i - 1, n + i, n, 0);
            }
        }
        double res = new MinimumCostFlow(G).primalDual(s, t, n);
        return res == Double.POSITIVE_INFINITY ? -1 : (int) res;
    }
}
