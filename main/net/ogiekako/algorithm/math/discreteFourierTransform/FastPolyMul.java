package net.ogiekako.algorithm.math.discreteFourierTransform;
import static java.lang.Math.*;
public class FastPolyMul{
	/**
	 * 返り値はas.length + bs.length - 1 要素になる.
	 * 引数は変更されない.
	 * FFTを使って,O(n log n).
	 * @param as
	 * @param bs
	 * @return as,bsをそれぞれ係数とする多項式の積の係数
	 */
	double[] fastPolyMul(double[] as,double[] bs) {		
		int N=1;
		while(N<max(as.length,bs.length))N*=2;
		N*=2;
		
		double[] ra = new double[N], ia = new double[N];
		double[] rb = new double[N], ib = new double[N];
		for(int i=0;i<as.length;i++)ra[i] = as[i];
		for(int i=0;i<bs.length;i++)rb[i] = bs[i];
		fft(1, ra, ia);
		fft(1, rb, ib);
		double[] real = new double[N];
		double[] imag = new double[N];
		for (int i = 0; i < N; i++) {
			real[i] = ra[i] * rb[i] - ia[i] * ib[i];
			imag[i] = ra[i] * ib[i] + ia[i] * rb[i];
		}
		fft(-1, real, imag);
		for(int i=0;i<N;i++) {
			real[i]/=N;
			imag[i]/=N;
		}
		double[] res = new double[as.length+bs.length-1];
		for(int i=0;i<res.length;i++)res[i] = real[i];
		return res;
	}
	
	private void fft(int sign, double[] real, double[] imag) {
		int n = real.length, d = Integer.numberOfLeadingZeros(n) + 1;
		double theta = sign * 2 * PI / n;
		for (int m = n; m >= 2; m >>= 1, theta *= 2) {
			for (int i = 0, mh = m >> 1; i < mh; i++) {
				double wr = cos(i * theta), wi = sin(i * theta);
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
				double tr = real[i]; real[i] = real[j]; real[j] = tr;
				double ti = imag[i]; imag[i] = imag[j]; imag[j] = ti;
			}
		}
	}
}
