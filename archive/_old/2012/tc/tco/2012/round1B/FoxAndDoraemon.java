package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;

public class FoxAndDoraemon {
    public int minTime(int[] workCost, int splitCost) {
        Arrays.sort(workCost);
        memo = new int[workCost.length + 1][workCost.length + 1];
        ArrayUtils.fill(memo, -1);
        return solve(0, workCost.length, workCost, splitCost);
    }
    private int solve(int left, int right, int[] workCost, int splitCost) {
        if (right - left == 1) return workCost[left];
        if (memo[left][right] >= 0) return memo[left][right];
        int res = Integer.MAX_VALUE;
        for (int mid = left + 1; mid < right; mid++) {
            res = Math.min(res, Math.max(solve(left, mid, workCost, splitCost), solve(mid, right, workCost, splitCost)) + splitCost);
        }
        return memo[left][right] = res;
    }

    int[][] memo;


}

