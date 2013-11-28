package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class G {
    int one = 2147483647;
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int N = in.nextInt();
        String[] def = new String[N];
        for (int i = 0; i < N; i++) def[i] = in.next();
        String[] res = solve(def);
        for (String r : res) out.println(r);
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    char[] fs;
    int[] c2i = new int[256];
    int[] f0 = new int[256];
    int[] f1 = new int[256];
    private String[] solve(String[] def) {
        fs = new char[def.length];
        for (int i = 0; i < def.length; i++) {
            String[] ss = def[i].split("\\(x\\)=");
            fs[i] = ss[0].charAt(0);
            def[i] = ss[1];
            c2i[fs[i]] = i;
        }
        Arrays.fill(f0, -1);
        Arrays.fill(f1, -1);
        for (int i = 0; i < def.length; i++) {
//            debug(def[i]);
            f0[i] = eval(fs[i], def[i], 0);
            f1[i] = eval(fs[i], def[i], one);
//            debug(f0[i], f1[i]);
        }
        String[] res = new String[def.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = String.format("%c(x)=(((x^%d)&%d)|(x&%d))", fs[i], one, f0[i], f1[i]);
        }
        return res;
    }

    private int eval(char f, String s, int x) {
        if (f0[c2i[f]] >= 0 && f1[c2i[f]] >= 0) {
            int res = (x & f1[c2i[f]]) | (~x & f0[c2i[f]]);
            return res;
        }
        p = 0;
        return expr(s.toCharArray(), x);
    }

    private int expr(char[] cs, int x) {
//        debug(String.valueOf(cs).substring(p));
        if (cs[p] == 'x') {
            p++;
            return x;
        } else if (Character.isDigit(cs[p])) {
            int num = 0;
            while (p < cs.length && Character.isDigit(cs[p])) {
                num *= 10;
                num += cs[p] - '0';
                p++;
            }
//            debug("num" , num);
            return num;
        } else if (cs[p] != '(') {
            char funName = cs[p++];
            int n = 1;
            if (cs[p] == '^') {
                p++;
                n = 0;
                while (p < cs.length && Character.isDigit(cs[p])) {
                    n *= 10;
                    n += cs[p] - '0';
                    p++;
                }
            }
            p++;
            int nx = expr(cs, x);
            p++;
            if (n == 0) return nx;
            if (n % 2 == 1) return eval(funName, null, nx);
            else return eval(funName, null, eval(funName, null, nx));
        }

        p++;// (
        int left = expr(cs, x);
        char op = cs[p++];
        int right = expr(cs, x);
        p++;
//        debug(left,op,right);
        if (op == '|') return left | right;
        else if (op == '^') return left ^ right;
        else if (op == '&') return left & right;
        else throw new AssertionError();
    }

    int p;
}
