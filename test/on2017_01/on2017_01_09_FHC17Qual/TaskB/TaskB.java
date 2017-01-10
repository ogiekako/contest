package on2017_01.on2017_01_09_FHC17Qual.TaskB;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++)
            a[i] = in.nextInt();
        Arrays.sort(a);
        int res = 0;
        for(int i=0,j=n-1;i<=j;j--) {
            int w=a[j];
            for(;w<50 && i<j; i++) w+=a[j];
            if (w>=50) res++;
        }
        out.printFormat("Case #%d: %d\n",testNumber, res);
    }
}
