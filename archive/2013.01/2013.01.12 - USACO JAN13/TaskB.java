package tmp;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.TreeSet;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        int N = in.nextInt();
        int[] d = new int[N];
        char[] c = new char[N];
        int x = 0;
        set.add(x);
        for (int i = 0; i < N; i++) {
            d[i] = in.nextInt();
            c[i] = in.nextChar();
            if (c[i] == 'R') x += d[i];
            else x -= d[i];
            set.add(x);
        }
        int[] xs = tois(set.toArray(new Integer[set.size()]));
        int[] delta = new int[xs.length];
        x = 0;
        for (int i = 0; i < N; i++) {
            int y = x;
            if (c[i] == 'R') y += d[i];
            else y -= d[i];
            delta[Arrays.binarySearch(xs, Math.max(x, y))]--;
            delta[Arrays.binarySearch(xs, Math.min(x, y))]++;
            x = y;
        }
        int res = 0;
        for (int i = 0; i < delta.length - 1; i++) {
            if (delta[i] > 1) {
                res += xs[i + 1] - xs[i];
            }
            delta[i + 1] += delta[i];
        }
        out.println(res);
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }
}
