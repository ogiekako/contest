package net.ogiekako.algorithm.grid;


import net.ogiekako.algorithm.dataStructure.IntervalSet;

import java.util.HashMap;

/**
 * upper right (0, 0)
 * lower left (height, width)
 */
public class Paper {
    private final int height, width;
    private final HashMap<Integer, IntervalSet> horizontalCuts = new HashMap<Integer, IntervalSet>();
    private final HashMap<Integer, IntervalSet> verticalCuts = new HashMap<Integer, IntervalSet>();
    public Paper(int height, int width){
        if(height < 0)throw new IllegalArgumentException("height must be positive.");
        if(width < 0)throw new IllegalArgumentException("width must be positive.");
        this.height = height;
        this.width = width;
    }
    public void cut(int row0, int col0, int row1, int col1){
        cut(new Cut(row0,col0,row1,col1));
    }

    public void cut(Cut cut) {
        IntervalSet set = getIntervalSetFor(cut);
        set.add(cut.interval);
    }

    private IntervalSet getIntervalSetFor(Cut cut) {
        switch (cut.orientation){
            case HORIZONTAL:
                IntervalSet res = horizontalCuts.get(cut.origin);
                if(res == null){
                    res = new IntervalSet();
                    horizontalCuts.put(cut.origin, res);
                }
                return res;
            case VERTICAL:
                res = verticalCuts.get(cut.origin);
                if(res == null){
                    res = new IntervalSet();
                    verticalCuts.put(cut.origin, res);
                }
                return res;
        }
        throw new AssertionError();
    }
}
