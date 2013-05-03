package net.ogiekako.algorithm.utils;

public class CharArray_Uncopied implements CharSequence {
    char[] cs;

    public CharArray_Uncopied(char[] cs) {
        this.cs = cs;
    }

    public int length() {
        return cs.length;
    }

    public char charAt(int index) {
        return cs[index];
    }

    public CharSequence subSequence(int start, int end) {
        return new CharArray_Uncopied(ArrayUtils.subArray(cs, start, end));
    }
}
