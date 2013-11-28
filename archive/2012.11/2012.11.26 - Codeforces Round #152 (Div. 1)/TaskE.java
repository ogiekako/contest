package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.math.BigInteger;

public class TaskE {
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger SIX = BigInteger.valueOf(6);

    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        BigInteger x1 = in.nextBigInteger().subtract(BigInteger.ONE), y1 = in.nextBigInteger().subtract(BigInteger.ONE), x2 = in.nextBigInteger(), y2 = in.nextBigInteger();
        BigInteger res = solve(x2, y2).subtract(solve(x2, y1)).subtract(solve(x1, y2)).add(solve(x1, y1));
        String s = res.toString();
        if (s.length() <= 10) out.println(s);
        else out.println("..." + s.substring(s.length() - 10));
    }

    private BigInteger solve(BigInteger x, BigInteger y) {
        if (x.compareTo(y) <= 0) {
            return
                    x.multiply(x).multiply(x.multiply(x).subtract(BigInteger.ONE)).divide(TWO)
                            .add(
                                    y.subtract(x).multiply(x).multiply(x.subtract(BigInteger.ONE)).divide(TWO)
                            ).add(
                            x.multiply(
                                    y.subtract(BigInteger.ONE).multiply(y).multiply(y.multiply(TWO).subtract(BigInteger.ONE)).divide(SIX)
                                            .subtract(
                                                    x.subtract(BigInteger.ONE).multiply(x).multiply(x.multiply(TWO).subtract(BigInteger.ONE)).divide(SIX)
                                            )
                            )
                    ).add(x.multiply(y));
        } else {
            return
                    y.multiply(y).multiply(y.multiply(y).subtract(BigInteger.ONE)).divide(TWO)
                            .subtract(
                                    x.subtract(y).multiply(y).multiply(y.add(BigInteger.ONE)).divide(TWO)
                            ).add(
                            y.multiply(
                                    x.add(BigInteger.ONE).multiply(x).multiply(x.multiply(TWO).add(BigInteger.ONE)).divide(SIX)
                                            .subtract(
                                                    y.add(BigInteger.ONE).multiply(y).multiply(y.multiply(TWO).add(BigInteger.ONE)).divide(SIX)
                                            )
                            )
                    ).add(x.multiply(y));
        }
    }
}
