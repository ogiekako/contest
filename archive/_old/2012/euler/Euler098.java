package tmp;

import net.ogiekako.algorithm.math.MathUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Euler098 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        String input = in.next();
        String[] ss = input.split(",");
        int mxLen = 0;
        for (String s : ss) mxLen = Math.max(mxLen, s.length());
        boolean[] used = new boolean[ss.length];
        List<List<String>>[] lists = new ArrayList[mxLen + 1];
        for (int i = 0; i < mxLen + 1; i++) lists[i] = new ArrayList<List<String>>();
        for (int i = 0; i < ss.length; i++)
            if (!used[i]) {
                List<String> list = new ArrayList<String>();
                for (int j = i; j < ss.length; j++)
                    if (anagram(ss[i], ss[j])) {
                        list.add(ss[j]);
                        used[j] = true;
                    }
                lists[ss[i].length()].add(list);
            }

        long N = MathUtils.power(10, mxLen);
        long p10 = 10;
        int dig = 1;
        long res = 0;
        int[] is = new int[256];
        for (long n = 1; n * n < N; n++) {
            if (n * n >= p10) {
                p10 *= 10;
                dig++;
            }
            for (List<String> list : lists[dig]) {
                int sz = list.size();
                for (int i = 0; i < sz; i++) {
                    String sample = list.get(i);
                    int[] js = new int[26];
                    int[] cs = new int[10];
                    Arrays.fill(cs, -1);
                    Arrays.fill(js, -1);
                    long nn = n * n;
                    boolean ok = true;
                    for (int j = dig - 1; j >= 0; j--) {
                        int p = sample.charAt(j) - 'A';
                        int v = (int) (nn % 10);
                        if (cs[v] != -1 && cs[v] != p) ok = false;
                        if (js[p] != -1 && js[p] != v) ok = false;
                        cs[v] = p;
                        js[p] = v;
                        nn /= 10;
                    }
                    if (!ok) continue;
                    for (int j = i + 1; j < sz; j++) {
                        String t = list.get(j);
                        if (js[t.charAt(0) - 'A'] == 0) continue;
                        long val = 0;
                        for (int k = 0; k < dig; k++) {
                            val *= 10;
                            val += js[t.charAt(k) - 'A'];
                        }
                        if (MathUtils.isSquare(val)) {
                            res = Math.max(res, val);
                            res = Math.max(res, n * n);
                        }
                    }
                }
            }
        }
        out.println(res);
    }

    private boolean anagram(String s, String t) {
        if (s.length() == t.length()) {
            char[] cs = s.toCharArray();
            char[] ds = t.toCharArray();
            Arrays.sort(cs);
            Arrays.sort(ds);
            return Arrays.equals(cs, ds);
        }
        return false;
    }
}
