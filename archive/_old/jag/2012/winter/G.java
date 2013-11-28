package tmp;

import net.ogiekako.algorithm.geometry.Line_methods;
import net.ogiekako.algorithm.geometry.Point;
import net.ogiekako.algorithm.geometry.Track;
import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;

public class G {
    double topSpeed;
    double manSpeed;
    Point cur;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        Point[] track = new Point[N];
        for (int i = 0; i < N; i++) {
            track[i] = new Point(in.nextDouble(), in.nextDouble());
        }
        Point from = new Point(in.nextDouble(), in.nextDouble());
        topSpeed = in.nextDouble();
        cur = new Point(in.nextDouble(), in.nextDouble());
        manSpeed = in.nextDouble();
        ArrayList<Point> list1 = new ArrayList<Point>();
        ArrayList<Point> list2 = new ArrayList<Point>();
        for (int i = 0; i < N - 1; i++) {
            if (Line_methods.crsSP(track[i], track[i + 1], from)) {
                list1.add(from);
                for (int j = i + 1; j < N; j++) {
                    list1.add(track[j]);
                }
                list2.add(from);
                for (int j = i; j >= 0; j--) {
                    list2.add(track[j]);
                }
                break;
            }
        }
        double res = Math.min(solve(list1.toArray(new Point[0]), list2.toArray(new Point[0])), solve(list2.toArray(new Point[0]), list1.toArray(new Point[0])));
        out.printf("%.6f\n", res);
    }

    private double solve(Point[] first, Point[] second) {
        double time1 = Track.getReachableTime(first, topSpeed, cur, manSpeed, 0);
        Point nxtMan = time1 == -1 ? cur : Track.where(first, topSpeed, time1);
        if (time1 == -1) time1 = Track.totalLengthOfTrack(first) / topSpeed;
        double time2 = Track.getReachableTime(second, topSpeed, nxtMan, manSpeed, time1);
        if (time2 == -1) time2 = Track.totalLengthOfTrack(second) / topSpeed;
        return Math.max(time1, time2);
    }

}
