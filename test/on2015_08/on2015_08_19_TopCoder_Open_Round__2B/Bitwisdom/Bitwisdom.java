package on2015_08.on2015_08_19_TopCoder_Open_Round__2B.Bitwisdom;



public class Bitwisdom {
    public double expectedActions(int[] _p) {
        int n = _p.length + 2;
        double[] p = new double[n];
        for (int i = 0; i < n; i++) {
            if (i == 0 || i == n - 1) p[i] = 0;
            else p[i] = _p[i - 1] / 100.0;
        }
        double res = 0;
        for (int i = 1; i < n - 2; i++) {
            res += p[i] * (1 - p[i + 1]) + (1 - p[i]) * p[i + 1];
        }
        double one = 1;
        for(int i=1;i<n-1;i++)one *= p[i];
        return res + one;
    }
}
