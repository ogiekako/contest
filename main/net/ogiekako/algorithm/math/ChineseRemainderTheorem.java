package net.ogiekako.algorithm.math;

import java.math.BigInteger;

public class ChineseRemainderTheorem {
    // 0 <= result < \prod moduli[i]
    // all moduli must be pairwise coprime.
    public static BigInteger crt(long[] remainders, long[] moduli) {// tested
        return crt(remainders, moduli, moduli.length);
    }

    public static BigInteger crt(long[] remainders, long[] moduli, int length) {
        BigInteger m = BigInteger.valueOf(moduli[0]);
        BigInteger d = BigInteger.valueOf(remainders[0]);
        for(int i=1;i<length;i++){
            BigInteger modulus = BigInteger.valueOf(moduli[i]);
            BigInteger remainder = BigInteger.valueOf(remainders[i]);
            BigInteger[] xy = ExEuclid.exGcd(m, modulus);
            BigInteger mm = m.multiply(modulus);
            d = m.multiply(remainder).multiply(xy[0]) . add(d.multiply(modulus).multiply(xy[1])) . mod(mm);
            if(d.signum() < 0)throw new AssertionError();
            m = mm;
        }
        return d;
    }
}
