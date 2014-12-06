package on2014_10.on2014_10_05_Single_Round_Match_635.SimilarRatingGraph;


import java.util.Arrays;
import java.util.Random;

public class SimilarRatingGraph {
    public static void main(String[] args) {
        new SimilarRatingGraph().run();
    }

    public double maxLength(int[] date, int[] rating) {
        int n = date.length;
        double res = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++)
                if (i != j) {
                    long d1 = date[i + 1] - date[i];
                    long d2 = date[j + 1] - date[j];
                    long r1 = rating[i + 1] - rating[i];
                    long r2 = rating[j + 1] - rating[j];
                    if (r1 * d2 == r2 * d1) {
                        double val = 0;
                        for (int k = 0; j + k < n - 1 && i + k < n - 1; k++) {
                            long d3 = date[i + k + 1] - date[i + k];
                            long d4 = date[j + k + 1] - date[j + k];
                            long r3 = rating[i + k + 1] - rating[i + k];
                            long r4 = rating[j + k + 1] - rating[j + k];
                            if (r3 * d4 == r4 * d3 && r2 * r3 == r1 * r4 && d2 * d3 == d1 * d4) {
                                val += Math.sqrt(d3 * d3 + r3 * r3);
                            } else {
                                break;
                            }
                        }
                        res = Math.max(res, val);
                    }
                }
        }
        return res;
    }

    private void run() {
        int n = 400;
        Random rnd = new Random(1401820182L);
        int[] p = new int[n];
        int[] c = new int[n];
        for (int o = 0; o < 100; o++) {
            for (int i = 0; i < n; i++) {
                p[i] = (i == 0 ? 0 : p[i - 1]) + rnd.nextInt(20) + 1;
                c[i] = p[i];
            }
            long s = System.currentTimeMillis();
            System.out.println(Arrays.toString(p).replace(" ", ""));
            System.out.println(Arrays.toString(c));
            double res = maxLength(p, c);
            System.err.println("res=" + ("" + res).replace(" ", ""));
            System.err.println(System.currentTimeMillis() - s);
            if (res >= 0) break;
        }
    }
}
