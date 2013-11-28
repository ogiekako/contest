package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class ElevenMultiples {
    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
    int M = 1000000007;
    public int countMultiples(String[] pieces) {
        for (int i = 0; i < 60; i++) P[i] = i == 0 ? 1 : P[i - 1] * i % M;
        for (int i = 0; i < 60; i++)
            for (int j = 0; j < i + 1; j++) {
                C[i][j] = j == 0 ? 1 : C[i - 1][j - 1] + C[i - 1][j];
                if (C[i][j] >= M) C[i][j] -= M;
            }
        int n = pieces.length;
        int[] even = new int[n];
        int cntEven = 0;
        int[] odd = new int[n];
        int cntOdd = 0;
        for (String s : pieces) {
            int val = 0;
            for (int i = 0; i < s.length(); i++) {
                if (i % 2 == 0) val += s.charAt(i) - '0';
                else val -= s.charAt(i) - '0';
            }
            val = (val % 11 + 11) % 11;
            if (s.length() % 2 == 0) {
                even[cntEven++] = val;
            } else {
                odd[cntOdd++] = val;
            }
        }
        long[][][] wayE = calcDp(even, cntEven);
        long[][][] wayO = calcDp(odd, cntOdd);

        long[] oddRes = odd(wayO, cntOdd);
        long[] evenRes = even(wayE, cntEven, cntOdd);
        debug(oddRes);
        debug(evenRes);
        long res = 0;
        for (int i = 0; i < 11; i++) {
            int j = (11 - i) % 11;
            res = (res + oddRes[i] * evenRes[j]) % M;
        }
        return (int) res;

    }


    private long[] even(long[][][] wayE, int cntEven, int cntOdd) {
        int plus = (cntOdd + 2) / 2;
        int minus = (cntOdd + 1) / 2;
        long[] res = new long[11];
        memo = new long[60][60];
        for (int i = 0; i < 60; i++) Arrays.fill(memo[i], -1);
        for (int i = 0; i <= cntEven; i++)
            for (int k = 0; k < 11; k++) {
                int j = cntEven - i;
                res[k] = (res[k] + wayE[i][j][k] * calc(plus, i) % M * calc(minus, j)) % M;
            }
        return res;
    }
    long[][] memo;

    private long calc(int n, int k) {
        if (n == 0) return k == 0 ? 1 : 0;
        if (memo[n][k] >= 0) return memo[n][k];
        long res = 0;
        for (int i = 0; i <= k; i++) {
            res = (res + C[k][i] * P[i] % M * calc(n - 1, k - i)) % M;
        }
        return memo[n][k] = res;
    }

    long[][] C = new long[60][60];
    long[] P = new long[60];

    private long[] odd(long[][][] wayO, int cntOdd) {
        int plus = (cntOdd + 1) / 2;
        int minus = cntOdd - plus;
        long[] res = new long[11];

        for (int i = 0; i < 11; i++) {
            res[i] = wayO[plus][minus][i] * P[plus] % M * P[minus] % M;
        }
        return res;
    }

    private long[][][] calcDp(int[] even, int cntEven) {
        long[][][] way = new long[cntEven + 1][cntEven + 1][11];
        way[0][0][0] = 1;
        for (int i = 0; i < cntEven + 1; i++)
            for (int j = 0; j < cntEven + 1; j++)
                if (i + j < cntEven) for (int k = 0; k < 11; k++) {
                    int plus = (k + even[i + j]) % 11;
                    int minus = (k + 11 - even[i + j]) % 11;
                    way[i + 1][j][plus] += way[i][j][k];
                    if (way[i + 1][j][plus] >= M) way[i + 1][j][plus] -= M;
                    way[i][j + 1][minus] += way[i][j][k];
                    if (way[i][j + 1][minus] >= M) way[i][j + 1][minus] -= M;
                }
        return way;
    }


}

