package net.ogiekako.algorithm.math.algebra;

import net.ogiekako.algorithm.math.MathUtils;

public class Mint implements Field<Mint> {
    public static int modPrime;
    public final long value;

    public Mint(long value) {
        this.value = value;
    }

    public static void setMod(int modPrime) {
        Mint.modPrime = modPrime;
    }

    public static Mint of(long value) {
        if (value >= modPrime) value = value % modPrime;
        if (value < 0) {
            value = value % modPrime;
            if (value < 0) value += modPrime;
        }
        return new Mint(value);
    }

    public Mint mulInv() {
        return of(MathUtils.inverse(value, modPrime));
    }

    public Mint mul(Mint other) {
        return of(value * other.value % modPrime);
    }

    public Mint add(Mint other) {
        long nValue = value + other.value;
        if (nValue >= modPrime) nValue -= modPrime;
        return of(nValue);
    }

    public Mint addInv() {
        if (value == 0) return of(0);
        return of(modPrime - value);
    }

    public boolean isZero() {
        return value % modPrime == 0;
    }

    public Mint zero() {
        return of(0);
    }
}
