package src;

public class FlippingBitsDiv1 {
    public int getmin(String[] _S, int M) {
        StringBuilder b = new StringBuilder();
        for (String s : _S) b.append(s);
        String S = b.toString();
        int N = S.length();
        int[] flipCost = new int[1 << 20];
        for (int i = 1; i < 1 << 20; i++) {
            int h = Integer.highestOneBit(i);
            flipCost[i] = 1 + flipCost[i ^ h ^ (h - 1)];
        }
        if (M > 18) {
            int L = N / M;
            int[] is = new int[M];
            int[] size = new int[M];
            for (int i = 0; i < N; i++) {
                is[i % M] |= (S.charAt(i) - '0') << i / M;
                size[i % M]++;
            }
            int res = Integer.MAX_VALUE;
            for (int i = 0; i < 1 << L; i++) {
                int val = flipCost[i];
                for (int j = 0; j < M; j++) {
                    int o = Integer.bitCount(is[j] ^ i);
                    val += Math.min(o, size[j] - o);
                }
                res = Math.min(res, val);
            }
            return res;
        } else {
            int L = N / M;
            int[] is = new int[L + 1];
            int[] size = new int[L + 1];
            for (int i = 0; i < N; i++) {
                is[i / M] |= (S.charAt(i) - '0') << i % M;
                size[i / M]++;
            }
            int res = Integer.MAX_VALUE;
            for (int i = 0; i < 1 << M; i++) {
                int init = Integer.bitCount((is[L] ^ i) & (1 << size[L]) - 1);
                int[] best = new int[]{init, Integer.MAX_VALUE};
                for (int j = L - 1; j >= 0; j--) {
                    int[] nBest = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
                    for (int k = 0; k < 2; k++)
                        if (best[k] < Integer.MAX_VALUE) {
                            for (int l = 0; l < 2; l++) {
                                int val = best[k];
                                if (k != l) val++;
                                int cur = is[j];
                                if (l == 1) cur ^= (1 << size[j]) - 1;
                                cur ^= i;
                                nBest[l] = Math.min(nBest[l], val + Integer.bitCount(cur));
                            }
                        }
                    best = nBest;
                }
                res = Math.min(res, Math.min(best[0], best[1]));
            }
            return res;
        }
    }
}
