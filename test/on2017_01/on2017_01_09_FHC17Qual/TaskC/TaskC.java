package on2017_01.on2017_01_09_FHC17Qual.TaskC;



import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Arrays;

public class TaskC {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int H = in.nextInt(), S = in.nextInt();
        double res = 0;
        for (int o = 0; o < S; o++) {
            String s = in.next();
            String[] ss = s.split("[+-]");
            int Z = 0;
            if (ss.length > 1) {
                Z = (s.contains("+") ? 1 : -1) * Integer.parseInt(ss[1]);
            }
            int X = Integer.parseInt(ss[0].split("d")[0]);
            int Y = Integer.parseInt(ss[0].split("d")[1]);

            double[][] prob = new double[2][Y * X + 1];
            int cur = 0, nxt = 1;
            prob[cur][0] = 1;
            for (int i = 0; i < X; i++) {
                Arrays.fill(prob[nxt], 0);
                for (int j = 0; j + Y < prob[cur].length; j++) {
                    for (int k = 1; k <= Y; k++) {
                        prob[nxt][j + k] += prob[cur][j] / Y;
                    }
                }
                int tmp = cur; cur = nxt; nxt = tmp;
            }
            double val = 0;
            for (int i = Math.max(0, H - Z); i < prob[cur].length; i++) {
                val += prob[cur][i];
            }
            res = Math.max(res, val);
        }
        out.printFormat("Case #%d: %.6f\n", testNumber, res);
    }
}
