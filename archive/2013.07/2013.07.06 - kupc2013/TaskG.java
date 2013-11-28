package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskG {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {

        double best = 0;
        int bestN = -1;
        int bests = -1;
        for (int N = 1; N <= 40; N++) {
            for (int s = 1; s <= N; s++) {
                double E = 1.0 / (N - s + 1) * s + (double) (N - s) / N;
                double diff = s - E;
                if (diff > best) {
                    best = diff;
                    bestN = N;
                    bests = s;
                }
            }
        }
        out.println(bestN);
        for(int i=0;i<bestN;i++){
            for(int j=0;j<bestN;j++){
                char c;
                if(i==j || i<bests && j<bests)c = 'N';
                else c = 'Y';
                out.printFormat("%c",c);
            }
            out.println();
        }
    }
}
