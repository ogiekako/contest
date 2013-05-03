package on2012_4_30.splittingfoxes;



// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.linearAlgebra.Matrix;

public class SplittingFoxes {
    /*
    f(n) : sum of xy// 0
    Y(n) : sum of y // 1
    X(n) : sum of x // 2
    S(n) : sum of 1 // 3

    f(n) = ( f(n-1) + Y(n-1) ) S + ( -f(n-1) ) L + ( -f(n-1) ) R
    Y(n) = Y(n-1) S + X(n-1) L - X(n-1) R
    X(n) = ( X(n-1) + S(n-1) ) S - Y(n-1) L + Y(n-1) R
    S(n) = S(n-1)(S + L + R)
     */
    int M = (int) (1e9 + 7);

    public int sum(long n, int _S, int _L, int _R) {
        long S = _S, L = _L, R = _R;
        long[][] A = new long[4][4];
        A[0][0] = S - L - R; A[0][1] = S;
        A[1][1] = S; A[1][2] = L - R;
        A[2][1] = -L + R; A[2][2] = S; A[2][3] = S;
        A[3][3] = S + L + R;
        for (int i = 0; i < 4; i++) for (int j = 0; j < 4; j++) A[i][j] = (A[i][j]%M+M)%M;
        A = Matrix.powered(A, n, M);
        return (int) A[0][3];
    }
}