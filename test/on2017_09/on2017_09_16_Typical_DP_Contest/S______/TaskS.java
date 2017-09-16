package on2017_09.on2017_09_16_Typical_DP_Contest.S______;



import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Union;
import net.ogiekako.algorithm.dataStructure.UnionFind;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.math.Mint;
import net.ogiekako.algorithm.utils.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TaskS {
    class State {
        int[] a;
        int o;

        public State(int[] a, int o) {
            this.a = a;
            this.o = o;
        }

        public State next(int i, boolean black) {
            int[] na = a.clone();
            if (!black) {
                na[i] = -1;
                int p = -1;
                for (int j = 0; j < na.length; j++) {
                    if (na[j] == o) {
                        p = j;
                    }
                }
                if (p < 0) return null;
                ArrayUtils.normalize(na);
                return new State(na, na[p]);
            } else {
                int p = -1;
                for (int j = 0; j < na.length; j++) {
                    if (a[j] == o) p = j;
                }
                if (p < 0) throw new AssertionError();
                if (i > 0 && a[i] >= 0 && a[i - 1] >= 0) {
                    for (int j = 0; j < na.length; j++) {
                        if (na[j] == a[i - 1]) na[j] = a[i];
                    }
                } else if (a[i] >= 0) {
                    na[i] = a[i];
                } else if (i > 0 && a[i - 1] >= 0) {
                    na[i] = a[i - 1];
                } else {
                    na[i] = na.length;
                }
                ArrayUtils.normalize(na);
                return new State(na, na[p]);
            }
        }

        @Override
        public boolean equals(Object o1) {
            if (this == o1) return true;
            if (o1 == null || getClass() != o1.getClass()) return false;

            State state = (State) o1;

            if (o != state.o) return false;
            return Arrays.equals(a, state.a);
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(a);
            result = 31 * result + o;
            return result;
        }
    }

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        Mint.set1e9_7();

        int h = in.nextInt(), w = in.nextInt();
        int[] init = new int[h];
        Arrays.fill(init,-1);
        init[0] = 0;
        State initState = new State(init, 0);
        HashMap<State, Mint>[] dp = new HashMap[2];
        for (int i = 0; i < 2; i++) {
            dp[i] = new HashMap<>();
        }
        int cur = 0, nxt = 1;
        dp[cur].put(initState, Mint.ONE);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                dp[nxt].clear();
                for (Map.Entry<State, Mint> e : dp[cur].entrySet()) {
                    State key = e.getKey();
                    State s1 = key.next(j, false);
                    if (s1 != null) {
                        Mint tmp = dp[nxt].get(s1);
                        if (tmp == null) tmp = Mint.ZERO;
                        dp[nxt].put(s1, tmp.add(e.getValue()));
                    }
                    State s2 = key.next(j, true);
                    Mint tmp = dp[nxt].get(s2);
                    if (tmp == null) tmp = Mint.ZERO;
                    dp[nxt].put(s2, tmp.add(e.getValue()));
                }
                int tmp = cur;
                cur = nxt;
                nxt = tmp;
            }
        }

        Mint res = Mint.ZERO;
        for (Map.Entry<State, Mint> e : dp[cur].entrySet()) {
            State s = e.getKey();
            if (s.a[h-1] == s.o) {
                res = res.add(e.getValue());
            }
        }
        out.println(res);
    }
}
