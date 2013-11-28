package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.TreeSet;

public class EllysFractions {
    public long getCount(int N) {
        long res = 0;
        TreeSet<Integer> primes = new TreeSet<Integer>();
        for (int i = 2; i <= N; i++) {
            int j = i;
            for (int k = 2; k * k <= j; k++) {
                if (j % k == 0) {
                    primes.add(k);
                    while (j % k == 0) j /= k;
                }
            }
            if (j > 1) primes.add(j);
            int num = primes.size();
            res += 1L << num - 1;
        }
        return res;
    }
}
