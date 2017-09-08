package on2017_08.on2017_08_26_AGC019.TaskC;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.utils.Cast;

import java.util.*;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int x1 = in.nextInt(), y1 = in.nextInt(), x2 = in.nextInt(), y2 = in.nextInt();
        int N = in.nextInt();
        int[] X = new int[N], Y = new int[N];
        for (int i = 0; i < N; i++) {
            X[i] = in.nextInt();
            Y[i] = in.nextInt();
        }
        if (x1 > x2) {
            x1 = -x1;
            x2 = -x2;
            for (int i = 0; i < N; i++) {
                X[i] = -X[i];
            }
        }
        if (y1 > y2) {
            y1 = -y1;
            y2 = -y2;
            for (int i = 0; i < N; i++) {
                Y[i] = -Y[i];
            }
        }
        int L = x2 - x1;
        int R = y2 - y1;
        TreeSet<Integer> xSet = new TreeSet<>();
        TreeSet<Integer> ySet = new TreeSet<>();
        xSet.add(x1);
        xSet.add(x2);
        ySet.add(y1);
        ySet.add(y2);
        for (int i = 0; i < X.length; i++) {
            if (x1 <= X[i] && X[i] <= x2 && y1 <= Y[i] && Y[i] <= y2) {
                xSet.add(X[i]);
                ySet.add(Y[i]);
            }
        }
        int[] sortedX = Cast.toInt(xSet);
        int[] sortedY = Cast.toInt(ySet);
        x1 = Arrays.binarySearch(sortedX, x1);
        x2 = Arrays.binarySearch(sortedX, x2);
        y1 = Arrays.binarySearch(sortedY, y1);
        y2 = Arrays.binarySearch(sortedY, y2);

        List<E> es = new ArrayList<>();
        for (int i = 0; i < X.length; i++) {
            if (xSet.contains(X[i]) && ySet.contains(Y[i])) {
                E e = new E();
                e.x = Arrays.binarySearch(sortedX, X[i]);
                e.y = Arrays.binarySearch(sortedY, Y[i]);
                es.add(e);
            }
        }
        Collections.sort(es);
        int[] left = new int[N + 10];
        Arrays.fill(left, N + 10);
        left[0] = 0;
        for (E e : es) {
            int i = Arrays.binarySearch(left, e.y);
            if (i >= 0) {
                left[i + 1] = left[i];
            } else {
                left[-i - 1] = e.y;
            }
        }
        int res = 0;
        for (int i = 0; i < N + 10; i++) {
            if (left[i] <= y2) {
                res = i;
            }
        }
        double len = 100.0 * (L + R);
        len += (-20 + 5 * Math.PI) * res;
        if (L + 1 == res || R + 1 == res) {
            len += 5 * Math.PI;
        }
        out.printFormat("%.15f", len);
    }

    class E implements Comparable<E> {
        int x, y;

        @Override
        public int compareTo(E o) {
            return x - o.x;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}
