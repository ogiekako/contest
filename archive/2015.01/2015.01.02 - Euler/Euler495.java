package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;
import net.ogiekako.algorithm.math.MathUtils;

import java.util.*;

public class Euler495 {
    int MOD = (int) (1e9 + 7);

    void debug(Object...os){
        System.err.println(Arrays.deepToString(os));
    }
    // l = 1229, |ps| = 5604, max(m_i) = 9995.

    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt(), k = in.nextInt();

        int[] primes = MathUtils.generatePrimes(n + 1);
        int l = primes.length;
        int[] ms = new int[l];
        for (int i = 0; i < l; i++) {
            int p = primes[i];
            for (int j = 1; j < n + 1; j++) {
                int t = j;
                while (t % p == 0) {
                    t /= p;
                    ms[i]++;
                }
            }
        }
//        debug("ms", ms);

        Partition[] ps = generatePartitions(k).toArray(new Partition[0]);
        debug("ps.length", l);

        long[][] h = computeH(ps);
        debug("h");
        long[] g = computeG(ps, ms);
        debug("gs");
        long[] f = new long[ps.length];
        Arrays.fill(f, -1);
        long res = compute(f, 0, ps, g, h);
        debug("f");

        for (int i = 1; i <= k; i++) {
            res = (res * MathUtils.inverse(i, MOD) % MOD);
        }
        out.println(res);


    }

    private long compute(long[] f, int i, Partition[] ps, long[] g, long[][] h) {
        if (f[i] >= 0) return f[i];
        f[i] = g[i];
        for (int j=0;j<ps.length;j++) if(i != j && h[i][j] > 0) {
            f[i] = (f[i] - compute(f, j, ps, g, h) * h[i][j]) % MOD;
            if (f[i] < 0) f[i] += MOD;
        }
        return f[i];
    }

    private long[] computeG(Partition[] ps, int[] ms) {
        int maxM = 0;
        for (int m : ms) maxM = Math.max(maxM, m);

        long[] res = new long[ps.length];
        for (int i = 0; i < ps.length; i++) {
            Partition p = ps[i];
            long[] g = gs(p, maxM);
            res[i] = 1;
            for (int m : ms) {
                res[i] = (res[i] * g[m]) % MOD;
            }
            debug("g", i + " / " + ps.length);
        }
        return res;
    }

    private long[] gs(Partition p, int m) {
        long[] dp = new long[m + 1];
        dp[0] = 1;
        for (int a : p.a) {
            long[] nDp = new long[m + 1];
            for (int i = 0; i < m + 1; i++) if (dp[i] > 0) {
                for (int j = 0; i + j * a < m + 1; j++) {
                    int k = i + j * a;
                    nDp[k] += dp[i];
                    if (nDp[k] >= MOD) nDp[k] -= MOD;
                }
            }
            dp = nDp;
        }
        return dp;
    }

    private long[][] computeH(Partition[] ps) {
        int t = ps.length;
        long[][] h = new long[t][t];
        for (int i = 0; i < t; i++) {
            Partition p = ps[i];
            HashMap<Partition, Long> dp = new HashMap<>();
            dp.put(new Partition(Collections.<Integer>emptyList()), 1L);
            for (int a : p.a) {
                HashMap<Partition, Long> nDp = new HashMap<>();
                for (Map.Entry<Partition, Long> e : dp.entrySet()) {
                    update(nDp, e, a);
                }
                dp = nDp;
            }
            for (int j = 0; j < t; j++) {
                h[i][j] = dp.containsKey(ps[j]) ? dp.get(ps[j]) : 0;
            }
            debug("h", i);
        }
        return h;
    }

    private void update(HashMap<Partition, Long> nDp, Map.Entry<Partition, Long> cur, int a) {
        Partition key = cur.getKey();
        for (int i = 0; i < key.a.size(); i++) {
            List<Integer> nxt = new ArrayList<>(key.a);
            nxt.set(i, nxt.get(i) + a);
            Collections.sort(nxt);
            Partition q = new Partition(nxt);
            if (!nDp.containsKey(q)) {
                nDp.put(q, 0L);
            }
            nDp.put(q, add(nDp.get(q), cur.getValue()));
        }
        List<Integer> add = new ArrayList<>(key.a);
        add.add(a);
        Collections.sort(add);
        Partition q = new Partition(add);
        if (!nDp.containsKey(q)) {
            nDp.put(q, 0L);
        }
        nDp.put(q, add(nDp.get(q), cur.getValue()));

    }

    private long add(long a, long b) {
        return (a + b) % MOD;
    }

    class Partition {
        List<Integer> a;

        public Partition(List<Integer> a) {
            int tmp = 0;
            for (int b : a) {
                if (tmp > b) throw new AssertionError();
                tmp = b;
            }
            this.a = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Partition partition = (Partition) o;

            if (a != null ? !a.equals(partition.a) : partition.a != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return a != null ? a.hashCode() : 0;
        }
    }

    List<Partition> generatePartitions(int k) {
        List<Partition> acc = new ArrayList<>();
        generatePartitions0(acc, 1, k, new ArrayList<Integer>());
        return acc;
    }

    private void generatePartitions0(List<Partition> acc, int min, int k, ArrayList<Integer> a) {
        if (k == 0) {
            acc.add(new Partition(a));
            return;
        }
        for (int i = min; i <= k; i++) {
            ArrayList<Integer> nA = new ArrayList<>(a);
            nA.add(i);
            generatePartitions0(acc, i, k - i, nA);
        }
    }


}
