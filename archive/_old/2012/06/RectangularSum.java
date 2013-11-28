package tmp;

// Paste me into the FileEdit configuration dialog

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RectangularSum {
    public long minimalArea(int height, int width, long S) {
        S *= 2;
        int[] dxs = gen(height, S);
        int[] dys = gen(width, S);
        long res = Long.MAX_VALUE;
        for (int dx : dxs) {
            long S2 = S / dx;
            for (int dy : dys) {
                if ((long) dy * dy > S2) break;
                if (S2 % dy == 0) {
                    long K = S2 / dy - (long) width * (dx - 1) - (dy - 1);
                    if ((K & 1) == 1 || K < 0) continue;
                    K >>= 1;
                    long x1 = K / width;
                    long y1 = K % width;
                    if (x1 <= height - dx && y1 <= width - dy) {
                        res = Math.min(res, dx * dy);
                    }
                }
            }
        }
        return res == Long.MAX_VALUE ? -1 : res;
    }

    public long minimalArea2(int h, int w, long S) {
        S *= 2;
        long height = h;
        long width = w;
        ArrayList<Long> cand = new ArrayList<Long>();
        for (long sz = 1; sz * sz <= S; sz++)
            if (S % sz == 0) {
                cand.add(sz);
                if (sz * sz != S)
                    cand.add(S / sz);
            }
        Collections.sort(cand);
        long ans = Long.MAX_VALUE;
        outer:
        for (int i = 0; i < cand.size(); i++) {
            long n = cand.get(i);
            if (n > width) continue;
            for (int j = 0; j < cand.size(); j++) {
                long m = cand.get(j);
                if (m > height) continue;
                long area = n * m;
                if (S % area != 0) continue;
                long x = S / area - (n - 1 + (m - 1) * width);
                if ((x & 1) == 1) continue;
                x /= 2;
                if (x < 0) continue;
                if (x % width + n > width) continue;
                if (x / width + m > height) continue;
                ans = Math.min(ans, area);
                continue outer;
            }
        }
        if (ans == Long.MAX_VALUE) ans = -1;
        return ans;
    }

    private int[] gen(int height, long S) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= height; i++) if (S % i == 0) list.add(i);
        return tois(list.toArray(new Integer[0]));
    }

    private int[] tois(Integer[] Is) {
        int[] is = new int[Is.length];
        for (int i = 0; i < is.length; i++) is[i] = Is[i];
        return is;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new RectangularSum().minimalArea2(1000000, 1000000, 963761198400L);
        long end = System.currentTimeMillis() - start;
        System.err.println(end);
        Random rnd = new Random();
        for (int h = 1; h < 100; h++)
            for (int w = 1; w < 100; w++) {
                System.err.println(h + " " + w);
                for (long S = 1; S <= 300; S++) {
                    long res = new RectangularSum().minimalArea(h, w, S);
                    long exp = solveStupid(h, w, S);
                    if (res != exp) {
                        System.err.println(h + " " + w + " " + S + " " + res + " " + exp);
                        throw new RuntimeException();
                    }
                }
            }
        test1(rnd);
    }

    private static long solveStupid(int h, int w, long S) {
        long res = Long.MAX_VALUE;
        for (int x1 = 0; x1 < h; x1++)
            for (int x2 = x1 + 1; x2 <= h; x2++)
                for (int y1 = 0; y1 < w; y1++)
                    for (int y2 = y1 + 1; y2 <= w; y2++) {
                        int sum = 0;
                        for (int x = x1; x < x2; x++)
                            for (int y = y1; y < y2; y++) {
                                sum += x * w + y;
                            }
                        if (sum == S) res = Math.min(res, (x2 - x1) * (y2 - y1));
                    }
        return res == Long.MAX_VALUE ? -1 : res;
    }

    static void test1(Random rnd) {
        long[] ten = new long[13];
        for (int i = 0; i < 13; i++) ten[i] = i == 0 ? 1 : ten[i - 1] * 10;
        for (; ; ) {
            int d1 = rnd.nextInt(7);
            int d2 = rnd.nextInt(7);
            int d3 = rnd.nextInt(13);
            int h = rnd.nextInt((int) ten[d1]) + 1;
            int w = rnd.nextInt((int) ten[d2]) + 1;
            long S = (long) (rnd.nextDouble() * ten[d3]) + 1;
            long res = new RectangularSum().minimalArea2(h, w, S);
            long exp = new RectangularSum().minimalArea(h, w, S);
            System.err.println(h + " " + w + " " + S + " " + res + " " + exp);
            if (res != exp) throw new AssertionError();
//            System.err.println(res);
        }
    }

    private long solve(int height, int width, long S) {
        S *= 2;
        int[] dxs = gen(height, S);
        int[] dys = gen(width, S);
        long res = Long.MAX_VALUE;
        for (int dx : dxs) {
            long S2 = S / dx;
            for (int dy : dys) {
//               if((long)dy*dy > S2)break;
                if (S2 % dy == 0) {
                    long K = S2 / dy - (long) width * (dx - 1) - (dy - 1);
                    if ((K & 1) == 1) continue;
                    K >>= 1;
                    long x1 = K / width;
                    long y1 = K % width;
                    if (x1 <= height - dx && y1 <= width - dy) {
                        res = Math.min(res, (long) dx * dy);
                    }
                }
            }
        }
        return res == Long.MAX_VALUE ? -1 : res;
    }
}

