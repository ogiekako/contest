package net.ogiekako.algorithm.math.linearAlgebra;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;


public class LinearSystemTest {
    @Test
    public void solutionSpaceTest() {
        Random r = new Random(1000109281028L);
        int n = 231;
        double[][] A = new double[n][n];
        double[] b = new double[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) A[i][j] = r.nextDouble();
        for (int i = 0; i < n; i++) b[i] = r.nextDouble();
        double[][] X = LinearSystem.solutionSpace(A, b, 1e-9);
        Assert.assertEquals(1, X.length);
        assertArrayEquals(b, mul(A, X[0]), 1e-9);
    }


    private void assertArrayEquals(double[] exp, double[] res, double delta) {
        Assert.assertEquals(exp.length, res.length);
        for (int i = 0; i < exp.length; i++) Assert.assertEquals(exp[i], res[i], delta);
    }


    @Test
    public void solutionSpaceTest2() {
        Random r = new Random(1204219487L);
        int n = 100;
        double[][] A = new double[n][n - 1];
        double[] b = new double[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n - 1; j++) A[i][j] = r.nextDouble();
        for (int i = 0; i < n; i++) b[i] = r.nextDouble();
        double[][] X = LinearSystem.solutionSpace(A, b, 1e-9);
        Assert.assertNull(X);
    }

    @Test
    public void solutionSpaceTest3() {
        Random r = new Random(12041024387L);
        int n = 100;
        double[][] A = new double[n][n + 1];
        double[] b = new double[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n + 1; j++) A[i][j] = r.nextDouble();
        for (int i = 0; i < n; i++) b[i] = r.nextDouble();
        double[][] X = LinearSystem.solutionSpace(A, b, 1e-9);
        Assert.assertEquals(2, X.length);
        assertArrayEquals(b, mul(A, X[0]), 1e-9);
        double[] zero = new double[n];
        Assert.assertFalse(Arrays.equals(new double[n + 1], X[1]));
        assertArrayEquals(zero, mul(A, X[1]), 1e-9);
    }

    @Test
    public void solutionSpaceTest4() {
        Random r = new Random(12041024387L);
        int n = 200;
        int m = 500;
        double[][] A = new double[n][m];
        double[] b = new double[n];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) A[i][j] = r.nextDouble();
        for (int i = 0; i < n; i++) b[i] = r.nextDouble();
        double[][] X = LinearSystem.solutionSpace(A, b, 1e-9);
        Assert.assertEquals(m - n + 1, X.length);
        assertArrayEquals(b, mul(A, X[0]), 1e-9);
        double[] zero = new double[n];
        for (int i = 1; i < X.length; i++) {
            Assert.assertFalse(Arrays.equals(new double[m], X[i]));
            assertArrayEquals(zero, mul(A, X[i]), 1e-9);
        }
    }

    @Test
    public void solutionSpaceTest5() {
        Random r = new Random(12041024387L);
        int n = 300;
        int m = 600;
        double[][] A = new double[n * 2][m];
        double[] b = new double[n * 2];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) A[i][j] = r.nextInt(100);
        for (int i = 0; i < n; i++) b[i] = r.nextInt(100);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double mul = r.nextDouble();
                b[n + i] += b[j] * mul;
                for (int k = 0; k < m; k++) A[n + i][k] += A[j][k] * mul;
            }
        }
        double[][] X = LinearSystem.solutionSpace(A, b, 1e-6);
        Assert.assertEquals(m - n + 1, X.length);
        assertArrayEquals(b, mul(A, X[0]), 1e-6);
        double[] zero = new double[n * 2];
        for (int i = 1; i < X.length; i++) {
            Assert.assertFalse(Arrays.equals(new double[n], X[i]));
            assertArrayEquals(zero, mul(A, X[i]), 1e-6);
        }
    }

    @Test
    public void solutionSpacesTest() {
        Random r = new Random(12041024387L);
        int n = 10;
        int k = 10;
        double[][] A = new double[n][n];
        double[][] b = new double[k][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) A[i][j] = r.nextDouble();
        for (int i = 0; i < k; i++) for (int j = 0; j < n; j++) b[i][j] = r.nextDouble();
        double[][] X = LinearSystem.solutionSpaces(A, b, 1e-6);
        Assert.assertEquals(k, X.length);
        for (int i = 0; i < k; i++) assertArrayEquals(b[i], mul(A, X[i]), 1e-6);
        double[] zero = new double[n];
        for (int i = k; i < X.length; i++) {
            Assert.assertFalse(Arrays.equals(new double[n], X[i]));
            assertArrayEquals(zero, mul(A, X[i]), 1e-6);
        }
    }

    @Test
    public void solutionSpacesTest2() {
        Random r = new Random(1204219487L);
        int n = 100;
        int k = 10;
        double[][] A = new double[n][n - 1];
        double[][] b = new double[k][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n - 1; j++) A[i][j] = r.nextDouble();
        for (int k2 = 0; k2 < k; k2++) for (int i = 0; i < n; i++) b[k2][i] = r.nextDouble();
        double[][] X = LinearSystem.solutionSpaces(A, b, 1e-9);
        Assert.assertEquals(X.length, k);
        for (int i = 0; i < k; i++) Assert.assertNull(X[i]);
    }
    @Test
    public void solutionSpacesTest3() {
        Random r = new Random(198274198274L);
        int n = 100;
        double[][] A = new double[n][n];
        double[][] E = new double[n][n];
        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) A[i][j] = r.nextDouble();
        for (int i = 0; i < n; i++) E[i][i] = 1;
        double[][] inv = transpose(LinearSystem.solutionSpaces(A, E, 1e-9));
        Assert.assertEquals(n, inv.length);
        Assert.assertEquals(n, inv[0].length);

        double[][] E2 = mul(A, inv);
        Assert.assertEquals(E.length, E2.length);
        for (int i = 0; i < E.length; i++) assertArrayEquals(E[i], E2[i], 1e-9);
    }

    private double[][] transpose(double[][] A) {
        double[][] At = new double[A[0].length][A.length];
        for (int i = 0; i < At.length; i++) for (int j = 0; j < At[0].length; j++) At[i][j] = A[j][i];
        return At;
    }

    private double[][] mul(double[][] A, double[][] B) {
        int n = A.length, m = B[0].length, k = A[0].length;
        if (k != B.length) throw new IllegalArgumentException();
        double[][] C = new double[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) for (int l = 0; l < k; l++) C[i][j] += A[i][l] * B[l][j];
        return C;
    }

    @Test
    public void solutionSpacesTest5() {
        Random r = new Random(12041024387L);
        int n = 400;
        int m = 600;
        int k = 100;
        double[][] A = new double[n * 2][m];
        double[][] b = new double[k][n * 2];
        for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) A[i][j] = r.nextInt(100);
        for (int k2 = 0; k2 < k; k2++) for (int i = 0; i < n; i++) b[k2][i] = r.nextInt(100);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double mul = r.nextDouble();
                for (int l = 0; l < k; l++) b[l][n + i] += b[l][j] * mul;
                for (int l = 0; l < m; l++) A[n + i][l] += A[j][l] * mul;
            }
        }
        double[][] X = LinearSystem.solutionSpaces(A, b, 1e-6);
        Assert.assertEquals(m - n + k, X.length);
        for (int i = 0; i < k; i++) assertArrayEquals(b[i], mul(A, X[i]), 1e-6);
        double[] zero = new double[n * 2];
        for (int i = k; i < X.length; i++) {
            Assert.assertFalse(Arrays.equals(new double[n], X[i]));
            assertArrayEquals(zero, mul(A, X[i]), 1e-6);
        }
    }

    private void debug(Object... objects) {
        System.err.println(Arrays.deepToString(objects));
    }

    private double[] mul(double[][] A, double[] v) {
        if (A[0].length != v.length) throw new IllegalArgumentException();
        double[] x = new double[A.length];
        for (int i = 0; i < A.length; i++) for (int j = 0; j < v.length; j++) x[i] += A[i][j] * v[j];
        return x;
    }
}
