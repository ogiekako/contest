package net.ogiekako.algorithm.grid;

import net.ogiekako.algorithm.dataStructure.Interval;

public class Cut {

    public static enum Orientation {
        VERTICAL,
        HORIZONTAL
    }
    final Orientation orientation;
    final int origin;
    final Interval interval;

    public Cut(int row0, int col0, int row1, int col1){
        if(row0 != row1 && col0 != col1)throw new IllegalArgumentException("row0 and row1 or col0 and col1 must be same.");
        if(row0 == row1){
            orientation = Orientation.HORIZONTAL;
            origin = row0;
            interval = Interval.of(col0, col1);
        }else{
            orientation = Orientation.VERTICAL;
            origin = col0;
            interval = Interval.of(row0, row1);
        }
    }

    public Cut(Orientation orientation, int origin, int from, int to){
        this(orientation, origin, Interval.of(from, to));
    }

    public Cut(Orientation orientation, int origin, Interval interval) {
        this.orientation = orientation;
        this.origin = origin;
        this.interval = interval;
    }
}
