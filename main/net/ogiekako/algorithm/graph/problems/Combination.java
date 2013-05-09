package net.ogiekako.algorithm.graph.problems;

import net.ogiekako.algorithm.math.MathUtils;

public class Combination {
    /**
     * http://codeforces.com/problemset/problem/156/D
     * http://translate.google.co.jp/translate?sl=auto&tl=en&js=n&prev=_t&hl=ja&ie=UTF-8&layout=2&eotf=1&u=http%3A%2F%2Fcodeforces.com%2Fblog%2Fentry%2F4005&act=url
     */
    public static long numWayToUnionComponentsWithMinimumEdges(int[] sizes, int modulo) {
        int numComp = sizes.length;
        if (numComp == 1) {
            return 1 % modulo;
        } else {
            long res = 1 % modulo;
            long n = 0;
            for (int size : sizes) {
                res = res * size % modulo;
                n += size;
            }
            res = res * MathUtils.powMod(n, numComp - 2, modulo) % modulo;
            return res;
        }
    }
}
