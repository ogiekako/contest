package tmp;

public class TheSquareRootDilemma {
    public int countPairs(int N, int M) {
        int res = 0;
        for (int a = 1; a <= N; a++) {
            int b = a;
            for (int d = 2; d * d <= b; d++) {
                while (b % (d * d) == 0) b /= d * d;
            }
            for (int d = 1; b * d * d <= M; d++) res++;
        }
        return res;
    }
}
