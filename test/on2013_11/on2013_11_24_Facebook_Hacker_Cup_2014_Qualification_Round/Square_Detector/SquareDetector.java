package on2013_11.on2013_11_24_Facebook_Hacker_Cup_2014_Qualification_Round.Square_Detector;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class SquareDetector {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        out.printFormat("Case #%d: ", testNumber);
        int n = in.nextInt();
        int S = 0;
        char[][] cs = new char[n][n];
        for (int i = 0; i < n; i++) {
            cs[i] = in.next().toCharArray();
            for(char c:cs[i])if(c=='#')S++;
        }
        int s;
        for(s=0;s*s<S;s++);
        if(s*s != S){
            out.println("NO");
            return;
        }
        for (int i = 0; i < n - s + 1; i++) {
            for (int j = 0; j < n - s + 1; j++) {
                boolean ok = true;
                for (int i2 = 0; i2 < s; i2++) {
                    for (int j2 = 0; j2 < s; j2++) {
                        if(cs[i+i2][j+j2] != '#') ok = false;
                    }
                }
                if(ok){
                    out.println("YES");
                    return;
                }
            }
        }
        out.println("NO");
    }
}
