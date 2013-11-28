package tmp;

import java.util.Arrays;
import java.util.Random;

public class TheLargestString {
    static void debug(Object... os) {
        System.err.println(Arrays.deepToString(os));
    }

    public String find(String s, String t) {
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            String ns = "" + s.charAt(i);
            String nt = "" + t.charAt(i);
            res = max(res, ns + nt);
            int lst = i;
            for (char c = 'z'; c > t.charAt(i); c--) {
                for (int j = lst + 1; j < s.length(); j++) {
                    if (s.charAt(j) == c) {
                        ns += c;
                        nt += t.charAt(j);
                        lst = j;
                    }
                }
            }
            res = max(res, ns + nt);
            char c = t.charAt(i);
            String ns2 = "";
            String nt2 = "";
            for (int j = lst + 1; j < s.length(); j++)
                if (s.charAt(j) == c) {
                    ns2 += c;
                    nt2 += t.charAt(j);
                }
            res = max(res, ns + ns2 + nt + nt2);
            int cnt = 0;
//            debug(ns,nt,ns2,nt2);
            for (int j = 0; j < ns2.length(); j++) {
                if (nt2.charAt(j) == c) {
                    cnt++;
                    String nxt = ns;
                    for (int k = 0; k < cnt; k++) nxt += c;
                    for (int k = j + 1; k < ns2.length(); k++) nxt += c;
                    nxt += nt;
                    for (int k = 0; k < cnt; k++) nxt += c;
                    for (int k = j + 1; k < nt2.length(); k++) nxt += nt2.charAt(k);
                    res = max(res, nxt);
                }
                res = max(res, ns + ns2.charAt(j) + nt + nt2.charAt(j));
            }
        }
        return res;
//        char ms = 'a';
//        for (char c : s.toCharArray()) ms = (char) Math.max(ms, c);
//        char mt = 'a';
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == ms) mt = (char) Math.max(mt, t.charAt(i));
//        }
////        debug(ms,mt);
//        String cur = "";
//        if (mt > ms) {
//            return "" + ms + mt;
//        } else if (mt == ms) {
//            String ns = "";
//            String nt = "";
//            for (int i = 0; i < s.length(); i++) {
//                if (s.charAt(i) == ms) {
//                    ns += ms;
//                    nt += t.charAt(i);
//                }
//            }
//            cur = max(cur,ns+nt);
////            String cur = ns + nt;
//            int cnt = 0;
//            debug("A",ns,nt);
//            for (int i = 0; i < ns.length(); i++) {
//                if (nt.charAt(i) == mt) {
//                    cnt++;
//                    String nxt = "";
//                    for (int j = 0; j < cnt * 2; j++) nxt += ms;
//                    for (int j = i + 1; j < ns.length(); j++) nxt += ms;
//                    for (int j = i + 1; j < nt.length(); j++) nxt += nt.charAt(j);
//                    debug(cnt,nxt,i,ns.length());
//                    cur = max(cur, nxt);
//                }
//            }
////            return cur;
//        }
////        String ns = "";
////        String nt = "";
////        int lst = 0;
////        for (int i = 0; i < s.length(); i++) {
////            if (s.charAt(i) == ms) {
////                ns += ms;
////                nt += t.charAt(i);
////                lst = i;
////            }
//        }
//        for(int i=lst+1;i<s.length();i++)cur = max(cur, "" + ns + s.charAt(i) + nt + t.charAt(i));
//        cur = max(cur,ns+nt);
////        String cur = ns + nt;
//        for (char c = (char) (ms - 1); c >= 'a'; c--) {
//            for (int i = lst; i < s.length(); i++) {
//                if (s.charAt(i) == c) {
//                    ns += c;
//                    nt += t.charAt(i);
//                    lst = i;
//                }
//            }
//            cur = max(cur, ns + nt);
//            for(int i=lst+1;i<s.length();i++)cur = max(cur, "" + ns + s.charAt(i) + nt + t.charAt(i));
//        }
//        return cur;
    }

    private static String max(String a, String b) {
        return a.compareTo(b) > 0 ? a : b;
    }

    public static void main(String[] args) {
        Random rnd = new Random(1124);
        for (int i = 0; ; i++) {
            int n = rnd.nextInt(10) + 1;
            debug(i);
            String s = "";
            String t = "";
            int k = rnd.nextInt(2) + 1;
            for (int j = 0; j < n; j++) {
                s += (char) (rnd.nextInt(k) + 'y');
                t += (char) (rnd.nextInt(k) + 'y');
            }
            String res = new TheLargestString().find(s, t);
            String exp = stupid(s, t);
            if (!res.equals(exp)) {
                debug(s, t);
                debug(res, exp);
//                if(i==80)
                throw new AssertionError();
            }
        }
    }

    private static String stupid(String s, String t) {
        return "";
    }
}
/*

ccb aca ->

*/