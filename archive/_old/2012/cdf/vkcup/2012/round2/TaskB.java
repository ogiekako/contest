package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt(), k = in.nextInt(), h = in.nextInt();
        Entry[] es = new Entry[n];
        for (int i = 0; i < n; i++) es[i] = new Entry(i);
        for (int i = 0; i < n; i++) es[i].m = in.nextInt();
        for (int i = 0; i < n; i++) es[i].v = in.nextInt();
        solve(n, k, h, es, out);
    }
    public void solve(int n, int k, int h, Entry[] es, PrintWriter out) {
        double lb = 0, ub = Integer.MAX_VALUE;
        Arrays.sort(es);
        for (int i = 0; i < 200; i++) {
            double md = (lb + ub) / 2;
            int j = k;
            for (Entry e : es) {
                if ((long) j * h <= md * e.v) {
                    j--;
                    if (j == 0) break;
                }
            }
            if (j <= 0) {
                ub = md;
            } else {
                lb = md;
            }
        }
        int[] res = new int[k];
        int j = k;
        for (Entry e : es) {
            if ((long) j * h <= ub * e.v) {
                j--;
                res[j] = e.id;
                if (j == 0) break;
            }
        }
        for (int i = 0; i < k; i++) {
            if (i > 0) out.print(" ");
            out.print(res[i] + 1);
        }
        out.println();
    }
    static class Entry implements Comparable<Entry> {
        int m;
        int v;
        int id;

        Entry(int id) {
            this.id = id;
        }

        public int compareTo(Entry o) {
            if (m != o.m) return -(m - o.m);
            return -(v - o.v);
        }
    }

    public static void main(String[] args) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream("tmp.txt"));
            int k = 100000, n = 100000, h = 10000;
            Entry[] es = new Entry[n];
            for (int i = 0; i < n; i++) es[i] = new Entry(i);
            for (int i = 0; i < n; i++) {
                es[i].m = i + 1;
                es[i].v = 1000000000;
            }
            long start = System.currentTimeMillis();
            new TaskB().solve(n, k, h, es, pw);
            System.err.println(System.currentTimeMillis() - start);
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
