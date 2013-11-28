package src;

import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import java.util.*;

public class AOJ2488_TreeConstruction {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for(int i=0;i<n;i++){
            x[i] = in.nextInt();
            y[i] = in.nextInt();
            minX = Math.min(minX,x[i]);
            minY = Math.min(minY,y[i]);
        }
        for(int i=0;i<n;i++)x[i] -= minX;
        for(int i=0;i<n;i++)y[i] -= minY;
        long[][] X = new long[n][n];
        int[][] K = new int[n][n];
        for(int i=0;i<n;i++)X[i][i] = 0;
        for(int i=0;i<n;i++)K[i][i] = i;
        for(int d=1;d<n;d++)for(int i=0,j;(j=i+d)<n;i++){
            X[i][j] = Long.MAX_VALUE;
            int w = - x[i] - y[j];
            for(int s=K[i][j-1];s<=Math.min(j-1,K[i+1][j]);s++){
                long value = X[i][s] + X[s+1][j] + w;
                if(X[i][j] >= value){
                    X[i][j] = value;
                    K[i][j] = s;
                }
            }
        }
        long res = X[0][n-1];
        for(int i=0;i<n;i++)res += x[i];
        for(int i=0;i<n;i++)res += y[i];
        out.println(res);
    }
}
