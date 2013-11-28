package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Random;

public class KUPC_E {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int[] point = new int[N];
        for (int i = 0; i < N; i++) {
            String s = in.next();
            for (char c : s.toCharArray()) {
                point[i] += c == 'x' ? 0 : c == 'o' ? 3 : 1;
            }
        }
        int sum = 0;
        for (int p : point) sum += p;
        Random rnd = new Random();
        int[] ops = new int[N];
        for (int i = 0; i < 1000; i++) {
            int val = rnd.nextInt(i + 1);
            int cur = 0;
            for (int j = 0; j < N; j++) {
                cur += ops[j];
                if (val <= cur) {
                    out.println(j + 1);
                    out.flush(); break;
                }
            }
            int op = in.nextInt() - 1;
            ops[op]++;
        }
    }
}
