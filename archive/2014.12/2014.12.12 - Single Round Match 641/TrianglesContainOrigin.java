package src;

public class TrianglesContainOrigin {
    public long count(int[] x, int[] y) {
        long n = x.length;
        long res = n*(n-1)*(n-2)/6;
        for (int i = 0; i < x.length; i++) {
            long cnt = 0;
            for (int j = 0; j < x.length; j++) if (i!=j) {
                if(0 < det(x[i],y[i],x[j],y[j])) {
                    cnt++;
                }
            }
            res -= cnt * (cnt-1) / 2;
        }
        return res;
    }

    private long det(long x1, long y1, long x2, long y2) {
        return x1 * y2 - x2 * y1;
    }

}
