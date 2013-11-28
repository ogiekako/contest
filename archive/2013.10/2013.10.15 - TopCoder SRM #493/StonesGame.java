package src;

public class StonesGame {
    public String winner(int N, int M, int K, int L) {
        boolean[] r1 = calc(N, M, K);
        boolean[] r2 = calc(N, L, K);
        if (r1[L - 1]) return "Romeo";
        for (int i = 0; i < N; i++) if (r1[i] && !r2[i]) return "Draw";
        return "Strangelet";
    }
    private boolean[] calc(int N, int M, int K) {
        M--;
        boolean[] res = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (((M - i) % 2 == 0) ^ (K % 2 == 0)) {
                int c = (M + i) / 2;
                int left = c - (K - 1) / 2;
                int right = c + K / 2;
                if (left <= i && i <= right && 0 <= left && right < N) res[i] = true;
            }
        }
        return res;
    }
}
