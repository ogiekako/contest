package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.Arrays;

public class B {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        int L = in.nextInt(), M = in.nextInt();
        String A = in.next(), B = in.next();
        debug(A, B);
        int p = 0;
        while (p < L - 1 && p < M - 1 && ok(A.charAt(L - 1 - p), B.charAt(p))) p++;
        int[] res = new int[4];
        if (p == 0) {
            out.printf("%d %d %d %d\n", res[0], res[1], res[2], res[3]);
            return;
        }
        String head = flip(rev(B.substring(p, M))), tail = flip(rev(A.substring(0, L - p)));
//        debug("ht",head,tail);
//        debug("p",p);
        String C = head + tail;
        char c = 'z';
        StringBuilder mid1 = new StringBuilder();
        StringBuilder mid2 = new StringBuilder();
        p--;
        while (p >= 1) {
            char d = flip(B.charAt(p));
            if (d > c) break;
            mid1.append(d); mid2.append(flip(A.charAt(L - 1 - p)));
            if (d < c) {
                String C2 = head + mid1.toString() + mid2.reverse().toString() + tail;
//            debug("C2",C2);
                if (C2.compareTo(C) < 0) C = C2;
            }
            c = flip(A.charAt(L - 1 - p));
            p--;
        }
        String C2 = head + mid1.toString() + mid2.reverse().toString() + tail;
//        debug(C2);
        if (C2.compareTo(C) < 0) C = C2;
        String S = "ATGC";
        for (int i = 0; i < C.length(); i++) res[S.indexOf(C.charAt(i))]++;
//        debug(C);
        out.printf("%d %d %d %d\n", res[0], res[1], res[2], res[3]);
    }

    private String flip(String s) {
        StringBuilder res = new StringBuilder();
        for (char c : s.toCharArray()) res.append(flip(c));
        return res.toString();
    }

    private char flip(char c) {
        if (c == 'A') return 'T';
        if (c == 'T') return 'A';
        if (c == 'G') return 'C';
        if (c == 'C') return 'G';
        throw null;
    }

    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    private String rev(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    private boolean ok(char a, char b) {
        return flip(a) == b;
    }
}
