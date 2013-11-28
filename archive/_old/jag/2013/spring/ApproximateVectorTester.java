package src;

import net.ogiekako.algorithm.math.algebra.Irr;
import net.ogiekako.algorithm.math.algebra.Poly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ApproximateVectorTester {
    Random rnd = new Random(12082380L);
    static boolean OUTPUT = false;
    static int V = 100;// max value
    static int maxN = 20;
    static int maxM = 20;
    static int maxDeg = 20;

    enum Sol {
        Exact,
        Approximate,
        No
    }

    void gen() {
        debug("exact");
        for (int i = 0; i < 5; i++) random(Sol.Exact);
        for (int i = 0; i < 5; i++) random(Sol.Approximate);
        for (int i = 0; i < 5; i++) random(Sol.No);


//        for (int iter = 0; iter < 10; iter++) {
//            log("iter", iter);
//            int n = 20, D = 10, K = 20;
//            List<Poly[]> v = new ArrayList<Poly[]>();
//            Poly[] u;
//            for (int i = 0; i < n; i++) {
//                int d = rnd.nextInt(K) + 1;
//                v.add(genVector(rnd, D, d));
//            }
//            u = genVector(rnd, D, K);
//            ApproximateVector instance = new ApproximateVector();
//            debug(v.toArray());
//            debug(u);
//            String res = instance.solve(v, u);
//            log(res);
//        }
    }

    private void random(Sol sol) {
        for (; ; ) {
            Problem p = random();
            if (p.res() == sol) {
                log(p.n < p.m, p.n > p.m);
                p.output();
                break;
            }
        }
    }

    private Problem random() {
        int n = rnd.nextInt(maxN) + 1;
        int m = rnd.nextInt(maxM) + 1;
        int[][][] v = new int[n][m][];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int d = rnd.nextInt(maxDeg + 1);
                v[i][j] = new int[d + 1];
                for (int k = 0; k <= d; k++) {
                    v[i][j][k] = rnd.nextInt(V * 2 + 1) - V;
                    if (k == d && v[i][j][k] == 0) {
                        k--;
                    }
                }
            }
        }
        int[][] u = new int[m][];
        for (int j = 0; j < m; j++) {
            int d = rnd.nextInt(maxDeg + 1);
            u[j] = new int[d + 1];
            for (int k = 0; k <= d; k++) {
                u[j][k] = rnd.nextInt(V * 2 + 1) - V;
                if (k == d && u[j][k] == 0) {
                    k--;
                }
            }
        }
        return Problem.of(v, u);
    }

    public static void main(String[] args) {
        new ApproximateVectorTester().gen();
    }

    static boolean DEBUG = false;

    static void debug(Object... os) {
        if (DEBUG) System.err.println(Arrays.deepToString(os));
    }

    static void log(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private static Poly<Irr>[] genVector(Random rnd, int D, int K) {
        Poly<Irr>[] res = new Poly[D];
        for (int j = 0; j < D; j++) {
            Irr[] a = new Irr[K];
            for (int k = 0; k <= K; k++) {
                a[k] = Irr.of(rnd.nextInt(2 * V + 1) - V);
            }
            res[j] = new Poly(a);
        }
        return res;
    }
}

class Problem {
    int n, m;
    int[][][] v;
    int[][] u;

    public static Problem of(int[][][] v, int[][] u) {
        Problem res = new Problem();
        res.n = v.length;
        res.m = v[0].length;
        res.v = v;
        res.u = u;
        return res;
    }

    public ApproximateVectorTester.Sol res() {
        List<Poly<Irr>[]> _v = new ArrayList<Poly<Irr>[]>();
        for (int i = 0; i < v.length; i++) {
            _v.add(vec(v[i]));
        }
        String res = new ApproximateVector().solve(_v, vec(u));
        return res.charAt(0) == 'E' ? ApproximateVectorTester.Sol.Exact : res.charAt(0) == 'A' ? ApproximateVectorTester.Sol.Approximate : ApproximateVectorTester.Sol.No;
    }

    private Poly[] vec(int[][] u) {
        Poly[] res = new Poly[u.length];
        for (int i = 0; i < res.length; i++) {
//            res[i] = new Poly();
//            res[i].degree = u[i].length - 1;
            Irr[] a = new Irr[u[i].length];
            for (int j = 0; j < u[i].length; j++) a[j] = Irr.of(u[i][j]);
            res[i] = new Poly(a);
        }
        return res;
    }

    public void output() {
        if (!ApproximateVectorTester.OUTPUT) {
            System.out.print("."); return;
        }
        int n = v.length, m = v[0].length;
        System.out.printf("%d %d\n", n, m);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(v[i][j].length - 1);
                for (int k = 0; k < v[i][j].length; k++) {
                    System.out.printf(" %d", v[i][j][k]);
                }
                System.out.println();
            }
        }
        for (int j = 0; j < m; j++) {
            System.out.print(u[j].length - 1);
            for (int k = 0; k < u[j].length; k++) {
                System.out.printf(" %d", u[j][k]);
            }
            System.out.println();
        }
    }
}
