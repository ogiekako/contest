package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashCollision2 {
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


    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int a = in.nextInt(), b = in.nextInt();
        String[] res = solve(a, b);
        for (String s : res) out.println(s);
    }

    Hasher hasher;
    Set<String> dejavu;
    Random rnd;
    private String[] solve(int a, int b) {
        hasher = new Hasher(a, b);
        dejavu = new HashSet<String>();
        rnd = new Random(14012840L);
        String[][] pairs = new String[6][2];
        for (int i = 0; i < 6; i++) {
            pairs[i] = calcPair();
            for (String s : pairs[i]) dejavu.add(s);
        }
        String[] res = new String[50];
        for (int i = 0; i < 50; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 6; j++) sb.append(pairs[j][i >> j & 1]);
            res[i] = sb.toString();
        }
        return res;
    }

    private String[] calcPair() {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (; ; ) {
            String s = gen(8);
            int hash = hasher.hash(s);
            if (map.containsKey(hash)) {
                String t = map.get(hash);
                if (!s.equals(t) && !dejavu.contains(s) && !dejavu.contains(t)) {
                    return new String[]{s, t};
                }
            }
            map.put(hash, s);
        }
    }

    private String gen(int n) {
        char[] res = new char[n];
        for (int i = 0; i < n; i++) {
            res[i] = (char) (rnd.nextInt(26) + 'a');
        }
        return new String(res);
    }
}
