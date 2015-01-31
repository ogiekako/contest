package src;

import net.ogiekako.algorithm.math.discreteFourierTransform.FFT;

import java.util.ArrayList;
import java.util.List;

public class SumOfArrays {
    int MAX;
    int Q = 7;
    int N;
    public String findbestpair(int n, int[] Aseed, int[] Bseed) {
        MAX = Math.max(Aseed[5], Bseed[5]) + 1;
        int[] A = make(n, Aseed[0], Aseed[1], Aseed[2], Aseed[3], Aseed[4], Aseed[5]);
        int[] B = make(n, Bseed[0], Bseed[1], Bseed[2], Bseed[3], Bseed[4], Bseed[5]);
        N = 1;
        while(N < MAX) N *= 2;
        N *= 2;
        FFT fft = new FFT();
        int[] C = new int[N];
        for(int k=1;k<Q;k++){
            double[] nA = fit(filter(A, k), N);
            double[] nB = fit(filter(B, k), N);
            debug("nA", nA);
            debug("nB", nB);
            double[] imgA = new double[N];
            double[] imgB = new double[N];
            fft.fft(1, nA, imgA);
            fft.fft(1, nB, imgB);
            double[] Zr = new double[N];
            double[] Zi = new double[N];
            for (int i = 0; i < N; i++) {
                Zr[i] = nA[i] * nB[i] - imgA[i] * imgB[i];
                Zi[i] = nA[i] * imgB[i] + imgA[i] * nB[i];
            }
            fft.fft(-1, Zr, Zi);
            for (int i = 0; i < N; i++) {
                Zr[i] /= N;
                Zi[i] /= N;
                C[i] += Math.round(Zr[i]);
            }
            debug("Zr", Zr);
        }
        double[] nA = filter(A, Q);
        double[] nB = filter(B, Q);
        List<Integer> bIs = new ArrayList<>();
        for(int i=0;i<nB.length;i++)if(nB[i] > 0)bIs.add(i);
        for(int i=0;i<nA.length;i++)if(nA[i]>0){
            for(int j:bIs){
                C[i+j] += Math.min(A[i], B[j]) - Q + 1;
            }
        }

        int res=0;
        for(int i=0;i<N;i++){
            if(C[i] >= C[res]) res = i;
        }
        return C[res] + " " + res;
    }

    void debug(Object...os) {
//        System.err.println(Arrays.deepToString(os));
    }


    private double[] fit(double[] ds, int n) {
        double[] res = new double[n];
        System.arraycopy(ds, 0, res, 0, ds.length);
        return res;
    }

    private double[] filter(int[] A, int th) {
        double[] res = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            if (A[i] >= th) {
                res[i] = 1;
            }
        }
        return res;
    }

    int[] make(int n, long a0, long a1, long a2, long a3, long a4, int a5){
        int[] V = new int[n];
        V[0] = (int) a0;
        V[1] = (int) a1;
        for (int i=2;i<n;i++) {
            V[i] = (int) ((V[i - 1] * a2 + V[i - 2] * a3 + a4) % a5);
        }
        int[] A = new int[MAX];
        for(int v:V)A[v]++;
        return A;
    }
}
