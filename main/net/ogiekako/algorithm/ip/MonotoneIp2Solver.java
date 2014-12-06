package net.ogiekako.algorithm.ip;

import net.ogiekako.algorithm.graph.Graph;
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
    IntToDouble[] w;
    List<Constraint> constraints = new ArrayList<>();

    /**
     * The initial objective function is:
     * sum w_i(x_i)
     * and the initial constraints are:
     * 0 \leq x_i \leq u_i.
     * <p/>
     * w_i can be any function.
     */
    public MonotoneIp2Solver(long[] u, IntToDouble[] w) {
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
     * @param a >= 0
     * @param b >= 0
     * @param d is 0 or 1
     * @param e must be a convex function.
     */
    public void addConstraint(int i, int j, double a, double b, double c, int d, long g, IntToDouble e) {
        constraints.add(new Constraint(i, j, a, b, c, d, g, e));
    }

    /**
     * @return Double.POSITIVE_INFINITY if unsatisfiable, Double.NEGATIVE_INFINITY if unbounded or
     * minimum value otherwise.
     */
    public double solve() {
        long U = 0;
        for (long v : u) U = Math.max(U, v);
        if (U <= 1) {
            return solveMinCostFlow();
        } else {
            return solveMaxFlow();
        }
    }

    private double solveMaxFlow() {
        int n = u.length;
        int numVertex = 0;
        int source = numVertex++;
        int sink = numVertex++;
        int[][] x = new int[n][];
        for (int i = 0; i < n; i++) {
            if (u[i] > Integer.MAX_VALUE) throw new AssertionError();
            for (int j = 0; j < u[i]; j++) {
                x[i][j] = numVertex++;
            }
        }
        double res = 0;
        Graph graph = new Graph(numVertex);
        for (int i = 0; i < n; i++) {
            double[] vs = new double[(int) u[i]];
            double min = 0;
            for (int j = 0; j <= u[i]; j++) {
                vs[j] = w[i].f(j);
                min = Math.min(min, vs[j]);
            }
            res += min;
            for (int j = 0; j <= u[i]; j++) {
                double whenXIsJ = vs[j] - min;
                if (j == 0) {
//                    graph.addFlow(source, x[i][j], whenXIsJ);
                } else if (j == u[i]) {

                } else {

                }
            }
        }
        for (Constraint c : constraints) {
            for (int i = 0; i <= u[c.i]; i++) {
                for (int j = 0; j <= u[c.j]; j++) {

                }
            }
        }
        return 0;
    }

    private double solveMinCostFlow() {
        return 0;
    }

    public static interface IntToDouble extends Function<Integer, Double> {
    }

    private static class Constraint {
        int i, j;
        double a, b, c;
        int d;
        long g;
        IntToDouble e;

        public Constraint(int i, int j, double a, double b, double c, int d, long g, IntToDouble e) {
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

    public static class Linear implements IntToDouble {
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
        public Double f(Integer x) {
            return a * x + b;
        }
    }
}
