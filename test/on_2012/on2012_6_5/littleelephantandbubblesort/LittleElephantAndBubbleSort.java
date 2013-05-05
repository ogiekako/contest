package on_2012.on2012_6_5.littleelephantandbubblesort;


import net.ogiekako.algorithm.dataStructure.BIT_double;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.TreeSet;

public class LittleElephantAndBubbleSort {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        int d = in.nextInt();
        int[] B = new int[N];
        double[] P = new double[N];
        for (int i = 0; i < N; i++) B[i] = in.nextInt();
        for (int i = 0; i < N; i++) P[i] = (double) in.nextInt() / 100;
        solve(N, d, B, P, out);
    }

    private void solve(int N, int d, int[] B, double[] P, PrintWriter out) {
        TreeSet<Integer> set = new TreeSet<Integer>();
        for (int b : B) set.add(b);
        for (int b : B) set.add(b + d);
        int[] sorted = tois(set.toArray(new Integer[0]));
        BIT_double bit = new BIT_double(sorted.length);
        double res = 0;
        for (int i = N - 1; i >= 0; i--) {
            res += bit.sum(0, Arrays.binarySearch(sorted, B[i])) * (1 - P[i]);
            res += bit.sum(0, Arrays.binarySearch(sorted, B[i] + d)) * P[i];
            bit.add(Arrays.binarySearch(sorted, B[i]), 1 - P[i]);
            bit.add(Arrays.binarySearch(sorted, B[i] + d), P[i]);
        }
        out.printf("%.04f\n", res);
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }
}
