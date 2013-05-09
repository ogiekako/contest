package net.ogiekako.algorithm.utils;

import net.ogiekako.algorithm.math.ChineseRemainderTheorem;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class BigIntegerUtils {
    public static final BigInteger ZERO = BigInteger.ZERO;
    public static final BigInteger ONE = BigInteger.ONE;
    public static final BigInteger TWO = BigInteger.valueOf(2);
    public static final BigInteger THREE = BigInteger.valueOf(3);
    public static final BigInteger FOUR = BigInteger.valueOf(4);
    public static final BigInteger FIVE = BigInteger.valueOf(5);
    public static final BigInteger SIX = BigInteger.valueOf(6);
    public static final BigInteger SEVEN = BigInteger.valueOf(7);
    public static final BigInteger EIGHT = BigInteger.valueOf(8);
    public static final BigInteger NINE = BigInteger.valueOf(9);
    public static final BigInteger TEN = BigInteger.valueOf(10);

    public static interface Function {
        int calculate(int primeMod);
    }
    // 大きい素数で割り切れる場合，うまくいかない．
    public static BigInteger computeUsingCRT(Function f) {
        BigInteger result = BigInteger.ZERO;

        List<Long> values = new ArrayList<Long>();
        List<Long> mods = new ArrayList<Long>();
        for (int mod = Integer.MAX_VALUE / 2; ; mod--) {
            if (BigInteger.valueOf(mod).isProbablePrime(30)) {
                int value = f.calculate(mod);
                values.add((long) value);
                mods.add((long) mod);
                BigInteger current = ChineseRemainderTheorem.crt(Cast.toLong(values), Cast.toLong(mods));
                if (current.equals(result)) break;
                result = current;
            }
        }
        return result;
    }
}
