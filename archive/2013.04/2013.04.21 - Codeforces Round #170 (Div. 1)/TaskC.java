package src;

import net.ogiekako.algorithm.dataStructure.interval.Interval;
import net.ogiekako.algorithm.dataStructure.interval.IntervalSet;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class TaskC {
    class Cut {
        int axis;
        IntervalSet intervalSet;

        public Cut(Integer axis, IntervalSet intervalSet) {
            this.axis = axis;
            this.intervalSet = intervalSet;
        }
    }

    int height, width;
    Map<Integer, IntervalSet> xSet = new TreeMap<Integer, IntervalSet>();
    Map<Integer, IntervalSet> ySet = new TreeMap<Integer, IntervalSet>();
    List<Cut> xCuts = new ArrayList<Cut>();
    List<Cut> yCuts = new ArrayList<Cut>();
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        height = in.nextInt();
        width = in.nextInt();
        int k = in.nextInt();
        for (int i = 0; i < k; i++) {
            add(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
        }
        for (Map.Entry<Integer, IntervalSet> entry : xSet.entrySet())
            xCuts.add(new Cut(entry.getKey(), entry.getValue()));
        for (Map.Entry<Integer, IntervalSet> entry : ySet.entrySet())
            yCuts.add(new Cut(entry.getKey(), entry.getValue()));
        int xor = calcXor();
        if (xor == 0) {
            out.println("SECOND");
            return;
        }
        out.println("FIRST");

        calcMove(xor, out);
    }

    private void calcMove(int xor, MyPrintWriter out) {
        boolean ok = calcMove(xor, height, width, xCuts, true, out);
        if (!ok) calcMove(xor, width, height, yCuts, false, out);
    }

    private boolean calcMove(int xor, int height, int width, List<Cut> xCuts, boolean isX, MyPrintWriter out) {
        int empty = height - 1 - xCuts.size();
        if (empty > 0 && move(xor, width) > 0) {
            IntervalSet set = new IntervalSet();
            set.add(new Interval(0, width));
            Interval cut = calcCut(move(xor, width), set);
            print(out, firstEmpty(xCuts), cut, isX);
            return true;
        }
        for (Cut cut : xCuts) {
            IntervalSet all = new IntervalSet();
            all.add(Interval.of(0, width));
            IntervalSet rests = cut.intervalSet.xored(all);
            int pile = (int) rests.cardinality();
            if (move(xor, pile) > 0) {
                Interval iv = calcCut(move(xor, pile), rests);
                print(out, cut.axis, iv, isX);
                return true;
            }
        }
        return false;
    }

    private Interval calcCut(int cut, IntervalSet rest) {
        for (Interval i : rest.toArray()) {
            long len = i.length();
            if (cut <= len) {
                return Interval.of(0, i.left + cut);
            }
            cut -= len;
        }
        throw new AssertionError();
    }

    private void print(MyPrintWriter out, int o, Interval interval, boolean isX) {
        if (isX) out.printFormat("%d %d %d %d\n", o, interval.left, o, interval.right);
        else out.printFormat("%d %d %d %d\n", interval.left, o, interval.right, o);
    }

    private int firstEmpty(List<Cut> cuts) {
        for (int i = 1; ; i++) {
            if (i > cuts.size() || cuts.get(i - 1).axis != i) return i;
        }
    }

    private int move(int xor, int pile) {
        xor ^= pile;
        if (xor < pile) return pile - xor;
        return -1;
    }

    private int calcXor() {
        return calcXor(height, width, xCuts) ^ calcXor(width, height, yCuts);
    }

    private int calcXor(int height, int width, List<Cut> xCuts) {
        int empty = height - 1 - xCuts.size();
        int res = 0;
        if ((empty & 1) == 1) res ^= width;
        for (Cut cut : xCuts) {
            res ^= calcXor(width, cut.intervalSet);
        }
        return res;
    }

    private int calcXor(int length, IntervalSet intervalSet) {
        return (int) (length - intervalSet.cardinality());
    }

    private void add(int xb, int yb, int xe, int ye) {
        if (xb == xe) {
            put(xSet, xb, Interval.of(yb, ye));
        } else {
            put(ySet, yb, Interval.of(xb, xe));
        }
    }

    private void put(Map<Integer, IntervalSet> map, int o, Interval interval) {
        if (!map.containsKey(o)) map.put(o, new IntervalSet());
        map.get(o).add(interval);
    }
}
