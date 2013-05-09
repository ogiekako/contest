package net.ogiekako.algorithm.math;

import net.ogiekako.algorithm.utils.IntegerUtils;

public class PrimePower implements Comparable<PrimePower> {
    public final long prime;
    public final int power;
    public PrimePower(long prime, int power) {
        this.prime = prime;
        this.power = power;
    }

    public int compareTo(PrimePower o) {
        return IntegerUtils.compare(prime, o.prime);
    }

    @Override
    public String toString() {
        return String.format("%d^%d", prime, power);
    }
}