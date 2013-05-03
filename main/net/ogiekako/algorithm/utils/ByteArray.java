package net.ogiekako.algorithm.utils;

import java.util.Arrays;

public class ByteArray {
    public final byte[] is;

    public ByteArray(byte[] is) {
        this.is = is;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteArray byteArray = (ByteArray) o;

        if (!Arrays.equals(is, byteArray.is)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(is);
    }

    public boolean contains(byte target) {
        for (byte i : is) if (i == target) return true;
        return false;
    }

    public int length() {
        return is.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(is);
    }
}
