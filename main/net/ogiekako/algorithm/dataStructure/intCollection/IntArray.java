package net.ogiekako.algorithm.dataStructure.intCollection;

import java.util.Arrays;

public class IntArray {
    public final int[] is;

    public IntArray(int[] is) {
        this.is = is;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntArray intArray = (IntArray) o;

        if (!Arrays.equals(is, intArray.is)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(is);
    }

    public boolean contains(int target) {
        for (int i : is) if (i == target) return true;
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
