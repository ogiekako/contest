package tmp;

// Paste me into the FileEdit configuration dialog

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class GuessTheSubstring {

    public double solve(String[] pieces) {
        int M = 0;
        Random rnd = new Random();
        while (M < 1000000000 || !BigInteger.valueOf(M).isProbablePrime(10)) M = rnd.nextInt();
        StringBuilder b = new StringBuilder();
        for (String s : pieces) b.append(s);
        String s = b.toString();
        int N = s.length();
        int tot = N * (N + 1) / 2;
        long[] is = new long[tot];
        tot = 0;
        for (int i = 0; i < N; i++) {
            long cur = 0;
            for (int j = i; j < N; j++) {
                cur *= M;
                cur += s.charAt(j);
                is[tot++] = cur;
            }
        }
        Arrays.sort(is);

        freq = new int[N * (N + 1) / 2 + 1];
        int size = 0;
        for (int i = 0; i < is.length; ) {
            int j = i;
            while (j < is.length && is[i] == is[j]) j++;
            freq[j - i]++;
            size++;
            i = j;
        }
        int res = 0;
        for (int i = 0; i < size - 1; i++) {
            int v = get() + get();
            res += v;
            set(v);
        }
        return (double) res / (N * (N + 1) / 2);
    }

    int[] freq;
    int at;

    int get() {
        while (freq[at] == 0) at++;
        freq[at]--;
        return at;
    }

    void set(int idx) {
        freq[idx]++;
        at = Math.min(at, idx);
    }

}

