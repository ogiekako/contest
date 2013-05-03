package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class Palindrome {
    /*
     s[i]を中心とする回文の半径を i 要素としてもつ配列を返す.
     aba のb 中心の半径は1 となる.
     奇数長の回文しか考えない.偶数長の回文も考えたい場合は,各文字をダミーで挟んだ文字列を与え,
     適宜変換すること.

     Manacherのアルゴリズム.
     */
    public static int[] palindromeRadius(String s) {
        int n = s.length();
        int[] rad = new int[n];
        for (int i = 0, j = 0; i < n; ) {
            while (j < n && i + i - j >= 0 && s.charAt(j) == s.charAt(i + i - j)) j++;
            rad[i] = j - i - 1;
            for (int bi = i++; i < j && rad[bi + bi - i] != rad[bi] + bi - i; i++) {
                rad[i] = Math.min(rad[bi] + bi - i, rad[bi + bi - i]);
            }
        }
        return rad;
    }

    /**
     * upToまでの回分数を,小さい順に収めた配列を返す.
     * O(sqrt(upTo)).
     *
     * @param upTo
     * @return
     */
    public static long[] generatePalindromicNumbers(long upTo) {
        if (upTo < 0) return new long[0];
        List<Long> list = new ArrayList<Long>();
        list.add(0L);
        for (int numDigit = 1; ; numDigit++) {
            boolean even = numDigit % 2 == 0;
            long M = MathUtils.power(10, (numDigit + 1) / 2);
            long M2 = even ? M : M / 10;
            for (long higher = M / 10; higher < M; higher++) {
                long lower = IntegerUtils.reverse10(even ? higher : higher / 10, numDigit / 2);
                long number = higher * M2 + lower;
                if (number > upTo) {
                    return Cast.toLong(list);
                }
                list.add(number);
            }
        }
    }
}
