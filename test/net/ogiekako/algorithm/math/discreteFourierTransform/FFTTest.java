package net.ogiekako.algorithm.math.discreteFourierTransform;

import net.ogiekako.algorithm.Debug;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class FFTTest {
    @Test
    public void fft() throws Exception {
        int n = 8;
        double[] xr = Arrays.copyOf(new double[]{1, 2, 1}, n);
        double[] yr = Arrays.copyOf(new double[]{1, 1, 2}, n);
        double[] xi = new double[n];
        double[] yi = new double[n];
        FFT.fft(1, xr, xi);
        FFT.fft(1, yr, yi);
        double[] zr = new double[n];
        double[] zi = new double[n];
        for (int i = 0; i < n; i++) {
            zr[i] = xr[i] * yr[i] - xi[i] * yi[i];
            zi[i] = xr[i] * yi[i] + xi[i] * yr[i];
        }
        FFT.fft(-1, zr, zi);
        for (int i = 0; i < n; i++) {
            zr[i] /= n;
            zi[i] /= n;
        }
        Assert.assertArrayEquals(zr, new double[]{1, 3, 5, 5, 2, 0, 0, 0}, 1e-9);
        Assert.assertArrayEquals(zi, new double[8], 1e-9);
    }

    @Test
    public void fftRand() throws Exception {
        int n = 32;
        Random rnd = new Random(120948102);
        double[] xr = new double[n];
        double[] yr = new double[n];
        for (int i = 0; i < n / 2; i++) {
            xr[i] = rnd.nextInt(100);
            yr[i] = rnd.nextInt(100);
        }
        double[] exp = new double[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                exp[i] += xr[j] * yr[i - j];
            }
        }

        double[] xi = new double[n];
        double[] yi = new double[n];
        FFT.fft(1, xr, xi);
        FFT.fft(1, yr, yi);
        double[] zr = new double[n];
        double[] zi = new double[n];
        for (int i = 0; i < n; i++) {
            zr[i] = xr[i] * yr[i] - xi[i] * yi[i];
            zi[i] = xr[i] * yi[i] + xi[i] * yr[i];
        }
        FFT.fft(-1, zr, zi);
        for (int i = 0; i < n; i++) {
            zr[i] /= n;
            zi[i] /= n;
        }

        Assert.assertArrayEquals(zr, exp, 1e-9);
        Assert.assertArrayEquals(zi, new double[n], 1e-9);
    }
}
