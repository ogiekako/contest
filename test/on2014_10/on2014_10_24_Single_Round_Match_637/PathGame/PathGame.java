package on2014_10.on2014_10_24_Single_Round_Match_637.PathGame;


import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Random;

public class PathGame {
    HashMap<K, Integer> memo = new HashMap<>();
    Random r = new Random(1212L);

    public static void main(String[] args) {
        new PathGame().test();
    }

    public String judge(String[] board) {
        int n = board[0].length();
        String s = "";
        for (int i = 0; i < n; i++) {
            s += (char) ((board[0].charAt(i) == '#' ? 1 : 0)
                    + (board[1].charAt(i) == '#' ? 2 : 0) + '0');
        }
        boolean res = solve(s);
        return res ? "Snuke" : "Sothe";
    }

    private boolean solve(String s) {
        int n = s.length();
        int gr = 0;
        for (int i = 0; i < n; ) {
            int j = i + 1;
            while (j < n && s.charAt(j) == '0') j++;
            int len = j - i;
            if (j < n) len++;
            String b = s.substring(i, i + len);
            gr ^= solve2(b);
            i = j;
        }
        return gr > 0;
    }

    private int solve2(String a) {
        int n = a.length();
        return solve2(a.charAt(0), a.charAt(n - 1), n);
    }

    private int solve2(char l, char r, int n) {
        K key = new K(l, r, n);
        if (memo.containsKey(key)) return memo.get(key);
        BitSet gs = new BitSet();
        for (int i = 0; i < n; i++) {
            for (char c = '1'; c <= '2'; c++) {
                boolean ok = true;
                if (i == 0 && l != '0' || i == n - 1 && r != '0') ok = false;
                if (i == 1 && l != '0' && l != c) ok = false;
                if (i == n - 2 && r != '0' && r != c) ok = false;
                if (ok) {
                    int g1 = solve2(l, c, i + 1);
                    int g2 = solve2(c, r, n - i);
                    gs.set(g1 ^ g2);
                }
            }
        }
        int res = gs.nextClearBit(0);
        memo.put(key, res);
        return res;
    }

    private void test() {
        int n = 1000;
        String[] ss = new String[2];
        Arrays.fill(ss, "");
        for (int i = 0; i < n; i++) {
            char c1 = '.';
            char c2 = '.';
            ss[0] += c1;
            ss[1] += c2;
        }
        String res = judge(ss);
        System.out.println(ss[0]);
        System.out.println(res);
    }

    class K {
        char l, r;
        int n;

        K(char l, char r, int n) {
            this.l = l;
            this.r = r;
            this.n = n;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            K k = (K) o;

            if (l != k.l) return false;
            if (n != k.n) return false;
            if (r != k.r) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = l;
            result = 31 * result + r;
            result = 31 * result + n;
            return result;
        }
    }
}
