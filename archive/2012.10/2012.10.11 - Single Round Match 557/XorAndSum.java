package tmp;

import java.util.Arrays;

public class XorAndSum {
    public long maxSum(long[] number) {
        Arrays.sort(number);
        rank(number);
        int n = number.length;
        for (int i = 1; i < n; i++) number[0] ^= number[i];
        for (int i = 1; i < n; i++) number[i] ^= number[0];
        long res = 0;
        for (long i : number) res += i;
        return res;
    }

    public static int rank(long[] bitVector) {
        int n = bitVector.length;
        int mx = 64;
        int rank = 0;
        for (int i = mx - 1; i >= 0 && rank < n; i--) {
            for (int j = rank; j < n; j++) {
                if ((bitVector[j] >>> i & 1) == 1) {
                    swap(bitVector, rank, j);
                    break;
                }
            }
            if ((bitVector[rank] >>> i & 1) == 0) continue;
            for (int j = 0; j < n; j++)
                if (j != rank) {
                    if ((bitVector[j] >>> i & 1) == 1) {
                        bitVector[j] ^= bitVector[rank];
                    }
                }
            rank++;
        }
        return rank;
    }

    public static void swap(long[] array, int i, int j) {
        long tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
