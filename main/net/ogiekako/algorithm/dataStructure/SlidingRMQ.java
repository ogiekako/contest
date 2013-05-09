package net.ogiekako.algorithm.dataStructure;
/*
 * is[] に対して,
 * [a1,b1),[a2,b2)...[an,bn) という範囲の最小値をクエリとする.ただし,
 * a1<=a2<=...<=an
 * b1<=b2<=...<=bn
 * を満たす.この時,線形時間で解ける.
 * [i,i) に対する答えはLong.MAX_VALUE としてある.
 * cf. http://wcipeg.com/wiki/Sliding_range_minimum_query
 */
public class SlidingRMQ {
    public static long[] slidingRMQ(long[] is, int[] as, int[] bs) {
        if (as.length != bs.length) throw new RuntimeException();
        for (int i = 0; i < as.length - 1; i++) if (as[i] > as[i + 1]) throw new RuntimeException();
        for (int i = 0; i < bs.length - 1; i++) if (bs[i] > bs[i + 1]) throw new RuntimeException();
        long[] res = new long[as.length];
        long[] que = new long[is.length];
        int head = 0, tail = 0;
        for (int i = 0, a = 0, b = 0; i < as.length; i++) {
            while (b < bs[i]) {
                while (head < tail && que[tail - 1] > is[b]) tail--;
                que[tail++] = is[b++];
            }
            while (a < as[i]) {
                if (head < tail && que[head] == is[a]) head++;
                a++;
            }
            res[i] = head == tail ? Long.MAX_VALUE : que[head];
        }
        return res;
    }
}
