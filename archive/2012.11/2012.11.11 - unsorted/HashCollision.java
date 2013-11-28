package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.*;

public class HashCollision {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    class Hasher {
        int a, b;

        Hasher(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int hash(String s) {
            int res = 0;
            for (int i = 0; i < s.length(); i++) {
                res = (int) (((long) res * a + s.charAt(i) - 'a' + 1) % b);
            }
            return res;
        }
    }

    int N = 50;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int a = in.nextInt(), b = in.nextInt();
        List<String> list = solve(a, b);
        for (String s : list) out.println(s);
    }
    private List<String> solve(int a, int b) {
        Hasher hasher = new Hasher(a, b);
        int n = 25;
        List<String> list = new ArrayList<String>();
        Random rnd = new Random(1240918240L);
        int target = -1;
        while (list.size() < 50) {
            HashMap<Integer, String> map = new HashMap<Integer, String>();
            int L = 100000;
            long mul = 1;
            for (int i = 0; i < n; i++) mul = mul * a % b;
            int first = 0;
            for (int i = 0; i < L; i++) {
                String s = gen(rnd, n);
                long h = hasher.hash(s);
                h *= mul;
                h %= b;
                map.put((int) h, s);
                first = (int) h;
            }
            for (int i = 0; i < L; i++) {
                String s = gen(rnd, n);
                if (target == -1) target = (hasher.hash(s) + first) % b;
                int key = (b + target - hasher.hash(s)) % b;
                if (map.containsKey(key) && list.size() < N) list.add(map.get(key) + s);
            }
        }
        for (String s : list) if (hasher.hash(s) != target) throw new AssertionError();
        return list;
    }

    private String gen(Random rnd, int n) {
        char[] cs = new char[n];
        for (int i = 0; i < n; i++) cs[i] = (char) (rnd.nextInt(26) + 'a');
        return new String(cs);
    }

    public static void main(String[] args) {
        HashCollision instance = new HashCollision();
        Random rnd = new Random(12081208L);
        for (int i = 0; i < 100000; i++) {
            System.err.println(i);
            long start = System.currentTimeMillis();
            int b = rnd.nextInt((int) (1e9 - 27)) + 27;
            int a = rnd.nextInt(b - 26) + 26;
            instance.solve(a, b);
            long time = System.currentTimeMillis() - start;
            if (time > 2000) {
                debug(a, b, time);
                throw new AssertionError();
            }
        }
    }
}
