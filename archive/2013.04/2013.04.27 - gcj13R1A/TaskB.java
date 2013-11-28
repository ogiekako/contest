package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    private MyScanner in;
    private MyPrintWriter out;

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        this.in = in;
        this.out = out;
        if (testNumber == 1) prepare();
        out.printFormat("Case #%d: ", testNumber);
        System.err.println("case " + testNumber);
        solve();
    }

    private void prepare() {

    }

    private void solve() {
        int E = in.nextInt(), R = in.nextInt(), N = in.nextInt();
        int[] v = new int[N];
        for (int i = 0; i < N; i++) v[i] = in.nextInt();
//        System.err.println(E+" "+R+" "+N);
//        System.err.println(Arrays.toString(v));
        int[] next = new int[N];
        for (int i = 0; i < N; i++) {
            next[i] = Integer.MAX_VALUE;
            for (int j = i + 1; j < N; j++) {
                if (v[i] < v[j]) {
                    next[i] = j;
                    break;
                }
            }
        }
        int have = E;
        long res = 0;
        for (int i = 0; i < N; i++) {
            if (next[i] == Integer.MAX_VALUE) {
                res += (long) have * v[i];
                have = 0;
            } else {
                long to = Math.max(0, E - (long) (next[i] - i) * R);
                if (have < to) {
//                    throw new AssertionError();
                    to = have;
                }
                long use = have - to;
                res += use * v[i];
                have = (int) to;
            }
            have += R;
            have = Math.min(have, E);
//            if(have > E) throw new AssertionError();
        }
        out.println(res);
    }

}
