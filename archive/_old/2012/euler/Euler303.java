package tmp;

import net.ogiekako.algorithm.utils.BigInt;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Euler303 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        int N = in.nextInt();
        long res = 0;
        for (int n = 1; n <= N; n++) {
            BigInteger BN = BigInteger.valueOf(n);
            boolean[] visited = new boolean[n];
            Queue<BigInteger> que = new LinkedList<BigInteger>();
            que.offer(BigInt.ONE); que.offer(BigInt.TWO);
            for (; ; ) {
                BigInteger cur = que.poll();
                int md = cur.mod(BN).intValue();
                if (visited[md]) continue;
                visited[md] = true;
                if (md == 0) {
                    res += cur.divide(BN).longValue();
                    break;
                }
                for (int i = 0; i < 3; i++) que.offer(cur.multiply(BigInt.TEN).add(BigInteger.valueOf(i)));
            }
        }
        out.println(res);
    }
}
