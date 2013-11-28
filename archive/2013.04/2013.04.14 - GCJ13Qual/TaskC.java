package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskC {
    static BigInteger[] answer;

    static {
        List<BigInteger> list = new ArrayList<BigInteger>();
        for (int n = 1; n <= 60; n++) {
            char[] cs = new char[n];
            if (n % 2 == 0) make(cs, 0, 0, list);
            else {
                for (int d = 0; d <= 3; d++) {
                    cs[n / 2] = (char) ('0' + d);
                    make(cs, 0, d * d, list);
                }
            }
        }
        answer = new BigInteger[list.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = list.get(i).multiply(list.get(i));
        }
        Arrays.sort(answer);
    }

    private static void make(char[] cs, int i, int sum, List<BigInteger> list) {
        if (sum >= 10) return;
        int n = cs.length;
        int j = n - 1 - i;
        if (!(i < j)) {
            list.add(new BigInteger(String.valueOf(cs)));
        } else {
            for (int d = 0; d <= 2; d++) {
                if (d == 0 && i == 0) continue;
                cs[i] = cs[j] = (char) ('0' + d);
                make(cs, i + 1, sum + d * d * 2, list);
            }
        }
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        BigInteger A = in.nextBigInteger(), B = in.nextBigInteger();
        int res = solve(B.add(BigInteger.ONE)) - solve(A);
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }

    private int solve(BigInteger A) {
        int p = Arrays.binarySearch(answer, A);
        if (p >= 0) return p;
        else return -p - 1;
    }
}
