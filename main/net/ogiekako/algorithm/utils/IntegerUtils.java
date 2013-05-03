package net.ogiekako.algorithm.utils;

import java.math.BigInteger;

public class IntegerUtils {
    public static final long[] tens = {
            1,
            10,
            100,
            1000,
            10000,
            100000,
            1000000,
            10000000,
            100000000,
            1000000000,
            10000000000L,
            100000000000L,
            1000000000000L,
            10000000000000L,
            100000000000000L,
            1000000000000000L,
            10000000000000000L,
            100000000000000000L,
            1000000000000000000L,
    };

    public static int reverse(int i, int lower) {
        if(lower < 0  || 32 < lower)throw new IllegalArgumentException("" + lower);
        if(lower == 0)return i;
        if(lower == 32)return Integer.reverse(i);
        int msk = Integer.reverse(i) >>> (32 - lower);
        return ((i >>> lower) << lower) | msk;
    }

    public static int lg2(int i) {
        if (i <= 0) throw new IllegalArgumentException();
        return 31 - Integer.numberOfLeadingZeros(i);
    }

    public static int digitCount(int number, int digit) {
        if (digit < 0 || digit >= 10 || number < 0) throw new IllegalArgumentException();
        if (number == 0) {
            if (digit == 0) return 1;
            return 0;
        }
        int res = 0;
        while (number > 0) {
            int d = number % 10;
            if (d == digit) res++;
            number /= 10;
        }
        return res;
    }

    public static long reverse10(long number, int digit) {
        long res = 0;
        for (int i = 0; i < digit; i++) {
            res *= 10;
            res += number % 10;
            number /= 10;
        }
        return res;
    }

    public static int compare(long o1, long o2) {
        return o1 < o2 ? -1 : o1 > o2 ? 1 : 0;
    }

    public static BigInteger pow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while(exponent.compareTo(BigInteger.ZERO) > 0){
            if(exponent.testBit(0)){
                result = result.multiply(base);
            }
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    public static int floorHalf(int i) {
        if(i<0)return (i-1)>>1;
        return i>>1;
    }
}
