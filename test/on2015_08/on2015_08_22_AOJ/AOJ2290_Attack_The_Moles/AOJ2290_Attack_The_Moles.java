package on2015_08.on2015_08_22_AOJ.AOJ2290_Attack_The_Moles;



import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MinimumCostFlow;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class AOJ2290_Attack_The_Moles {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), V = in.nextInt(), XLeft = in.nextInt(), XRight = in.nextInt();
        Entry[] entries = new Entry[N];
        for (int i = 0; i < N; i++) {
            entries[i] = new Entry(in.nextInt(), in.nextInt(), in.nextInt());
        }
        Arrays.sort(entries);
        int n = 0;
        // Assign indices so that the graph G becomes a DAG in the indices' order.
        int s = n++;
        int sLeft = n++;
        int sRight = n++;
        int[] from = new int[N], to = new int[N];
        for (int i = 0; i < N; i++) {
            from[i] = n++;
            to[i] = n++;
        }
        int t = n++;

        Graph G = new Graph(n);
        G.addFlow(s, sLeft, 1, 0);
        G.addFlow(s, sRight, 1, 0);
        G.addFlow(sLeft, t, 1, 0);
        G.addFlow(sRight, t, 1, 0);
        for (int i = 0; i < N; i++) {
            G.addFlow(from[i], to[i], 1, -entries[i].P);
            if (Math.abs(XLeft - entries[i].X) <= entries[i].T * V) G.addFlow(sLeft, from[i], 1, 0);
            if (Math.abs(XRight - entries[i].X) <= entries[i].T * V) G.addFlow(sRight, from[i], 1, 0);
            for (int j = i + 1; j < N; j++) {
                if (Math.abs(entries[i].X - entries[j].X) <= (entries[j].T - entries[i].T) * V)
                    G.addFlow(to[i], from[j], 1, 0);
            }
            G.addFlow(to[i], t, 1, 0);
        }
        double res = -new MinimumCostFlow(G).primalDual(s, t, 2);
        out.println((long) res);
    }

    class Entry implements Comparable<Entry> {
        long X, T, P;

        public Entry(int x, int t, int p) {
            X = x;
            T = t;
            P = p;
        }

        @Override
        public int compareTo(Entry o) {
            return (int) (T - o.T);
        }
    }
}
