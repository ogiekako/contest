package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;
import java.util.Random;

public class TaskF {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while(T-- > 0) {
            int N = in.nextInt();
            double[] x = new double[N];
            for (int i = 0; i < N; i++) {
                x[i] = in.nextDouble();
            }
            boolean isFake = isFake(x);
            out.println(isFake ? "Black" : "White");
        }
    }

    double[][] reals, fakes;

    private boolean isFake(double[] x) {
        Arrays.sort(x);
        int N = x.length;
        double dReal = 0, dFake = 0;

        if (reals==null) {
            Random r = new Random();
            int t = (int)1e7/N;
            reals=new double[t][N];
            fakes=new double[t][N];
            for (int i = 0; i < t; i++) {
                for (int j = 0; j < N; j++) {
                    reals[i][j] = genReal(r);
                    fakes[i][j] = genFake(r);
                }
                Arrays.sort(reals[i]);
                Arrays.sort(fakes[i]);
            }
        }

        for (int j=0;j<reals.length;j++) {
            for (int i = 0; i < N; i++) {
                dReal += h(reals[j][i] - x[i]);
                dFake += h(fakes[j][i] - x[i]);
            }
        }
        return dFake < dReal;
    }

    private double h(double x) {
        return x * x;
    }

    public void test() {
        Random r = new Random(120912048L);
        int N = 10000;
        String okR = "";
        String okF = "";
        double ok = 0, num = 0;
        for (int i = 0; i < 200; i++) {
            num+=2;
            double[] x = new double[N];
            for (int j = 0; j < N; j++) {
                x[j] = genFake(r);
            }
            boolean ans = isFake(x);
            okF += ans ? "o" : "x";
            if (ans) ok++;

            for (int j = 0; j < N; j++) {
                x[j] = genReal(r);
            }

            okR += (ans=isFake(x)) ? "x" : "o";
            if(!ans)ok++;
        }
        System.out.println(okF);
        System.out.println(okR);
        System.out.println(ok / num);
    }

    public static void main(String[] args) {
        new TaskF().test();
    }

    private static double genReal(Random r) {
        double s = r.nextDouble(), t = r.nextDouble();
        return Math.sqrt(-Math.log(s)) * Math.sin(2 * Math.PI * t);
    }

    private static double genFake(Random r) {
        return r.nextDouble() + r.nextDouble() + r.nextDouble() + r.nextDouble() + r.nextDouble() + r.nextDouble() - 3;
    }
}
