package src;

import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskD {
    long B;
    int L;
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        B = in.nextLong(); L = in.nextInt();
        int N = in.nextInt();
        char[] cs = new char[N + 1];
        cs[N] = '=';
        for (int i = 0; i < N; i++) cs[i] = in.nextChar();
//        debug(cs);
        out.println(solve(B, L, cs));
    }

    public String solve(long B, int L, char[] cs) {
        this.B = B; this.L = L;
        this.cs = cs;
//        s += "=";
        p = 0;
//        cs = s.toCharArray();
        In root = input();
        root.len();
        if (B < 0) B = root.len + B;
        char[] res = new char[L];
        root.solve(res, 0, B, B + L);
        return String.valueOf(res);
    }
    int p;
    private In input() {
        In in = new In();
        while (cs[p] != ')' && cs[p] != '=') {
            in.list.add(string());
        }
        return in;
    }

    char[] cs;
    private Str string() {
        if (cs[p] == '(') {
            p++;
            In in = input();
            p++;
            long rep = 0;
            while (Character.isDigit(cs[p])) {
                rep *= 10;
                rep += (cs[p++] - '0');
            }
            Str res = new Str();
            res.in = in;
            res.rep = rep;
            return res;
        } else {
            StringBuilder b = new StringBuilder();
            int from = p;
            while (Character.isLowerCase(cs[p]) || Character.isUpperCase(cs[p])) {
//                b.append(cs[p++]);
                p++;
            }
            Str res = new Str();
//            res.str = b.toString();
            res.from = from;
            res.to = p;
            return res;
        }
    }
    class In {
        List<Str> list = new ArrayList<Str>(0);
        long len;
        public long len() {
            for (Str str : list) len += str.len();
            return len;
        }

        public void solve(char[] res, int p, long from, long to) {
//            debug("In",p,from,to);
            if (from >= to) return;
            long cur = 0;
            for (Str str : list) {
                long nxt = cur + str.len;
                if (from < nxt && cur < to) {
                    str.solve(res, (int) (p + Math.max(0, cur - from)), Math.max(from, cur) - cur, Math.min(to, nxt) - cur);
                }
                cur = nxt;
            }
        }
    }
    // 012345678901234567890
    // ABCDEDEFCDEDEFCDEDEFG

    class Str {
        //        String str;
        In in;
        long rep;
        long len;
        public int from, to;
        public long len() {
            if (in == null) {
                len = to - from;
            } else {
                len = rep * in.len();
            }
            return len;
        }
        public void solve(char[] res, int p, long from, long to) {
//            debug("str",p,from,to);
            if (from >= to) return;
            if (in == null) {
                for (int i = (int) from; i < to; i++) res[((int) (p - from + i))] = cs[this.from + i];
            } else {
                long d = from / in.len;
                for (long i = d; i < rep; i++) {
                    long cur = in.len * i;
                    long nxt = in.len * (i + 1);
                    if (to <= cur) break;
                    in.solve(res, (int) (p + Math.max(0, cur - from)), Math.max(from, cur) - cur, Math.min(to, nxt) - cur);
                }
            }
        }
    }

    static void debug(Object... os) {
        System.out.println(Arrays.deepToString(os));
    }

//    public static void main(String[] args) {
//        String in = "AB(C(DE)2F)3G";
//        String text = "ABCDEDEFCDEDEFCDEDEFG";
//        for (int len = 1; len < text.length() + 1; len++) {
//            for (int i = 0; i + len <= text.length(); i++) {
//                System.err.println(i + " " + len);
//                String res = new TaskD().solve(i, len, in);
//                String exp = text.substring(i, i + len);
//                if (!res.equals(exp)) {
//                    throw new AssertionError(i + " " + len);
//                }
//            }
//        }
//    }
}
