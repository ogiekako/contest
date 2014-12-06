package net.ogiekako.algorithm.ip;

import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.graph.algorithm.MaxFlow;
import net.ogiekako.algorithm.utils.interfaces.Function;

import java.util.ArrayList;
import java.util.List;

/**
 * Solve the following optimization problem.
 * <p/>
 * Min  sum w_i(x_i) + sum e_{ij}z_{ij}
 * s.t. a_{ij}x_i - b_{ij}x_j \leq c_{ij} + d_{ij}z_{ij}
 * 0 \leq x_i \leq u_i
 * 0 \leq z_{ij} \leq g_{ij}
 * <p/>
 * where,
 * w_i : arbitrary function
 * e_{ij} : convex function
 * a,b,c,d,u,g \geq 0
 * d_{ij} = 0 or 1.
 */
public class MonotoneIp2Solver {

    long[] u;
    LongToDouble[] w;
    List<Constraint> constraints = new ArrayList<>();

    /**
     * The initial objective function is:
     * sum w_i(x_i)
     * and the initial constraints are:
     * 0 \leq x_i \leq u_i.
     * <p/>
     * w_i can be any function.
     */
    public MonotoneIp2Solver(long[] u, LongToDouble[] w) {
        this.w = w;
        this.u = u;
    }

    /**
     * Add constraints:
     * ax_i - bx_j \leq c + dz_{ij}
     * 0 \leq z_{ij} \leq g
     * and add
     * e(z_{ij})
     * to the objective function.
     *
     * @param i,j indices. For d=1, The same (i,j) pair must not appear twice.
     * @param a >= 0
     * @param b >= 0
     * @param d is 0 or 1
     * @param e must be a convex function. Can be null if d = 0.
     */
    public void addConstraint(int i, int j, double a, double b, double c, int d, long g, LongToDouble e) {
        if (d == 0) {
            g = 0;
            d = 1;
            e = new Linear(0);
        }
        constraints.add(new Constraint(i, j, a, b, c, d, g, e));
    }

    /**
     * @return Double.POSITIVE_INFINITY if unsatisfiable, Double.NEGATIVE_INFINITY if unbounded or
     * minimum value otherwise.
     */
    public double solve() {
        if (isBinary()) {
            // TODO(oka): Add min cost flow logic.
//            return solveMinCostFlow();
        }
        return solveMaxFlow();
    }

    private double solveMaxFlow() {
        int n = u.length;
        int numVertex = 0;
        int source = numVertex++;
        int sink = numVertex++;
        int[][] x = new int[n][];
        for (int i = 0; i < n; i++) {
            x[i] = new int[(int) u[i]];
            if (u[i] > Integer.MAX_VALUE) throw new AssertionError();
            for (int j = 0; j < u[i]; j++) {
                x[i][j] = numVertex++;
            }
        }
        double res = 0;
        Graph graph = new Graph(numVertex);
        for (int i = 0; i < n; i++) {
            double[] vs = new double[(int) u[i] + 1];
            double min = 0;
            for (int j = 0; j <= u[i]; j++) {
                vs[j] = w[i].f((long) j);
                min = Math.min(min, vs[j]);
            }
            res += min;
            for (int j = 0; j <= u[i]; j++) {
                double whenXIsJ = vs[j] - min;
                if (j == 0) {
                    graph.addFlow(source, x[i][j], whenXIsJ);
                } else if (j == u[i]) {
                    graph.addFlow(x[i][j - 1], sink, whenXIsJ);
                } else {
                    graph.addFlow(x[i][j - 1], x[i][j], whenXIsJ);
                }
            }
        }
        for (Constraint ct : constraints) {
            double a = ct.a;
            double b = ct.b;
            double c = ct.c;

            long argMinZ = 0;
            double minE = Double.POSITIVE_INFINITY;
            // O(g)
            // This can be done in O(log g) time using ternary search.
            for (long z = 0; z <= ct.g; z++) {
                double v = ct.e.f(z);
                if (minE > v) {
                    minE = v;
                    argMinZ = z;
                }
            }
            res += minE;

            int u1 = (int) u[ct.i];
            int u2 = (int) u[ct.j];
            double[][] penalty = new double[u1 + 1][u2 + 1];
            // O(U^2)
            for (int k = 0; k <= u1; k++) {// x = k
                for (int l = 0; l <= u2; l++) {// y = l
                    long minimumZ = (long) Math.ceil(Math.max(0, a * k - b * l - c) - 1e-9);
                    if (minimumZ > ct.g) {
                        penalty[k][l] = Double.POSITIVE_INFINITY;
                    } else {
                        if (minimumZ < argMinZ) {
                            penalty[k][l] = 0;
                        } else {
                            penalty[k][l] = ct.e.f(minimumZ) - minE;
                        }
                    }
                }
            }
            for (int k = 0; k <= u1; k++) {
                for (int l = u2; l >= 0; l--) {
                    double cut = penalty[k][l];
                    if (k > 0) cut -= penalty[k - 1][l];
                    if (l < u2) cut -= penalty[k][l + 1];
                    if (k > 0 && l < u2) cut += penalty[k - 1][l + 1];
                    if (cut > 1e-9) {
                        int from, to;
                        if (k == 0) from = source;
                        else from = x[ct.i][k - 1];
                        if (l == u2) to = sink;
                        else to = x[ct.j][l];
                        graph.addFlow(from, to, cut);
                    }
                }
            }
        }
        return MaxFlow.maxFlow(graph, source, sink) + res;
    }

    private double solveMinCostFlow() {
        return 0;
    }

    public boolean isBinary() {
        for (Constraint c : constraints) {
            if (c.a != 1 || c.b != 1) return false;
        }
        return true;
    }

    public static interface LongToDouble extends Function<Long, Double> {
        Double f(Long x);
    }
    
    private static class Constraint {
        int i, j;
        double a, b, c;
        int d;
        long g;
        LongToDouble e;

        public Constraint(int i, int j, double a, double b, double c, int d, long g, LongToDouble e) {
            this.i = i;
            this.j = j;
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
            this.g = g;
            this.e = e;
        }
    }

    public static class Linear implements LongToDouble {
        double a, b;

        /**
         * ax + b
         */
        public Linear(double a, double b) {
            this.a = a;
            this.b = b;
        }

        /**
         * ax
         */
        public Linear(double a) {
            this(a, 0);
        }

        @Override
        public Double f(Long x) {
            return a * x + b;
        }
    }
}

/**
 * f(x) = ax^2 + bx + c
 * f^2(x) = f(f(x)).
 * f^4(x) = f^2(f^2(x)).
 *
 */