package net.ogiekako.algorithm.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class PrimeDecomposition extends ArrayList<PrimePower> {
    public PrimeDecomposition(int size) {
        super(size);
    }
    public PrimeDecomposition(){
    }

    public static PrimeDecomposition factorize(int number, int[] factorTable) {
        if (number <= 0) throw new IllegalArgumentException();
        PrimeDecomposition result = new PrimeDecomposition();
        while (number > 1) {
            int prime = factorTable[number];
            int power = 1;
            number /= prime;
            while (number % prime == 0) {
                power++;
                number /= prime;
            }
            result.add(new PrimePower(prime, power));
        }
        Collections.sort(result);
        return result;
    }

    public long divisorCount() {
        long res = 1;
        for (PrimePower f : this) {
            res *= f.power + 1;
        }
        return res;
    }

    /**
     * in sorted order.
     */
    public static PrimeDecomposition factorize(long number) {
        PrimeDecomposition result = new PrimeDecomposition();
        for (long prime = 2; prime * prime <= number; prime++) {
            if (number % prime == 0) {
                int power = 1;
                number /= prime;
                while (number % prime == 0) {
                    power++;
                    number /= prime;
                }
                result.add(new PrimePower(prime, power));
            }
        }
        if (number > 1) {
            result.add(new PrimePower(number, 1));
        }
        return result;
    }

    public long totient() {
        long res = 1;
        for (PrimePower f : this) {
            res *= MathUtils.power(f.prime, f.power - 1) * (f.prime - 1);
        }
        return res;
    }

    public long[] divisors() {
        long numDiv = this.divisorCount();
        if (numDiv > Integer.MAX_VALUE) throw new RuntimeException("Too many divisors");
        long[] res = new long[(int) numDiv];
        counterDivisors = 0;
        dfsDivisors(res, 0, 1L);
        Arrays.sort(res);
        return res;
    }

    int counterDivisors;

    private void dfsDivisors(long[] res, int i, long number) {
        if (i == this.size()) {
            res[counterDivisors++] = number;
            return;
        }
        PrimePower f = this.get(i);
        for (int p = 0; p <= f.power; p++) {
            dfsDivisors(res, i + 1, number);
            number *= f.prime;
        }
    }

    private static int[] dpTotientChainLength;
    /*
        res[12] = 3; 12 -> 4 -> 2 -> 1.
        結局,何個2が出てくるかで決まる.
        初めに2がなければ +1.
     */
    public long totientChainLength() {
        int MX = 1000010;
        if (dpTotientChainLength == null) {
            int[] divisors = MathUtils.generateDivisorTable(MX);
            dpTotientChainLength = new int[MX];
            dpTotientChainLength[2] = 1;
            for (int i = 3; i < MX; i++) {
                int j = i - 1;
                while (j > 1) {
                    dpTotientChainLength[i] += dpTotientChainLength[divisors[j]];
                    j /= divisors[j];
                }
            }
        }
        long res = 1;
        for (PrimePower f : this) {
            if (f.prime >= dpTotientChainLength.length)
                throw new IllegalArgumentException(String.format("prime >= %d", MX));
            if (f.prime == 2) res--;
            res += (long) dpTotientChainLength[(int) f.prime] * f.power;
        }
        return res;
    }

    public int numberOfPrimeDivisorsWithMultiplicity() {
        int res = 0;
        for(PrimePower pp : this)res += pp.power;
        return res;
    }
}
