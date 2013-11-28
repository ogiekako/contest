package tmp;

public class AlternateColors {
    public String getColor(long r, long g, long b, long k) {
        long[] ns = {r, g, b};
        String[] names = {"RED", "GREEN", "BLUE"};
        for (int t = 3; ; ) {
            long m = Long.MAX_VALUE;
            for (int i = 0; i < t; i++) m = Math.min(m, ns[i]);
            if (m * t >= k) {
                return names[((int) ((k - 1) % t))];
            }
            k -= m * t;
            int nt = 0;
            for (int i = 0; i < t; i++) {
                ns[i] -= m;
                if (ns[i] > 0) {
                    ns[nt] = ns[i];
                    names[nt++] = names[i];
                }
            }
            t = nt;
        }
    }
}
