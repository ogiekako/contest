package net.ogiekako.algorithm.misc;

import java.util.*;

public class MaxRectangle {
    public static int maxRectangle(boolean[][] grid) {
        int res = 0;
        int[] rect = maximalRectangles(grid);
        for (int i = 0; i < rect.length; i++) {
            res = Math.max(res, i * rect[i]);
        }
        return res;
    }

    /**
     * Computes a list containing all the maximal rectangles in linear time.
     * Returned array contains the max height per width. (res[w] = h).
     * Verified: ARC 081 F - Flip and Rectangles
     */
    public static int[] maximalRectangles(int[] histogram) {
        class E {
            int height;
            int pos;
        }

        histogram = Arrays.copyOf(histogram, histogram.length + 1);
        int[] res = new int[histogram.length];

        Stack<E> S = new Stack<>();
        for (int i = 0; i < histogram.length; i++) {
            E e = new E();
            e.height = histogram[i];
            e.pos = i;
            if (S.isEmpty()) {
                S.push(e);
            } else {
                if (S.peek().height < e.height) {
                    S.push(e);
                } else if (S.peek().height > e.height) {
                    int target = i;
                    while (!S.empty() && S.peek().height >= e.height) {
                        E pre = S.pop();
                        int w = i - pre.pos;
                        res[w] = Math.max(res[w], pre.height);
                        target = pre.pos;
                    }
                    e.pos = target;
                    S.push(e);
                }
            }
        }
        return res;
    }

    /**
     * Computes maximal rectangles in linear time.
     * Returned array contains the max height per width (res[w] = h).
     * It may contain non-maximal rectangles.
     * Verified: ARC 081 F - Flip and Rectangles
     */
    public static int[] maximalRectangles(boolean[][] grid) {
        if (grid.length == 0) return new int[0];
        int[] res = new int[grid[0].length + 1];
        int[] histogram = new int[grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                histogram[j] = grid[i][j] ? histogram[j] + 1 : 0;
            }
            int[] cur = maximalRectangles(histogram);
            for (int j = 0; j < res.length; j++) {
                res[j] = Math.max(res[j], cur[j]);
            }
        }
        return res;
    }

}
