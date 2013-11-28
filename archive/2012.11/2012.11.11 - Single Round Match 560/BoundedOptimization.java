package tmp;

public class BoundedOptimization {
    double EPS = 1e-9;

    public double maxValue(String[] expr, int[] lowerBound, int[] upperBound, int maxSum) {
        int n = lowerBound.length;
        StringBuilder sb = new StringBuilder();
        for (String s : expr) sb.append(s);
        boolean[][] graph = new boolean[n][n];
        for (int i = 0; i < sb.length(); i += 3) {
            int x = sb.charAt(i) - 'a';
            int y = sb.charAt(i + 1) - 'a';
            graph[x][y] = graph[y][x] = true;
        }
        double[][] lowerSum = new double[1 << n][n];
        double[][] upperSum = new double[1 << n][n];
        for (int mask = 0; mask < 1 << n; mask++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (graph[i][j] && mask << 31 - j < 0) {
                        lowerSum[mask][i] += lowerBound[j];
                        upperSum[mask][i] += upperBound[j];
                    }
        double res = 0;
        for (int mid = 0; mid < 1 << n; mid++) {
            boolean clique = true;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < i; j++) if (mid << 31 - i < 0 && mid << 31 - j < 0 && !graph[i][j]) clique = false;
            if (!clique) continue;
            int m = Integer.bitCount(mid);
            int sub = (1 << n) - 1 ^ mid;
            int lower = 0;
            do {
                lower = lower - 1 & sub;
                int upper = sub ^ lower;
                double S = maxSum;
                for (int i = 0; i < n; i++) if (lower << 31 - i < 0) S -= lowerBound[i];
                for (int i = 0; i < n; i++) if (upper << 31 - i < 0) S -= upperBound[i];
                if (S < -EPS) continue;
                double[] d = new double[n];
                for (int i = 0; i < n; i++) d[i] = lowerSum[lower][i] + upperSum[upper][i];
                double sum = 0;
                for (int i = 0; i < n; i++) if (lower << 31 - i < 0) sum += d[i] * lowerBound[i];
                for (int i = 0; i < n; i++) if (upper << 31 - i < 0) sum += d[i] * upperBound[i];
                if (m == 0) {
                    res = Math.max(res, sum);
                    continue;
                }
                double D = 0;
                for (int i = 0; i < n; i++) if (mid << 31 - i < 0) D += d[i];
                double lambda = 2 * (S - D) / m;
                double[] x = new double[n];
                boolean valid = true;
                sum += S * S;
                for (int i = 0; i < n; i++)
                    if (mid << 31 - i < 0) {
                        x[i] = (lambda + 2 * d[i]) / 2;
                        if (x[i] < lowerBound[i] - EPS || x[i] > upperBound[i] + EPS) valid = false;
                        sum += 2 * d[i] * x[i] - x[i] * x[i];
                    }
                if (!valid) continue;
                res = Math.max(res, sum);
            } while (lower > 0);
        }
        return res / 2;
    }
}
