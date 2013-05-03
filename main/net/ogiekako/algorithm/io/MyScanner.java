package net.ogiekako.algorithm.io;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class MyScanner {
    private final InputStream in;

    public MyScanner(InputStream in) {
        this.in = in;
    }

    int bufLen;
    int bufPtr;
    byte[] buf = new byte[1024];

    public int read() {
        if (bufLen == -1)
            throw new InputMismatchException();
        if (bufPtr >= bufLen) {
            bufPtr = 0;
            try {
                bufLen = in.read(buf);
            } catch (IOException e) {
                throw new InputMismatchException();
            }
            if (bufLen <= 0)
                return -1;
        }
        return buf[bufPtr++];
    }

    public String nextLine() {
        StringBuilder res = new StringBuilder("");
        int c = read();
        while (c == '\n' || c == '\r') {
            c = read();
        }
        if (c == -1) throw new NoSuchElementException();
        do {
            res.append((char) c);
            c = read();
        } while (c != '\n' && c != '\r' && c != -1);
        return res.toString();
    }

    public char nextChar() {
        try {
            int c = read();
            if (c == -1) return (char) c;
            while (Character.isWhitespace(c)) {
                c = read();
                if (c == -1) return (char) c;
            }
            return (char) c;
        } catch (Exception e) {
            return (char) -1;
        }
    }

    private char[] strBuf = new char[65536];

    public String next() {
        int strLen = 0;
        int c;
        do {
            c = read();
            if (c == -1) throw new NoSuchElementException();
        } while (Character.isWhitespace(c));
        do {
            if (strLen + 1 >= strBuf.length) {
                char[] tmp = new char[strBuf.length * 2];
                System.arraycopy(strBuf, 0, tmp, 0, strBuf.length);
                strBuf = tmp;
            }
            strBuf[strLen++] = (char) c;
            c = read();
        } while (c != -1 && !Character.isWhitespace(c));
        return new String(strBuf, 0, strLen);
    }

    public int nextInt() {
        int c = read();
        if (c == -1) throw new NoSuchElementException();
        while (c != '-' && (c < '0' || '9' < c)) {
            c = read();
            if (c == -1) throw new NoSuchElementException();
        }
        if (c == '-') return -nextInt();
        int res = 0;
        do {
            res *= 10;
            res += c - '0';
            c = read();
        } while ('0' <= c && c <= '9');
        return res;
    }

    public long nextLong() {
        try {
            int c = read();
            if (c == -1) return c;
            while (c != '-' && (c < '0' || '9' < c)) {
                c = read();
                if (c == -1) return c;
            }
            if (c == '-') return -nextLong();
            long res = 0;
            do {
                res *= 10;
                res += c - '0';
                c = read();
            } while ('0' <= c && c <= '9');
            return res;
        } catch (Exception e) {
            return -1;
        }
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public int[] nextIntArray(int n) {
        int[] res = new int[n];
        for (int i = 0; i < n; i++) res[i] = nextInt();
        return res;
    }

    public int[][] nextIntField(int height, int width) {
        int[][] res = new int[height][width];
        for (int i = 0; i < height; i++) for (int j = 0; j < width; j++) res[i][j] = nextInt();
        return res;
    }

    public long[] nextLongArray(int n) {
        long[] res = new long[n];
        for (int i = 0; i < n; i++) res[i] = nextLong();
        return res;
    }

    public long[][] nextLongArray(int n1, int n2) {
        long[][] res = new long[n1][n2];
        for (int i = 0; i < n1; i++)
            for (int j = 0; j < n2; j++) {
                res[i][j] = nextLong();
            }
        return res;
    }

    public String[] nextStringArray(int n) {
        String[] res = new String[n];
        for (int i = 0; i < n; i++) res[i] = next();
        return res;
    }


    public void readArrays(int[]... arrays) {
        for (int i = 0; i < arrays[0].length; i++) for (int j = 0; j < arrays.length; j++) arrays[j][i] = nextInt();
    }

    public BigInteger nextBigInteger() {
        return new BigInteger(next());
    }

    public int[] nextIntArray() {
        int n = nextInt();
        return nextIntArray(n);
    }
}
