package net.ogiekako.algorithm.misc;

import junit.framework.Assert;
import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.graph.Edge;
import net.ogiekako.algorithm.graph.SimpleEdge;
import org.junit.Test;

import java.util.Random;

public class MatroidIntersectionTest {
    public void testIntersection() throws Exception {
        Random rnd = new Random(2408912490L);
        for (int iteration = 0; iteration < 50; iteration++) {
            final int n = 15;
            final int m = 15;
            testIntersection(rnd, n, m, true);
        }
    }

    @Test(timeout = 1000)
    public void testIntersection2() throws Exception {
        Random rnd = new Random(120934812L);
        for (int iteration = 0; iteration < 50; iteration++) {
            testIntersection(rnd, 50, 50, false);
        }
    }

    private void testIntersection(Random rnd, final int n, final int m, boolean doCompare) {
        final Edge[] es = new Edge[m];
        boolean[][] bs = new boolean[n][n];
        for (int i = 0; i < m; i++) {
            int s = rnd.nextInt(n);
            int t = rnd.nextInt(n);
            if (bs[s][t] || s == t) {
                i--; continue;
            }
            bs[s][t] = true;
            es[i] = new SimpleEdge(s, t);
        }
        Matroid f1 = new Matroid() {
            public boolean isIndependentSet(boolean[] X) {
                UnionFind uf = new UnionFind(n);
                for (int i = 0; i < m; i++)
                    if (X[i]) {
                        if (uf.find(es[i].from(), es[i].to())) return false;
                        uf.union(es[i].from(), es[i].to());
                    }
                return true;
            }

            public int size() {
                return m;
            }
        };
        Matroid f2 = new Matroid() {
            public boolean isIndependentSet(boolean[] X) {
                int[] cnt = new int[n];
                for (int i = 0; i < m; i++)
                    if (X[i]) {
                        if (cnt[es[i].from()]++ > 0) return false;
                    }
                return true;
            }

            public int size() {
                return m;
            }
        };
//        GraphVis.output(bs);
        boolean[] res = MatroidIntersection.intersection(f1, f2);
        int cnt = 0;
        for (boolean b : res) if (b) cnt++;
        if (doCompare) {
            int exp = solveStupid(f1, f2);
            Assert.assertEquals(exp, cnt);
        }
    }

    private int solveStupid(Matroid f1, Matroid f2) {
        int m = f1.size();
        int res = 0;
        for (int i = 0; i < 1 << m; i++) {
            boolean[] bs = new boolean[m];
            for (int j = 0; j < m; j++) if ((i >> j & 1) == 1) bs[j] = true;
            if (f1.isIndependentSet(bs) && f2.isIndependentSet(bs)) {
                res = Math.max(res, Integer.bitCount(i));
            }
        }
        return res;
    }
}
