package net.ogiekako.algorithm.math;

import junit.framework.Assert;
import net.ogiekako.algorithm.utils.ChallengeUtils;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class ChineseRemainderTheoremTest {
    @Test
    public void testCrt() throws Exception {
        int n = 5;
        Random rnd = new Random(12124L);
        for (int iteration = 0; iteration < 50; iteration++) {
            long[] remainders = new long[n], moduli = new long[n];
            long exp = (long) (rnd.nextDouble() * Long.MAX_VALUE);
            for (int i = 0; i < n; i++) {
                moduli[i] = ChallengeUtils.generateRandomPrime(0, 1000, rnd);
                moduli[i] *= ChallengeUtils.generateRandomPrime(0, 1000, rnd);
                remainders[i] = exp % moduli[i];
                boolean ok = true;
                for (int j = 0; j < i; j++)
                    if (MathUtils.gcd(moduli[i], moduli[j]) != 1) {
                        ok = false;
                    }
                if (!ok) i--;
            }
            for (int i = 0; i < n; i++)
                for (int j = 0; j < i; j++) if (MathUtils.gcd(moduli[i], moduli[j]) != 1) throw new AssertionError();
            BigInteger res = ChineseRemainderTheorem.crt(remainders, moduli);
            for (int i = 0; i < n; i++) {
                BigInteger tmp = res.remainder(BigInteger.valueOf(moduli[i]));
                Assert.assertEquals(remainders[i], tmp.longValue());
            }
            Assert.assertEquals(exp, res.longValue());
        }
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }
}
