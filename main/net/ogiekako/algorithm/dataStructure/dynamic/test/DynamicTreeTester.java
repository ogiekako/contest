package net.ogiekako.algorithm.dataStructure.dynamic.test;
import net.ogiekako.algorithm.dataStructure.dynamic.DynamicTree;
import net.ogiekako.algorithm.graph.*;

import java.util.Arrays;
import java.util.Random;
public class DynamicTreeTester {
    private static boolean log = false;
    public static void test(Generator gen) {
        Random rnd = new Random(14019284908L);
        int[] ns = {2, 3, 4, 5, 10, 50, 1000, 10000, 100000, 1};

        for (int Q : new int[]{100, 100000})
            for (int n : ns) {
                System.out.println("n = " + n);
                DynamicTree tree = gen.create(n);
                boolean check = n <= 50;
                int[] parent = new int[n];
                Arrays.fill(parent, -1);
                Graph forest = new BidirectionalGraph(n);
                int[] query = new int[Q];// link, cut, root
                int[] xs = new int[Q];
                int[] ys = new int[Q];
                for (int i = 0; i < Q; i++) {
                    query[i] = rnd.nextInt(3);
                    if (n == 1) query[i] = Math.max(query[i], 1);
                    if (query[i] == 1) xs[i] = rnd.nextInt(n);
                    else if (query[i] == 0) {
                        xs[i] = rnd.nextInt(n - 1) + 1;
                        ys[i] = rnd.nextInt(xs[i]);
                    } else {
                        xs[i] = rnd.nextInt(n);
                        ys[i] = rnd.nextInt(n);
                    }
                }
                for (int i = 0; i < Q; i++) {
                    int x = xs[i];
                    int y = ys[i];
                    if (query[i] == 0) {
                        if (parent[x] == -1) {// link
                            log("link", x, y);
                            parent[x] = y;
                            if (check) forest.add(new SimpleEdge(x, y));
                            tree.link(x, y);
                            if (tree.root(x) != tree.root(y)) throw new AssertionError("");
                        }
                    } else if (query[i] == 1) {// cut
                        if (parent[x] != -1) {
                            log("cut", x);
                            y = parent[x];
                            if (check) {
                                Edge rem = null;
                                for (Edge e : forest.edges(x)) {
                                    if (e.to() == parent[x]) rem = e;
                                }
                                if (rem == null) throw new AssertionError();
                                forest.remove(rem);
                            }
                            if (tree.root(x) != tree.root(y)) throw new AssertionError(x + " " + y);

                            tree.cut(x, y);
                            parent[x] = -1;

                            if (tree.root(x) == tree.root(y)) throw new AssertionError(x + " " + y);
                        }
                    } else {// root
                        log("root", x, y);
                        boolean res = tree.root(x) == tree.root(y);
                        if (check) {
                            boolean exp = GraphUtils.reachable(forest, x, y);
                            if (exp != res) throw new AssertionError(x + " " + y + " " + res);
                        }
                    }
                }
            }
    }
    private static void log(Object... os) {
        if (log) System.out.println(Arrays.deepToString(os));
    }
    public static void setLog(boolean log) {
        DynamicTreeTester.log = log;
    }
    public static interface Generator {
        DynamicTree create(int n);
    }
}
