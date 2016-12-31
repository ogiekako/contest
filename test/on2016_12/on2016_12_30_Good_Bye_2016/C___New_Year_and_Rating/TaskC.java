package on2016_12.on2016_12_30_Good_Bye_2016.C___New_Year_and_Rating;



import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        long L= Long.MIN_VALUE / 2, R = Long.MAX_VALUE / 2;
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            int c = in.nextInt(), d = in.nextInt();
            if (d == 1) {
                L = Math.max(L, 1900);
            } else {
                R = Math.min(R, 1899);
            }
            L += c;
            R += c;
        }
        if (L <= R) {
            if (R > Long.MAX_VALUE / 4) {
                out.println("Infinity");
            } else {
                out.println(R);
            }
        } else {
            out.println("Impossible");
        }
    }
}
