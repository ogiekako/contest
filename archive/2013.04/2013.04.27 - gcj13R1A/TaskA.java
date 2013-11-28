package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.math.BigInteger;

public class TaskA {
    private MyScanner in;
    private MyPrintWriter out;
    private BigInteger TWO = BigInteger.valueOf(2);

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        this.in = in; this.out = out;
        if (testNumber == 1) prepare();
        out.printFormat("Case #%d: ", testNumber);
        System.err.println("case " + testNumber);
        solve();
    }

    private void prepare() {

    }

    private void solve() {
        BigInteger r = in.nextBigInteger(), t = in.nextBigInteger();
        BigInteger left = BigInteger.ONE, right = BigInteger.valueOf(Long.MAX_VALUE);
        do {
            BigInteger d = left.add(right).divide(TWO);
            BigInteger need = d.multiply(TWO.multiply(r).add(TWO.multiply(d)).subtract(BigInteger.ONE));
            if (need.compareTo(t) <= 0) left = d;
            else right = d;
        } while (right.subtract(left).compareTo(BigInteger.ONE) > 0);
        out.println(left);
    }

}
