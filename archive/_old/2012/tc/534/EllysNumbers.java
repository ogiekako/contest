package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.StringUtils;

import java.util.ArrayList;
import java.util.TreeSet;

public class EllysNumbers {
    public long getSubsets(long n, String[] specials) {
        String special = StringUtils.concat(specials);
        int[] is = StringUtils.toIntArray(special, " ");
        TreeSet<Integer> candidates = new TreeSet<Integer>();
        for (int number : is) {
            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    candidates.add(i);
                    number /= i;
                    while (number % i == 0) {
                        number /= i;
                    }
                }
            }
            if (number > 1) {
                candidates.add(number);
            }
        }
        ArrayList<Integer> primeList = new ArrayList<Integer>();
        ArrayList<Integer> divideCounts = new ArrayList<Integer>();
        for (int prime : candidates) {
            if (n % prime == 0) {
                primeList.add(prime);
                int j = 0;
                while (n % prime == 0) {
                    j++;
                    n /= prime;
                }
                divideCounts.add(j);
            }
        }
        if (n > 1) return 0;
        int numPrime = primeList.size();
        ArrayList<Integer> masks = new ArrayList<Integer>();
        for (int i = 0; i < is.length; i++) {
            int number = is[i];
            boolean ok = true;
            int msk = 0;
            for (int j = 0; j < primeList.size(); j++) {
                int prime = primeList.get(j);
                if (number % prime == 0) {
                    int cnt = 0;
                    while (number % prime == 0) {
                        number /= prime;
                        cnt++;
                    }
                    if (cnt != divideCounts.get(j)) {
                        ok = false;
                    }
                    msk |= 1 << j;
                }
            }
            if (number == 1 && ok) {
                masks.add(msk);
            }
        }
        long[][] dp = new long[2][1 << numPrime];
        dp[0][0] = 1;
        int cur = 0, nxt = 1;
        for (int mask : masks) {
            System.arraycopy(dp[cur], 0, dp[nxt], 0, (1 << numPrime));
            for (int i = 0; i < 1 << numPrime; i++)
                if (dp[cur][i] > 0 && (i & mask) == 0) {
                    dp[nxt][i | mask] += dp[cur][i];
                }
            int tmp = cur; cur = nxt; nxt = tmp;
        }
        return dp[cur][(1 << numPrime) - 1];
    }


}

