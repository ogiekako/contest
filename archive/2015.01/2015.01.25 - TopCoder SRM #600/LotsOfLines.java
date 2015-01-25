package src;

import net.ogiekako.algorithm.math.MathUtils;

public class LotsOfLines {
    public long countDivisions(int A, int B) {
        // F = 2 + E - V
        //   = 2 + \sum (d_v/2 - 1)

        long F = 2;

        // Point v=(a,b) has d_v/2 lines that intersect with the point.
        // <->
        // Line v*: y=ax-b covers d_v lattice points such that 0 <= x < A && 0 <= y < B.

        // v: Point at infinity. Degree = A * B * 2.
        F += A * B - 1;

        // v*: Horizontal lines. Cover A points.
        F += B * (A - 1);

        for (long P = 1; P < A; P++)
            for (long Q = 1; Q < B; Q++)
                if (MathUtils.gcd(P, Q) == 1) {
                    // v*: lines with slope of Q/P or -Q/P.
                    F += count(A, B, P, Q) * 2;
                }

        return F;
    }

    private long count(long A, long B, long P, long Q) {
        long res = 0;

        // A line can cover at most d points.
        long d = Math.min((A - 1) / P, (B - 1) / Q) + 1;
        long x0 = A - P * (d - 1), y0 = B - Q * (d - 1);
        // lines that pass 0 <= x < x0 && 0 <= y < y0 cover d points.
        res += x0 * y0 * (d - 1);

        long x1 = x0 + P, y1 = y0 + Q;
        // lines that pass 0 <= x < x1 && 0 <= y < y1 cover at least d-1 points.
        if (d > 2) {
            res += (x1 * y1 - x0 * y0 - (x1 - P) * (y1 - Q)) * (d - 2);
        }

        // For each 2 <= i <= d-2, 2*P*Q lines cover exactly i points.
        // 2*P*Q * \sum [0 <= i < d-2] i = PQ(d-2)(d-3).
        if (d > 3) {
            res += P * Q * (d - 2) * (d - 3);
        }

        return res;
    }
}
