package on2015_08.on2015_08_29_TopCoder_SRM__637.PathGame;



import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

public class PathGame {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

    String win = "Snuke", lose = "Sothe";

    public String judge(String[] board) {
        String S = "";
        for (int i = 0; i < board[0].length(); i++) {
            S += (board[0].charAt(i) == '.' ? 0 : 1) + (board[1].charAt(i) == '.' ? 0 : 2);
        }
        S = "0" + S + "0";
        ArrayUtils.fill(memo, -1);
        S = S.replaceAll("1", "1;1");
        S = S.replaceAll("2", "2;2");
        int grundy = 0;
        for (String s : S.split(";")) {
            grundy ^= grundy(s.charAt(0) - '0', s.charAt(s.length() - 1) - '0', s.length() - 2);
        }
        return grundy == 0 ? lose : win;
    }

    int[][][] memo = new int[4][4][1001];

    private int grundy(int left, int right, int length) {
        if (length < 0) return 0;
        if (memo[left][right][length] >= 0) return memo[left][right][length];
        BitSet possible = new BitSet();
        for (int j = 1; j <= 2; j++) {
            for (int i = 0; i < length; i++) {
                if (i == 0 && left == 3 - j) continue;
                if (i == length - 1 && right == 3 - j)continue;
                int g = grundy(left, j, i) ^ grundy(j, right, length - i - 1);
                possible.set(g);
            }
        }
        return memo[left][right][length] = possible.nextClearBit(0);
    }
}
