package tmp;

import net.ogiekako.algorithm.io.MyScanner;

import java.io.PrintWriter;
import java.util.ArrayList;

public class AOJ2362 {
    public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        String egg = "egg";
        String chicken = "chicken";
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        String cur = "";
        ArrayList<String> list = new ArrayList<String>();
        while (!s.isEmpty()) {
            if (s.startsWith(egg)) {
                if (cur.equals(egg)) {
                    lists.add(list);
                    list = new ArrayList<String>();
                    list.add(egg);
                } else {
                    list.add(egg);
                }
                cur = egg;
                s = s.substring(egg.length());
            } else if (s.startsWith(chicken)) {
                if (cur.equals(chicken)) {
                    lists.add(list);
                    list = new ArrayList<String>();
                    list.add(chicken);
                } else {
                    list.add(chicken);
                }
                cur = chicken;
                s = s.substring(chicken.length());
            }
        }
        lists.add(list);
        int maxLen = 0;
        for (ArrayList<String> l : lists) maxLen = Math.max(maxLen, l.size());
        for (ArrayList<String> l : lists) {
            if (l.size() == maxLen) {
                out.println(l.get(l.size() - 1));
                return;
            }
        }
    }
}
