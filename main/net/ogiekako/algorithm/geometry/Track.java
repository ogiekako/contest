package net.ogiekako.algorithm.geometry;

import net.ogiekako.algorithm.utils.Algorithm;

/*
orbit[0] -> orbit[1] -> ...
と等速直線運動.
時刻0 から動き始め,
単位時間に speed 進む.

Problems:
    SRM 1000 死兆星のやつ
 */
public class Track {
    public final Point[] orbit;
    public final double speed;
    public Track(Point[] orbit, double speed) {
        this.orbit = orbit.clone();
        this.speed = speed;
    }

    public double totalLength() {
        double res = 0;
        for (int i = 0; i < orbit.length - 1; i++) {
            res += orbit[i].distance(orbit[i + 1]);
        }
        return res;
    }

    /*
     終点に達している場合は,終点座標を返す.
     */
    public Point where(double when) {
        if (when < 0) throw new IllegalArgumentException();
        double time = 0;
        for (int i = 0; i < orbit.length - 1; i++) {
            double took = orbit[i].distance(orbit[i + 1]) / speed;
            double nextTime = time + took;
            if (when < nextTime) {
                double p = (when - time) / took;
                return orbit[i].mul(1 - p).add(orbit[i + 1].mul(p));
            }
            time = nextTime;
        }
        return orbit[orbit.length - 1];
    }

    /*
    二度以上通る場合は, 始めに通る時刻を返す.
    通らない場合, -1を返す.

    通る判定は, crsSPで.
     */
    public double when(Point where) {
        double length = 0;
        for (int i = 0; i < orbit.length - 1; i++) {
            if (new Segment(orbit[i], orbit[i + 1]).intersect(where))
                return (length + orbit[i].distance(where)) / speed;
            length += orbit[i].distance(orbit[i + 1]);
        }
        return -1;
    }

    /*
     * manPositionにいる,速さmanSpeedの人が manStartTimeから走り始めたときに this に追いつく時刻を返す.
     * ただし, 人の速さは this より速いとする.
     * 最後まで追いつけないときは -1 を返す.
     */
    public double getReachableTime(final Point manPosition, final double manSpeed, final double manStartTime) {
        if (speed > manSpeed) throw new IllegalArgumentException();
        double totalLength = totalLength();
        double endTime = totalLength / speed;

        double res = Algorithm.binarySearch(0, endTime, new Algorithm.BinSearchFilter() {
            public boolean isUpperBound(double value) {
                Point p = where(value);
                double reachTime = manStartTime + p.distance(manPosition) / manSpeed;
                return reachTime < value;
            }
        });

        return res == endTime ? -1 : res;
    }

    public Point getOrbit(int at) {
        return orbit[at];
    }

    public double arrivalTime() {
        return totalLength() / speed;
    }
}
