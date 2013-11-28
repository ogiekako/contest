package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.math.MathUtils;

import java.util.HashMap;

public class ArtShift {
    public int maxShifts(String sequence) {
        int B = 0, W = 0;
        for (char c : sequence.toCharArray()) {
            if (c == 'B') B++;
            else W++;
        }
        return dfs(B, W, 1);
    }
    HashMap<Long, Integer> memo = new HashMap<Long, Integer>();
    private int dfs(int B, int W, int value) {
        if (B == 0 || W == 0) return value;
        int res = 0;
        long key = B + W * 30 + value * 900L;
        if (memo.containsKey(key)) return memo.get(key);
        for (int b = 1; b <= B; b++)
            for (int w = 1; w <= W; w++) {
                res = Math.max(res, dfs(B - b, W - w, lcm(value, b + w)));
            }
        memo.put(key, res);
        return res;
    }

    private int lcm(int a, int b) {
        return (int) (a * b / MathUtils.gcd(a, b));
    }
}
