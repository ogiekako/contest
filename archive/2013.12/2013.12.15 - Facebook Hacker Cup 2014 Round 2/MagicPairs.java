package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MagicPairs {
    long[] A, B;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int N = in.nextInt(), M = in.nextInt();
        make(N, M, in);
        long res = solve(A, B);
        out.printFormat("Case #%d: %d\n", testNumber, res);
    }
    private long solve(long[] A, long[] B) {
        int N = A.length, M = B.length;
        TreeSet<Long> setA = new TreeSet<>(), setB = new TreeSet<>(), onlyA = new TreeSet<>(), onlyB = new TreeSet<>();
        long res = 0;
        for (int i = 0, j = 0; i < N || j < M; ) {
            if (onlyA.isEmpty() && onlyB.isEmpty()) {
                int prevI = i;
                int prevJ = j;
                while (i < N && setA.contains(A[i])) i++;
                while (j < M && setB.contains(B[j])) j++;
                res += (long) (i - prevI) * (j - prevJ);
            }
            if (setA.size() < setB.size()) {
                while (i < N && setA.contains(A[i])) i++;
                if (i == N) break;
                setA.add(A[i]);
                if (onlyB.contains(A[i])) onlyB.remove(A[i]);
                else onlyA.add(A[i]);
            } else {
                while (j < M && setB.contains(B[j])) j++;
                if (j == M) break;
                setB.add(B[j]);
                if (onlyA.contains(B[j])) onlyA.remove(B[j]);
                else onlyB.add(B[j]);
            }
        }
        return res;
    }
    private void make(int N, int M, MyScanner in) {
        A = new long[N]; B = new long[M];
        long x1 = in.nextInt(), a1 = in.nextLong(), b1 = in.nextLong(), c1 = in.nextLong(), r1 = in.nextLong();
        long x2 = in.nextInt(), a2 = in.nextLong(), b2 = in.nextLong(), c2 = in.nextLong(), r2 = in.nextLong();
        A[0] = x1;
        B[0] = x2;
        for (int i = 1; i < N || i < M; i++) {
            if (i < N)
                A[i] = (a1 * A[(i - 1) % N] + b1 * B[(i - 1) % M] + c1) % r1;
            if (i < M)
                B[i] = (a2 * A[(i - 1) % N] + b2 * B[(i - 1) % M] + c2) % r2;
        }
    }
    public static void main(String[] args) {
        int N = 100, M = 150;
        long[] A = new long[N], B = new long[M];
        Random rnd = new Random(124102498L);
        for (int t = 0; t < 50; t++) {
            System.err.println(t);
            for (int i = 0; i < N; i++) {
                A[i] = rnd.nextInt(100);
            }
            for (int i = 0; i < M; i++) {
                B[i] = rnd.nextInt(100);
            }
            long res = new MagicPairs().solve(A,B);
            long exp = 0;
            for(int i=0;i<N;i++)for(int j=0;j<M;j++){
                Set<Long> set1 = new TreeSet<>();
                Set<Long> set2 = new TreeSet<>();
                for(int k=0;k<=i;k++)set1.add(A[k]);
                for(int k=0;k<=j;k++)set2.add(B[k]);
                if(set1.equals(set2))exp++;
            }
            System.err.println("res " + res);
            if(res!=exp) throw new AssertionError();
        }

    }
}
