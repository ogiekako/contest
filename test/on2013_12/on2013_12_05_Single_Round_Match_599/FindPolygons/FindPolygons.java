package on2013_12.on2013_12_05_Single_Round_Match_599.FindPolygons;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class FindPolygons {
    public double minimumPolygon(int L) {
        if (L < 4) return -1;
        if (L % 2 == 1) return -1;
        int res = Integer.MAX_VALUE;
        for (int x = 1; 2 * x < L; x++) {
            int y = (L - 2 * x) / 2;
            res = Math.min(res, Math.abs(x - y));
        }
        int res3 = Integer.MAX_VALUE;
        List<P>[] ps = new ArrayList[L / 2 + 1];
        for (int i = 0; i < ps.length; i++) ps[i] = new ArrayList<P>();
        for (int x = 0; x <= L / 2; x++)
            for (int y = 0; y <= L / 2; y++) {
                if (x == 0 && y == 0) continue;
                double d = Math.sqrt(x * x + y * y);
                if (Math.abs(d - Math.round(d)) < 1e-9) {
                    int dd = (int) Math.round(d);
                    if (dd * 2 >= L) continue;
                    for (int d1 = -1; d1 <= 1; d1 += 2)
                        for (int d2 = -1; d2 <= 1; d2 += 2)
                            ps[dd].add(new P(x * d1, y * d2, dd));
                }
            }
        for (int d1 = 0; d1 < ps.length; d1++)
            for (int d2 = d1; d2 < ps.length; d2++) {
                int d3 = L - d1 - d2;
                if (d3 < d2 || d3 >= ps.length || d3 <= 0) continue;

                for (P p : ps[d1]) {
                    if (p.x < 0 || p.y < 0 || p.x < p.y) continue;
                    for (P q : ps[d2]) {
                        int X = p.x + q.x;
                        int Y = p.y + q.y;
                        int D = L - p.d - q.d;
                        if (
                                D > 0
                                        &&
                                        X * X + Y * Y == D * D
                                        && D != p.d + q.d
                                ) {
                            res3 = Math.min(res3, max(p.d, q.d, D) - min(p.d, q.d, D));
                        }
                    }
                }
            }
        if (res3 == Integer.MAX_VALUE) return res;
        debug(L,res3);
        return res3;
    }
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
    private int max(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }
    private int min(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }
    class P {
        int x, y, d;
        public P(int x, int y, int d) {
            this.x = x; this.y = y; this.d = d;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            P p = (P) o;

            if (d != p.d) return false;
            if (x != p.x) return false;
            if (y != p.y) return false;

            return true;
        }
        int hash = -1;
        @Override
        public int hashCode() {
            if (hash != -1) return hash;
            int result = x;
            result = 31 * result + y;
            result = 31 * result + d;
            return hash = result;
        }
    }
    public static void main(String[] args) {
//        double[] res = new double[5001];
        Random rnd = new Random(152901248L);
        for (int L = 0; L <= 5000; L++) {
            int LL = rnd.nextInt(2500) * 2 + 2;
            double res = new FindPolygons().minimumPolygon(LL);
//            double exp = new FindPolygons().solve(LL);
//            if (res != exp) throw new AssertionError(LL + " " + exp + " " + res);

        }
    }
    private double solve(int L) {
        debug("solve" , L);
        if (L < 4) return -1;
        if (L % 2 == 1) return -1;
        int res = Integer.MAX_VALUE;
        for (int x = 1; 2 * x < L; x++) {
            int y = (L - 2 * x) / 2;
            res = Math.min(res, Math.abs(x - y));
        }
        int res3 = Integer.MAX_VALUE;
        List<P>[] ps = new ArrayList[L+1];
        for (int i = 0; i < ps.length; i++) ps[i] = new ArrayList<P>();
        for (int x = 0; x <= L; x++)
            for (int y = 0; y <= L; y++) {
                if (x == 0 && y == 0) continue;
                double d = Math.sqrt(x * x + y * y);
                if (Math.abs(d - Math.round(d)) < 1e-9) {
                    int dd = (int) Math.round(d);
                    if (dd >= L) continue;
                    for (int d1 = -1; d1 <= 1; d1 += 2)
                        for (int d2 = -1; d2 <= 1; d2 += 2)
                            ps[dd].add(new P(x * d1, y * d2, dd));
                }
            }
        for (int d1 = 0; d1 < ps.length; d1++)
            for (int d2 = d1; d2 < ps.length; d2++) {
                int d3 = L - d1 - d2;
                if(d3 < d2)continue;
//                if (d3 <= d2 || d3 >= ps.length || d3 < 0) continue;

                for (P p : ps[d1]) {
//                    if (p.x < 0 || p.y < 0 || p.x < p.y) continue;
                    for (P q : ps[d2]) {
                        int X = p.x + q.x;
                        int Y = p.y + q.y;
                        int D = L - p.d - q.d;
                        if (
                                D > 0
                                        &&
                                        X * X + Y * Y == D * D
                                        && D != p.d + q.d
                                ) {
                            res3 = Math.min(res3, max(p.d, q.d, D) - min(p.d, q.d, D));
                            debug(p.x,p.y,p.d, q.x,q.y,q.d, X,Y,D);
                        }
                    }
                }
            }
        if (res3 == Integer.MAX_VALUE) return res;
        debug(res3);
        return res3;
    }
}
