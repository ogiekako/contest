package on_2012.on2012_5_19.kleofastail;


// Paste me into the FileEdit configuration dialog

import java.util.Random;

public class KleofasTail {
    public long countGoodSequences(long K, long A, long B) {
        if (K == 0) return B - A + 1;
        long L = K;
        if (L % 2 == 0) L |= 1;
        long res = 0;
        while (K > 0) {
            long C = Math.max(A, K);
            long D = Math.min(B, L);
            if (C <= D) res += D - C + 1;
            K <<= 1;
            L <<= 1;
            L |= 1;
        }
        return res;
    }

    public static void main(String[] args) {
        Random rnd = new Random(124901824L);
        for (; ; ) {
            int K = rnd.nextInt(1000);
            int B = rnd.nextInt(1000);
            int A = rnd.nextInt(B + 1);
            long res = new KleofasTail().countGoodSequences(K, A, B);
            long exp = calc(K, A, B);
            if (res != exp) {

                System.err.println(K + " " + A + " " + B);
                System.err.println("res " + res);
                System.err.println("exp " + exp);
                throw new AssertionError();
            }
        }
    }

    private static long calc(int K, int A, int B) {
        long res = 0;
        for (int i = A; i <= B; i++) {
            if (has(i, K)) {
//                System.err.println(i);
                res++;
            }
        }
        return res;
    }

    private static boolean has(int v, int K) {
        if (v == K) return true;
        if (v == 0) return false;
        return has(v % 2 == 0 ? v / 2 : v - 1, K);
    }


}

