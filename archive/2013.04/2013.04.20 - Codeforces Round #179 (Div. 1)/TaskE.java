package src;


import net.ogiekako.algorithm.dataStructure.segmentTree.SegTreeSemigroup;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

// 9:51 -
public class TaskE {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] x = new int[n];
        Set<Integer> xSet = new TreeSet<Integer>();
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            xSet.add(x[i]);
        }
        int m = in.nextInt();
        int[] t = new int[m], l = new int[m], r = new int[m];
        int[] tmp = x.clone();
        for (int i = 0; i < m; i++) {
            t[i] = in.nextInt(); l[i] = in.nextInt(); r[i] = in.nextInt();
            if (t[i] == 1) {
                l[i]--;
                tmp[l[i]] += r[i];
                xSet.add(tmp[l[i]]);
            } else {
                r[i]++;
                xSet.add(l[i]);
                xSet.add(r[i]);
            }
        }
        int[] sortX = tois(xSet.toArray(new Integer[xSet.size()]));
        Arrays.sort(sortX);
        SegTreeSemigroup<Entry> list = new SegTreeSemigroup<Entry>(sortX.length) {

            @Override
            protected Entry operate(Entry left, Entry right) {
                if (left == null) return right;
                if (right == null) return left;
                int nNum = left.num + right.num;
                long nSum = left.sum + right.sum;
                long nProd = left.prod + right.prod + (right.sum * left.num - left.sum * right.num);
                return new Entry(nNum, nSum, nProd);
            }

            @Override
            protected Entry identity() {
                return null;
            }
        };
        for (int i = 0; i < n; i++) list.set(Arrays.binarySearch(sortX, x[i]), make(x[i]));
        for (int i = 0; i < m; i++) {
            if (t[i] == 1) {
                list.set(Arrays.binarySearch(sortX, x[l[i]]), null);
                x[l[i]] += r[i];
                list.set(Arrays.binarySearch(sortX, x[l[i]]), make(x[l[i]]));
            } else {
                Entry res = list.convolution(Arrays.binarySearch(sortX, l[i]), Arrays.binarySearch(sortX, r[i]));
                out.println(res.prod);
            }
        }
    }

    private Entry make(int x) {return new Entry(1, x, 0);}

    class Entry {
        int num;
        long sum;
        long prod;

        Entry(int num, long sum, long prod) {
            this.num = num; this.sum = sum; this.prod = prod;
        }
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }
}
