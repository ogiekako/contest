package src;

public class BigFatInteger {
    public int minOperations(int A, int B) {
        int res = 0;
        int max = 0;
        for (int p = 2; p <= A; p++) {
            if (A % p == 0) {
                res++;
                int c = 0;
                while (A % p == 0) {
                    A /= p;
                    c++;
                }
                c *= B;
                max = Math.max(max, 32 - Integer.numberOfLeadingZeros(c - 1));
            }
        }
        return res + max;
    }
}
