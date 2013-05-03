package net.ogiekako.algorithm.utils;

import java.math.BigInteger;
import java.util.Random;

public class ChallengeUtils {
    public static long generateRandomPrime(long from, long to) {
        return generateRandomPrime(from, to, new Random());
    }

    public static long generateRandomPrime(long from, long to, Random random) {
        for(int iteration=0;iteration<100000;iteration++){
            long value = (long) (from + random.nextDouble() * (to - from));
            if(BigInteger.valueOf(value).isProbablePrime(30))return value;
        }
        throw new IllegalArgumentException("no prime number was found");
    }
}
