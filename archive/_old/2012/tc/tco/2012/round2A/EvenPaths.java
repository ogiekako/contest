package tmp;

// Paste me into the FileEdit configuration dialog

import net.ogiekako.algorithm.convolution.SubsetConvolution;

import java.util.ArrayList;


public class EvenPaths {
    int N;
    public long theCount(String[] maze, String rooms) {
        N = maze.length;
        V[] vs = new V[N];
        for (int i = 0; i < N; i++) {
            vs[i] = new V(i, rooms.charAt(i) == '?');
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (maze[i].charAt(j) == 'Y') {
                    vs[i].es.add(vs[j]);
                    vs[j].rs.add(vs[i]);
                }
            }
        vs = topologicalSort(vs);
        for (int i = 0; i < N; i++) vs[i].pos = i;
        int numQ = 0;
        for (V v : vs) if (v.question) numQ++;
        int n1 = numQ / 2;
        int n2 = numQ - n1;
        int[] indiesQ = new int[numQ];
        for (int i = 0, j = 0; i < vs.length; i++)
            if (vs[i].question) {
                vs[i].qId = j;
                indiesQ[j++] = i;
            }
        long[] A0 = new long[1 << n2];
        long[] A1 = new long[1 << n2];
        for (int i = 0; i < 1 << n1; i++) {
            boolean[] dp = new boolean[N];
            for (int j = 0; j < N; j++) {
                if (vs[j].qId >= 0 && vs[j].qId < n1 && (i >> vs[j].qId & 1) == 1) continue;
                if (vs[j].id == 0) dp[j] = true;
                if (dp[j] && vs[j].qId < n1) {
                    for (V v : vs[j].es) {
                        dp[v.pos] ^= true;
                    }
                }
            }
            int msk = 0;
            for (int j = 0; j < n2; j++) {
                msk |= (dp[indiesQ[n1 + j]] ? 1 : 0) << j;
            }
            for (int j = 0; j < N; j++)
                if (vs[j].id == 1) {
                    if (dp[j]) A1[msk]++;
                    else A0[msk]++;
                }
        }
        long[] B = new long[1 << n2];
        for (int i = 0; i < 1 << n2; i++) {
            boolean[] dp = new boolean[N];
            for (int j = N - 1; j >= 0; j--) {
                if (vs[j].qId >= n1 && (i >> vs[j].qId - n1 & 1) == 1) continue;
                if (vs[j].id == 1) dp[j] = true;
                if (dp[j]) {
                    for (V v : vs[j].rs) {
                        dp[v.pos] ^= true;
                    }
                }
            }
            int msk = 0;
            for (int j = 0; j < n2; j++)
                if ((i >> j & 1) == 0) {
                    msk |= (dp[indiesQ[n1 + j]] ? 1 : 0) << j;
                }
            B[msk]++;
        }

        return doIt(A0, B, 0, n2) + doIt(A1, B, 1, n2);
    }

    private long doIt(long[] A, long[] B, int goal, int n) {
        long[] g = new long[1 << n];
        long[] sumAs = SubsetConvolution.zetaConvolutionInv(A, n);
        long[] sumBs = SubsetConvolution.zetaConvolutionInv(B, n);
        for (int i = 0; i < 1 << n; i++) {
            g[i] = sumAs[i] * sumBs[i];
        }
        long[] f = SubsetConvolution.mobiusConvolutionInv(g, n);
        long res = 0;
        for (int S = 0; S < 1 << n; S++)
            if ((Integer.bitCount(S) & 1) == goal) {
                res += f[S];
            }
        return res;
    }

    class V {
        public int id;
        public int pos;

        public boolean question;
        int qId = -1;
        public ArrayList<V> es = new ArrayList<V>();
        public ArrayList<V> rs = new ArrayList<V>();

        public V(int index, boolean question) {
            this.id = index;
            this.question = question;
        }
    }

    private int topologicalSortN;
    V[] topologicalSort(V[] vs) {
        topologicalSortN = vs.length;
        int[] states = new int[topologicalSortN];
        V[] us = new V[topologicalSortN];
        for (V v : vs) {
            if (states[v.id] == 0 && !topologicalSortDfs(v, us, states)) return null;
        }
        return us;
    }
    boolean topologicalSortDfs(V v, V[] us, int[] states) {
        states[v.id] = 1;
        for (V u : v.es) {
            if (states[u.id] == 1 || states[u.id] == 0 && !topologicalSortDfs(u, us, states)) return false;
        }
        us[--topologicalSortN] = v;
        states[v.id] = 2;
        return true;
    }
}

