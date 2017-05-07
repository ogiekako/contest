package on2016_12.on2016_12_30_Good_Bye_2016.A___New_Year_and_Hurry;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();
        int res = 0;
        while((res+1) * 5 + k <= 240) {
            res++;
            k += res * 5;
        }
        out.println(Math.min(res, n));
    }
}
