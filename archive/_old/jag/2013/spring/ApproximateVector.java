package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.algebra.Frac;
import net.ogiekako.algorithm.math.algebra.Irr;
import net.ogiekako.algorithm.math.algebra.Poly;
import net.ogiekako.algorithm.utils.interfaces.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApproximateVector {
    static double EPS = 1e-9;
    static int K = 20;// max degree
    int m;
    boolean log = false;

    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    static void log(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    Poly<Irr> make(MyScanner in) {
        int degree = in.nextInt();
        Irr[] a = new Irr[degree + 1];
//        double[] a = new double[degree + 1];
        for (int i = 0; i <= degree; i++) a[degree - i] = Irr.of(in.nextInt());
        Poly<Irr> res = new Poly<Irr>(a);
        return res;
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int m = in.nextInt();
        List<Poly<Irr>[]> v = new ArrayList<Poly<Irr>[]>();
        for (int i = 0; i < n; i++) {
            Poly<Irr>[] vi = new Poly[m];
            for (int j = 0; j < m; j++) vi[j] = make(in);
            v.add(vi);
        }
        log("v", v.toArray());
        Poly[] u = new Poly[m];
        for (int i = 0; i < m; i++) u[i] = make(in);
        log("u", u);
        String res = solve(v, u);
        out.println(res);
    }

    void sweep(Sys sys) {
        for (int i = 0; i < sys.u.length; ) {
            while (!allZero(sys.u[i].toArray()) && constZero(sys.u[i].toArray())) sys.shift(i);
            if (allZero(sys.u[i].toArray())) {
                sys.remove(i);
            } else i++;
        }
        int n = sys.u.length;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++) {
                if (compare(sys.u[i].toArray(), sys.u[j].toArray()) > 0) {
                    sys.swap(i, j);
                }
            }
        debug("D", sys.u);
        int[] pivot = new int[n];
        Arrays.fill(pivot, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                Poly<Irr>[] vj = sys.u[j].toArray();
                double mul = sys.u[i].toArray()[pivot[j]].v(0) / vj[pivot[j]].v(0);
                sys.sub(i, j, mul);
            }

            boolean zero = true;
            for (int k = 0; k < m; k++) {
                if (sys.u[i].toArray()[k].degree() >= 0 && !sys.u[i].toArray()[k].get(0).isZero()) {
                    zero = false;
                    pivot[i] = k;
                }
            }
            if (zero) {
                sweep(sys);
                return;
            }
        }
    }

    int compare(Poly[] o1, Poly[] o2) {
        int d1 = 0;
        for (Poly p : o1) d1 = Math.max(d1, p.degree());
        int d2 = 0;
        for (Poly p : o2) d2 = Math.max(d2, p.degree());
        return d1 - d2;
    }


    String solve(List<Poly<Irr>[]> v, Poly<Irr>[] u) {
        m = u.length;
        Vec[] v2 = new Vec[v.size()];
        for (int i = 0; i < v2.length; i++) v2[i] = new Vec(v.get(i));
        Sys sys = new Sys(v2);
        debug("A", sys.u);
        sweep(sys);
        debug("A2", sys.u);
        int size = sys.u.length;
        sys.add(new Vec(u));
        log = true;
        sweep(sys);
        if (sys.u.length <= size) return "Exact";

        sys = new Sys(v2);
        sweep(sys);
        sys.add(new Vec(u));
        for (int i = 0; i < sys.u.length; i++) {
            sys.u[i] = new Vec(subst0(sys.u[i].toArray()));
        }
        sweep(sys);
        if (sys.u.length <= size) return "Approximate";
        return "No";
    }

    private Poly<Irr>[] subst0(Poly<Irr>[] v) {
        Poly[] v0 = new Poly[v.length];
        for (int i = 0; i < v0.length; i++) {
            Irr[] a = new Irr[]{(Irr) v[i].get(0)};
            v0[i] = new Poly(a);
        }
        return v0;
    }

    void sweep(List<Poly[]> v) {
        Vec[] u = new Vec[v.size()];
        for (int i = 0; i < u.length; i++) u[i] = new Vec(v.get(i));
        Sys sys = new Sys(u);
        sweep(sys);
        v.clear();
        for (Vec vi : sys.u) v.add(vi.toArray());
    }

    private void shift(Poly[] v) {
        for (Poly f : v) f.shift();
    }

    private boolean constZero(Poly[] v) {
        for (Poly f : v) if (!f.isZero() && !f.get(0).isZero()) return false;
        return true;
    }

    private boolean allZero(Poly[] v) {
        for (Poly f : v) if (!f.isZero()) return false;
        return true;
    }

    class Sys {
        Vec[] v;
        Frac[][] A;
        Vec[] u;

        // u = Av
        Sys(Vec[] v) {
            this.v = v;
            this.u = v.clone();
            A = new Frac[u.length][v.length];
            for (int i = 0; i < u.length; i++) for (int j = 0; j < v.length; j++) A[i][j] = zero();
            for (int i = 0; i < u.length; i++) A[i][i] = Frac.one();
        }

        // u[i] -> u[i] - u[j] * d
        void sub(int i, int j, double mul) {
            debug("sub", i, j, mul);
            debug(u[i], u[j]);
            debug(this);
            u[i] = u[i].minus(u[j].times(mul));
            for (int k = 0; k < A[i].length; k++) {
                A[i][k] = A[i][k].minus(A[j][k].mul(new Frac(Poly.constant(mul), Poly.one())));
            }
        }

        void remove(int i) {
            if (log) {
                StringBuilder b = new StringBuilder();
                for (int k = 0; k < A[i].length; k++) {
                    b.append("(").append(A[i][k]).append(")").append(v[k]);
                    if (k < A[i].length - 1) b.append(" + ");
                }
                b.append(" = 0");
                log(b);
            }

            Vec[] nU = new Vec[u.length - 1];
            Frac[][] nA = new Frac[A.length - 1][];
            for (int j = 0; j < i; j++) {
                nU[j] = u[j];
                nA[j] = A[j];
            }
            for (int j = i + 1; j < u.length; j++) {
                nU[j - 1] = u[j];
                nA[j - 1] = A[j];
            }
            this.u = nU;
            this.A = nA;
            debug("remove", i, this);
        }

        void shift(int i) {
            u[i] = u[i].map(new Function<Poly, Poly>() {
                public Poly f(Poly a) {
                    return a.shifted();
                }
            }
            );
            for (int k = 0; k < A[i].length; k++) A[i][k] = A[i][k].div(Poly.x());
            debug("shift", i, this);
        }

        public void swap(int i, int j) {
            Vec tmp = u[i]; u[i] = u[j]; u[j] = tmp;
            Frac[] tmp2 = A[i]; A[i] = A[j]; A[j] = tmp2;
            debug("swap", i, j, this);
        }

        public void add(Vec v2) {
            Vec[] nV = new Vec[v.length + 1];
            Vec[] nU = new Vec[u.length + 1];
            Frac[][] nA = new Frac[nU.length][nV.length];
            for (int i = 0; i < nA.length; i++) Arrays.fill(nA[i], zero());
            for (int i = 0; i < v.length; i++) nV[i] = v[i];
            nV[v.length] = v2;
            for (int i = 0; i < u.length; i++) nU[i] = u[i];
            nU[u.length] = v2;
            for (int i = 0; i < u.length; i++) for (int j = 0; j < v.length; j++) nA[i][j] = A[i][j];
            nA[u.length][v.length] = Frac.one();
            this.v = nV;
            this.u = nU;
            this.A = nA;
            debug("add", this);
        }

        @Override
        public String toString() {
            StringBuilder b = new StringBuilder();
            b.append("sys = \n");
            for (int i = 0; i < u.length; i++) {
                b.append(toString(i)).append("\n");
            }
            return b.toString();
        }

        private String toString(int row) {
            StringBuilder res = new StringBuilder();
            for (int k = 0; k < A[row].length; k++) {
                if (k > 0) res.append(" + ");
                res.append("(" + A[row][k] + ")" + v[k]);
            }
            res.append(" = " + u[row]);
            return res.toString();
        }
    }

    public static Frac<Poly<Irr>> zero() {
        Poly<Irr> ins = new Poly<Irr>(new Irr[0]);
        return new Frac(ins.zero(), new Poly(new Irr[]{Irr.of(1)}));
    }
}

class Vec {
    Poly<Irr>[] vi;

    Vec(Poly[] vi) {
        this.vi = vi;
    }

    public Vec times(double d) {
        Poly<Irr>[] res = new Poly[vi.length];
        for (int i = 0; i < vi.length; i++) res[i] = vi[i].mul(Irr.of(d));
        return new Vec(res);
    }

    public Vec minus(Vec other) {
        Poly[] res = new Poly[vi.length];
        for (int i = 0; i < vi.length; i++) res[i] = vi[i].minus(other.vi[i]);
        return new Vec(res);
    }

    public Vec map(Function<Poly, Poly> function) {
        Poly[] res = new Poly[vi.length];
        for (int i = 0; i < res.length; i++) res[i] = function.f(vi[i]);
        return of(res);
    }

    private Vec of(Poly[] vi) {
        return new Vec(vi);
    }

    public Poly[] toArray() {
        return vi;
    }

    @Override
    public String toString() {
        return Arrays.toString(vi);
    }
}

