package on2015_08.on2015_08_31_TopCoder_SRM__620.PerfectSquare;



import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.linearAlgebra.LinearSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class PerfectSquare {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    /**
     * x_i を選ぶか選ばないかとして制約を書き下すと、線形連立方程式になる。
     * (mod 2) これの解の総数を求めれば良い.
     */
    int  MOD = (int) (1e9+7);
    public int ways(int[] x) {
        List<int[]> constraints = new ArrayList<int[]>();
        int n = (int) Math.sqrt(x.length);
        for (int i = 0; i < n; i++) {
            int[] a = new int[n * n];
            for (int j = 0; j < n; j++) {
                a[j * n + i] = 1;
            }
            constraints.add(a);
            a = new int[n * n];
            for (int j = 0; j < n; j++) {
                a[i * n + j] = 1;
            }
            constraints.add(a);
        }
        TreeSet<Integer> primes = new TreeSet<Integer>();
        for (int i : x) {
            for (int p = 2; p * p <= i; p++) {
                if (i % p != 0) continue;
                primes.add(p);
                while (i % p == 0) i /= p;
            }
            if (i > 1) primes.add(i);
        }
        for (int p : primes) {
            int[] a = new int[n * n];
            for (int i = 0; i < n * n; i++) {
                int c = 0;
                for(int j=x[i];j%p==0;j/=p)c++;
                a[i] = c%2;
            }
            constraints.add(a);
        }
        int[][] A = new int[constraints.size()][];
        for (int i = 0; i < A.length; i++) {
            A[i] = constraints.get(i);
        }
        int[] b = new int[A.length];
        for (int i = 0; i < n * 2; i++) {
            b[i] = 1;
        }
        int[][] res = LinearSystem.solutionSpace(A, b, 2);
        if (res == null) {
            return 0;
        }
        return (int) MathUtils.powMod(2, res.length - 1, MOD);
    }
}
