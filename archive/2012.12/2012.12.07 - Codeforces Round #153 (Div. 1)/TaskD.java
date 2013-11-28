package tmp;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.linearAlgebra.LinearSystem;
import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

import java.io.PrintWriter;

public class TaskD {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int n = in.nextInt();
        long[] x = new long[n];
        for (int i = 0; i < n; i++) x[i] = in.nextLong();
        int L = 62;
        long xor = 0;
        for (long l : x) xor ^= l;
        debug("xor", Long.toBinaryString(xor));
        long[] matrix = new long[L + 2];
        int[] index = new int[L + 2];
        int rank = 0;
        for (int i = 0; i < n; i++) {
            matrix[rank] = x[i];
            index[rank] = i;
            if (LinearSystem.isIndependentFromHighest(matrix, rank, x[i])) {
                rank++;
                Matrix.rank(matrix, rank);
            }
        }
        for (int i = 0; i < rank; i++) matrix[i] = x[index[i]];
        debug("rank = ", rank);
        debug("matrix");
        for (int i = 0; i < rank; i++) {
            debug(Long.toBinaryString(matrix[i]));
        }
        debug("index =", index);
        debug();
        int[] order = new int[L];
        {
            int p = 0;
            for (int i = L - 1; i >= 0; i--) if ((xor >> i & 1) == 0) order[p++] = i;
            for (int i = L - 1; i >= 0; i--) if ((xor >> i & 1) == 1) order[p++] = i;
        }
        debug("order = ", order);
        long[] coefficients = LinearSystem.gauss(matrix, rank, order);
        debug("matrix(after gauss)");
        for (int i = 0; i < rank; i++) {
            debug(Long.toBinaryString(matrix[i]));
        }
        debug("coefficients");
        for (long c : coefficients) {
            debug(Long.toBinaryString(c));
        }
        boolean[] res = new boolean[n];
        long xor2 = 0;
        for (int i = 0, p = 0; i < rank; i++) {
            while ((matrix[i] >> order[p] & 1) == 0) p++;
            if ((xor2 >> order[p] & 1) == 0) {
                xor2 ^= matrix[i];
                for (int j = 0; j < rank; j++)
                    if ((coefficients[i] >> j & 1) == 1) {
                        res[index[j]] ^= true;
                    }
            }
        }
        for (int i = 0; i < n; i++) out.printf("%d%c", res[i] ? 2 : 1, i == n - 1 ? '\n' : ' ');
    }
    static void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    public static void swap(long[] array, int i, int j) {
        long tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
