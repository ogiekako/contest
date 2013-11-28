package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;

public class TaskC {
    /*
y=−gt^2⁄2+vt⁄√2 +h

4y(1) = -2g + 2sqrt(2)v + 4h;
y(2) = -2g + sqrt(2)v + h

4y(1) - y(2) = sqrt(2)v + 3h
v = (4y(1) - y(2) - 3h) / sqrt(2)

2y(1) = -g + sqrt(2)v + 2h
y(2) = -2g + sqrt(2)v + h

2y(1) - y(2) = g - h
g = h + 2y(1) - y(2)

    */


    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        double h = f(in, out, 0);
        double y1 = f(in, out, 1);
        double y2 = f(in, out, 2);
        double v = (4 * y1 - y2 - 3 * h) / Math.sqrt(2);
        double g = -h + 2 * y1 - y2;
        out.printf("! %.12f %.12f %.12f\n", h, v, g);
    }

    private double f(MyScanner in, PrintWriter out, double t) {
        out.printf("? %.12f\n", t);
        out.flush();
        return in.nextDouble();
    }

    public static void main(String[] args) {
        double h = 10, v = 200, g = 9.80665;
        for (int t = 0; t < 3; t++) System.out.println(new TaskC().f(h, v, g, t));
    }
    // −gt2⁄2+vt⁄√2 +h 
    double f(double h, double v, double g, double t) {
        return -g * t * t / 2 + v * t / Math.sqrt(2) + h;
    }
}
