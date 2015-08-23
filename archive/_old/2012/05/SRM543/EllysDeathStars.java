package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.geometry.Circle_methods;
import net.ogiekako.algorithm.geometry.Point;
import net.ogiekako.algorithm.geometry.Track;
import net.ogiekako.algorithm.graph.Graph;
import net.ogiekako.algorithm.utils.Cast;

import java.util.Set;
import java.util.TreeSet;

public class EllysDeathStars {
    public double getMax(String[] _stars, String[] _ships) {
        int STAR = _stars.length;
        Point[] stars = new Point[STAR];
        for (int i = 0; i < STAR; i++)
            stars[i] = Point.make(Integer.valueOf(_stars[i].split(" ")[0]), Integer.valueOf(_stars[i].split(" ")[1]));
        int SHIP = _ships.length;
        Ship[] ships = new Ship[SHIP];
        for (int i = 0; i < SHIP; i++) {
            String[] ss = _ships[i].split(" ");
            int[] is = new int[ss.length];
            for (int j = 0; j < is.length; j++) is[j] = Integer.valueOf(ss[j]);
            Point[] ps = new Point[]{
                    Point.make(is[0], is[1]),
                    Point.make(is[2], is[3])
            };
            ships[i] = new Ship(ps, is[4], is[5], is[6]);
        }
        Set<Double> timeSet = new TreeSet<Double>();
        timeSet.add(0.);
        for (Ship ship : ships) {
            timeSet.add(ship.arrivalTime());
            double r = ship.radius;
            for (Point p : stars) {
                for (Point i : Circle_methods.isCL(p, r, ship.getOrbit(0), ship.getOrbit(1))) {
                    double d = ship.when(i);
                    if (d >= 0) {
                        timeSet.add(d);
                    }
                }
            }
        }
        double[] times = Cast.toDoubleArray(timeSet);
        int ITV = times.length - 1;
        Graph graph = new Graph(1 + SHIP + (SHIP + STAR) * ITV + 1);
        for (int i = 0; i < SHIP; i++) graph.add(new net.ogiekako.algorithm.graph.FlowEdge(0, 1 + i, ships[i].energy));
        for (int i = 0; i < times.length - 1; i++) {
            double length = times[i + 1] - times[i];
            double when = (times[i + 1] + times[i]) / 2;
            for (int j = 0; j < SHIP; j++) {
                int sp = 1 + j;
                int sp2 = 1 + SHIP + (SHIP + STAR) * i + j;
                graph.add(new net.ogiekako.algorithm.graph.FlowEdge(sp, sp2, Integer.MAX_VALUE));
                Point place = ships[j].where(when);
                for (int k = 0; k < STAR; k++) {
                    int str = 1 + SHIP + (SHIP + STAR) * i + SHIP + k;
                    if (when < ships[j].arrivalTime() && place.dist(stars[k]) < ships[j].radius) {
                        graph.add(new net.ogiekako.algorithm.graph.FlowEdge(sp2, str, length));
                    }
                }
            }
            for (int k = 0; k < STAR; k++) {
                int str = 1 + SHIP + (SHIP + STAR) * i + SHIP + k;
                int fin = 1 + SHIP + (SHIP + STAR) * ITV;
                graph.add(new net.ogiekako.algorithm.graph.FlowEdge(str, fin, length));
            }
        }

        return GraphAlgorithm.maxFlow(graph, 0, 1 + SHIP + (SHIP + STAR) * ITV);
    }

    class Ship extends Track {
        double energy;
        double radius;

        Ship(Point[] orbit, double speed, double radius, double energy) {
            super(orbit, speed);
            this.radius = radius;
            this.energy = energy;
        }
    }
}

