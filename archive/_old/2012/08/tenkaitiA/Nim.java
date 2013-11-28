package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;
import net.ogiekako.algorithm.math.PowerOperation;
import net.ogiekako.algorithm.math.Transform;

public class Nim {
    int T = 65536;
    int MOD = (int) (1e9 + 7);

    public int count(int K, int L) {
        boolean[] isPrime = MathUtils.generatePrimaryTable(L + 1);
        int[] x = new int[T];
        for (int i = 0; i < isPrime.length; i++) if (isPrime[i]) x[i] = 1;
        int[] z = Transform.hadamardTransform(x, MOD);
        z = new PowerOperation<int[]>() {
            @Override
            protected int[] associativeOperation(int[] a, int[] b) {
                int[] res = new int[a.length];
                for (int i = 0; i < a.length; i++) {
                    res[i] = (int) ((long) a[i] * b[i] % MOD);
                }
                return res;
            }
        }.power(z, K);
        x = Transform.hadamardTransform(z, MOD);
        return (int) (x[0] * MathUtils.inverse(T, MOD) % MOD);
    }

}

