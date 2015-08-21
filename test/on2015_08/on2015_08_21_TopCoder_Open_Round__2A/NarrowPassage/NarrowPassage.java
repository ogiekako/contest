package on2015_08.on2015_08_21_TopCoder_Open_Round__2A.NarrowPassage;



import java.util.*;

public class NarrowPassage {
    private static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }


    class E implements Comparable<E>{
        int pos;
        int id;

        public E(int pos, int id) {
            this.pos = pos;
            this.id = id;
        }

        @Override
        public int compareTo(E o) {
            return pos - o.pos;
        }
    }

    public int minDist(int L, int[] a, int[] b) {
        int n = a.length;
        E[] E1 = new E[n], E2 = new E[n];
        for (int i = 0; i < n; i++) {
            E1[i] = new E(a[i], i);
            E2[i] = new E(b[i], i);
        }
        Arrays.sort(E1);
        Arrays.sort(E2);
        int res = Integer.MAX_VALUE;
        // 1
        for (int l = 0; l < n; l++) {
            for (int r = l + 1; r <= n; r++) {
                Set<Integer> L1 = new TreeSet<>();
                Set<Integer> L2 = new TreeSet<>();
                List<Integer> M1 = new ArrayList<>();
                List<Integer> M2 = new ArrayList<>();
                Set<Integer> R1 = new TreeSet<>();
                Set<Integer> R2 = new TreeSet<>();
                int val = 0;
                for (int i = 0; i < l; i++) {
                    L1.add(E1[i].id);
                    L2.add(E2[i].id);
                    val += E1[i].pos + E2[i].pos;
                }
                for (int i = l; i < r; i++) {
                    M1.add(E1[i].id);
                    M2.add(E2[i].id);
                    val += Math.abs(E1[i].pos - E2[i].pos);
                }
                for (int i = r; i < n; i++) {
                    R1.add(E1[i].id);
                    R2.add(E2[i].id);
                    val += L - E1[i].pos + (L - E2[i].pos);
                }
                if (L1.equals(L2) && M1.equals(M2) && R1.equals(R2)) {
                    res = Math.min(res, val);
                }
            }
        }
        // 2
        for (int s1 = 0; s1 <= n; s1++) {
            for (int s2 = 0; s2 <= n; s2++) {
                Set<Integer> L1 = new TreeSet<>();
                Set<Integer> L2 = new TreeSet<>();
                Set<Integer> R1 = new TreeSet<>();
                Set<Integer> R2 = new TreeSet<>();
                int val = 0;
                for (int i = 0; i < s1; i++) {
                    L1.add(E1[i].id);
                    val += E1[i].pos;
                }
                for (int i = s1; i < n; i++) {
                    R1.add(E1[i].id);
                    val += L - E1[i].pos;
                }
                for (int i = 0; i < s2; i++) {
                    L2.add(E2[i].id);
                    val += E2[i].pos;
                }
                for (int i = s2; i < n; i++) {
                    R2.add(E2[i].id);
                    val += L - E2[i].pos;
                }
                L1.retainAll(R2);
                R1.retainAll(L2);
                val += (L1.size() + R1.size()) * L;
                res = Math.min(res, val);
            }
        }

        return res;
    }

    private void sort(int[] a, int[] b) {
        long[] ab = new long[a.length];
        for (int i = 0; i < a.length; i++) {
            ab[i] = (long) a[i] << 32 | b[i];
        }
        Arrays.sort(ab);
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (ab[i] >> 32);
            b[i] = (int) ab[i];
        }
    }
}
