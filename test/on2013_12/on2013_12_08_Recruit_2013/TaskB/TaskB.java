package on2013_12.on2013_12_08_Recruit_2013.TaskB;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

//
public class TaskB {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int T = in.nextInt();
        while (T-- > 0) {
            String line = in.nextLine();
            double A = f(line);
            int n = in.nextInt();
            double best = Double.POSITIVE_INFINITY;
            String res = "";
            for (int i = 0; i < n; i++) {
                line = in.nextLine();
                String[] ss = line.split(" ");
                double B = 0;
                for (int j = 1; j < ss.length; j++) {
                    B += ss[j].length();
                }
                B /= (ss.length - 1);
                double D = Math.abs(A - B);
                if(best > D + 1e-9){
                    best = D;
                    res = ss[0].substring(0, ss[0].length() - 1);
                }
            }
            out.println(res);
        }
    }
    private double f(String line) {
        String[] ss = line.split(" ");
        double res = 0;
        for (String s : ss) res += s.length();
        return res / ss.length;
    }
}
