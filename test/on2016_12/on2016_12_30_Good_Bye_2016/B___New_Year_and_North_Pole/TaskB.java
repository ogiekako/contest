package on2016_12.on2016_12_30_Good_Bye_2016.B___New_Year_and_North_Pole;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n=in.nextInt();
        String[] s = new String[n];
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            d[i] = in.nextInt();
            s[i] = in.next();
        }
        int c = 0;
        for (int i=n-1;i>=0;i--) {
            if(s[i].equals("North")) {
                c += d[i];
            } else if(s[i].equals("South")) {
                c -= d[i];
            } else {
                if (c == 0 || c == 20000) {
                    out.println("NO");
                    return;
                }
            }
            if (c < 0 || c > 20000) {
                out.println("NO");
                return;
            }
        }
        out.println(c==0 ? "YES" : "NO  ");
    }
}
