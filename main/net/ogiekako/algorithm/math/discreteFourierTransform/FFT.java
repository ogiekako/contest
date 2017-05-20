package net.ogiekako.algorithm.math.discreteFourierTransform;

/*
 * 長さは 2 のべき乗であること
 * 順変換のときは sign=1, 逆変換のときは sign=-1 を指定する
 * 逆変換の際には結果を 1/n 倍すること
 * thanks to wata.
 */
public class FFT {
    /**
     * f(x) = \sum c_kx^k (where c_k = real[k] + i imag[k])
     * を、w^k (sign==-1 の場合は (-w)^k)について評価した結果で引数を置き換える。
     * ここで w は、1 の n 乗根。
     */
    public static void fft(int sign, double[] real, double[] imag) {
        int n = real.length, d = Integer.numberOfLeadingZeros(n) + 1;
        double theta = sign * 2 * Math.PI / n;
        for (int m = n; m >= 2; m >>= 1, theta *= 2) {
            for (int i = 0, mh = m >> 1; i < mh; i++) {
                double wr = Math.cos(i * theta), wi = Math.sin(i * theta);
                for (int j = i; j < n; j += m) {
                    int k = j + mh;
                    double xr = real[j] - real[k], xi = imag[j] - imag[k];
                    real[j] += real[k];
                    imag[j] += imag[k];
                    real[k] = wr * xr - wi * xi;
                    imag[k] = wr * xi + wi * xr;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int j = Integer.reverse(i) >>> d;
            if (j < i) {
                double tr = real[i];
                real[i] = real[j];
                real[j] = tr;
                double ti = imag[i];
                imag[i] = imag[j];
                imag[j] = ti;
            }
        }
    }

    /*
     * 実行例 : SRM436 1000
     */
    public int maxScore(int N, int Z0, int A, int B, int M) {
        long tz = Z0 % M;
        int NN = Integer.highestOneBit(N) << 2;
        double[] X = new double[NN];
        double[] Y = new double[NN];
        for (int i = 0; i < N; i++) {
            X[N - 1 - i] = tz % 100;
            tz = (tz * A + B) % M;
        }
        for (int i = 0; i < N; i++) {
            Y[i] = tz % 100;
            tz = (tz * A + B) % M;
        }
        double[] Xi = new double[NN], Yi = new double[NN];
        fft(1, X, Xi);
        fft(1, Y, Yi);
        double[] Zr = new double[NN];
        double[] Zi = new double[NN];
        for (int i = 0; i < NN; i++) {
            Zr[i] = X[i] * Y[i] - Xi[i] * Yi[i];
            Zi[i] = X[i] * Yi[i] + Xi[i] * Y[i];
        }
        fft(-1, Zr, Zi);
        for (int i = 0; i < NN; i++) {
            Zr[i] /= NN;
            Zi[i] /= NN;
        }
        double res = 0;
        for (int i = 0; i < N; i++) {
            res = Math.max(res, Zr[i] + Zr[i + N]);
        }
        return (int) Math.round(res);
    }
}
