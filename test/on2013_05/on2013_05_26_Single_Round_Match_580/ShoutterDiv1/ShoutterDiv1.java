package on2013_05.on2013_05_26_Single_Round_Match_580.ShoutterDiv1;


import java.util.Arrays;
public class ShoutterDiv1 {
    public int count(String[] s1000, String[] s100, String[] s10, String[] s1, String[] t1000, String[] t100, String[] t10, String[] t1) {
        int[] s = gen(s1000, s100, s10, s1);
        int[] t = gen(t1000, t100, t10, t1);
        debug(s);
        debug(t);
        int n = s.length;
        I[] is = new I[n];
        for (int i = 0; i < n; i++) is[i] = new I(s[i], t[i]);
        Arrays.sort(is);
        int[] left = new int[n], right = new int[n];
        for (int i = 0; i < n; i++) {
            left[i] = -1; right[i] = -1;
            boolean hasLeft = false;
            for (int j = 0; j < n; j++) {
                if (is[j].t < is[i].s) hasLeft = true;
                if (is[j].s < is[i].s && is[i].s <= is[j].t) {
                    if (left[i] == -1 || is[j].s < is[left[i]].s) left[i] = j;
                }
            }
            if (hasLeft && left[i] == -1) return -1; // !
            if (!hasLeft) left[i] = -1;// needless to go left
            boolean hasRight = false;
            for (int j = 0; j < n; j++) {
                if (is[i].t < is[j].s) hasRight = true;
                if (is[i].t < is[j].t && is[j].s <= is[i].t) {
                    if (right[i] == -1 || is[right[i]].t < is[j].t) right[i] = j;
                }
            }
            if (hasRight && right[i] == -1) return -1;   // !  no -> WA, or -> AC
            if (!hasRight) right[i] = -1;
        }
        int[] cost = new int[n];
        for (int i = 0; i < n; i++) {
            cost[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++)
                if (is[j].s <= is[i].s && is[i].t <= is[j].t) cost[i] = Math.min(cost[i], cost[j] + 1);
            int point = 0;
            int j = i;
            while (right[j] >= 0) {
                point++;
                j = right[j];
            }
            j = i;
            while (left[j] >= 0) {
                point++;
                j = left[j];
            }
            cost[i] = Math.min(cost[i], point);
            if (cost[i] == Integer.MAX_VALUE) return -1;
        }
        int res = 0;
        for (int c : cost) res += c;
        return res;
    }

    class I implements Comparable<I> {
        int s, t;
        I(int s, int t) {
            this.s = s; this.t = t;
        }
        @Override
        public int compareTo(I o) {
            if (s != o.s) return s - o.s;
            return -(t - o.t);
        }
    }

    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }
    private int[] gen(String[] s1000, String[] s100, String[] s10, String[] s1) {
        int[] s = null;
        for (String[] ss : new String[][]{s1000, s100, s10, s1}) {
            StringBuilder b = new StringBuilder();
            for (String t : ss) b.append(t);
            String t = b.toString();
            int n = t.length();
            if (s == null) s = new int[n];
            for (int i = 0; i < n; i++) {
                s[i] = s[i] * 10 + (t.charAt(i) - '0');
            }
        }
        return s;
    }
    public static void main(String[] args) {
        int n = 50;
        String[] s1000 = new String[n];
        String[] s100 = new String[n];
        String[] s10 = new String[n];
        String[] s1 = new String[n];
        String[] t1000 = new String[n];
        String[] t100 = new String[n];
        String[] t10 = new String[n];
        String[] t1 = new String[n];
        for (int i = 0; i < n * n; i++) {
            int s = i + 1;
            int t = i + 2;
            int j = i / n;
            if (s1000[j] == null) {
                s1000[j] = "";
                s100[j] = "";
                s10[j] = "";
                s1[j] = "";
                t1000[j] = "";
                t100[j] = "";
                t10[j] = "";
                t1[j] = "";
            }
            s1000[j] += s / 1000 % 10;
            s100[j] += s / 100 % 10;
            s10[j] += s / 10 % 10;
            s1[j] += s % 10;
            t1000[j] += t / 1000 % 10;
            t100[j] += t / 100 % 10;
            t10[j] += t / 10 % 10;
            t1[j] += t % 10;
        }
        int res = new ShoutterDiv1().count(s1000, s100, s10, s1, t1000, t100, t10, t1);

        System.out.println(res + " " + (1 << 20));
    }
}
