package src;

import net.ogiekako.algorithm.utils.ArrayUtils;

public class BlackBoxDiv1 {
    int MOD = (int) (1e9 + 7);
    long[] two;
    long[][] A, B;
    long[][][] C;

    public int count(int W, int H) {
        two = new long[H * W + 1];
        for (int i = 0; i < two.length; i++) {
            two[i] = i == 0 ? 1 : two[i - 1] * 2 % MOD;
        }
        A = new long[W + 1][H + 1];
        B = new long[W + 1][H + 1];
        C = new long[W + 1][W + 1][H + 1];
        ArrayUtils.fill(A, -1);
        ArrayUtils.fill(B, -1);
        ArrayUtils.fill(C, -1);
        return (int) ((two[H * W] - R(W, H) + MOD) % MOD);
    }

    /*
    .....
    .....
     */
    long R(int W, int H) {
        if (H == 0) return 1;
        long res = 0;
        /*
        ///// or \\\\\
         */
        res += 2 * A(W, H - 1);

        /*
        [ i ]
        \\..\/..//
         */
        for (int i = 1; i < W; i++) {
            res += C(i, W - i, H - 1);
        }

        /*
        [ i ]    [ j ]
        \\..\/..\/../
        .....::::.... <- : should be same as the previous row.
         */
        for (int i = 0; i < W; i++)
            for (int j = 0; i + j < W; j++) {
                int k = W - i - j;
                if (k - 2 >= 0) {
                    res += two[k - 2] * B(i, H - 1) % MOD * B(j, H - 1) % MOD;
                }
            }
        return res % MOD;
    }

    void debug(Object... os) {
//        System.err.println(Arrays.deepToString(os));
    }

    /*
    W=5, H=2
    /////
    .....
    .....

     */
    long A(int W, int H) {
        if (H == 0) return 1;
        if (A[W][H] >= 0) return A[W][H];
        long res = 0;
        /*
        /////    /////
        ///// or \\\\\
        .....    .....
         */
        res += A(W, H - 1) * 2;
        /*
        /////
        /...\
        ..... <- should be same as the previous row.
         */
        if (W - 1 >= 0) {
            res += W - 1;
        }
        debug("A", W, H, res);
        return A[W][H] = res % MOD;
    }

    /*
    W=4, H=2
    \////
    \....
    \....
     */
    long B(int W, int H) {
        if (H == 0) return 1;
        if (B[W][H] >= 0) return B[W][H];
        long res = 0;
        /*
        \////
        \////
        \....
         */
        res += B(W, H - 1);

        /*
        \////////
        \/../\..\ <- '/'s can be 0.
        \........ <- should be same as the previous row.
         */
        res += W;
        debug("B", W, H, res);
        return B[W][H] = res % MOD;
    }

    /*
    [ L ][ R ]
    \\..\/..//
    ..........
    ..........
     */
    long C(int L, int R, int H) {
        if (H == 0) return 1;
        if (C[L][R][H] >= 0) return C[L][R][H];
        long res = 0;
        /*
        \\\/// or \\\///
        \\\\\\    //////
         */
        res += A(L + R, H - 1) * 2;
        /*
        \\\///
        \\\///
         */
        res += C(L, R, H - 1);
        /*
        \\\\\/////
        \\\\\//\\\
        .....//\\\
         */
        if (R - 1 >= 0) {
            res += (R - 1) * B(L, H - 1) % MOD;
        }
        /*
        \\\\\/////
        //\\\/////
         */
        if (L - 1 >= 0) {
            res += (L - 1) * B(R, H - 1) % MOD;
        }

        /*
        \\\\\/////
        //\\\///\\ <- mid '\'s and '/'s can be 0.
        .......... <- should be same as the previous row.
         */
        res += L * R;
        debug("C", L, R, H, res);
        return C[L][R][H] = res % MOD;
    }

    public static void main(String[] args) {
        for(int W=1;W<=3;W++)for(int H=1;H<=3;H++){
            int res = new BlackBoxDiv1().count(W, H);
            int exp = new BlackBoxDiv1().solveSlow(W, H);
            if (res != exp) {
                System.err.println(res + " " + exp + " " + W + " " + H);
            }
        }
    }

    private int solveSlow(int w, int h) {
        int res = 0;
        for(int m=0;m<=1<<w+h;m++){
            boolean[][] b = new boolean[w][h];
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    b[i][j] = ((m>>i*w+j)&1)==1;
                }
            }
            boolean ok = true;
            for(int i=0;i<w-1;i++)for(int j=0;j<h-1;j++){
                int c=0;
                if(b[i][j])c++;
                if(!b[i+1][j])c++;
                if(b[i+1][j+1])c++;
                if(!b[i][j+1])c++;
                if (c>=3)ok=false;
            }
            if(!ok)res++;
        }
        return res;
    }

}
