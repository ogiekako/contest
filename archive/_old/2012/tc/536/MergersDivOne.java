package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.Arrays;

public class MergersDivOne {
    public double findMaximum(int[] revenues) {
        Arrays.sort(revenues);
        double cur = revenues[0];
        for (int r : revenues) cur = (cur + r) / 2;
        return cur;
    }
}