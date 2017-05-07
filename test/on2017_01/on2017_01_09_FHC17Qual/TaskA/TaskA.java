package on2017_01.on2017_01_09_FHC17Qual.TaskA;



import net.ogiekako.algorithm.geometry.Point;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

public class TaskA {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        double P = in.nextDouble(), X = in.nextDouble(), Y = in.nextDouble();
        out.printFormat("Case #%d: %s\n",testNumber, isBlack(P,X,Y) ? "black" : "white");
    }

    private boolean isBlack(double P, double X, double Y) {
        X -= 50; Y -= 50;
        double tmp = X; X = Y; Y = tmp;
        X /= 50; Y /= 50;
        Point p = new Point(X,Y);
        if (p.norm2() > 1) return false;
        return p.arg() < P / 100 * 2 * Math.PI;
    }
}
