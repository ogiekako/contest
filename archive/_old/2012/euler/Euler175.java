package tmp;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Euler175 {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        long p = in.nextLong(), q = in.nextLong();
        StringBuilder res = new StringBuilder();
        while (p != q) {
            if (p < q) {
                res.append(1);
                q -= p;
            } else {
                res.append(0);
                p -= q;
            }
        }
        res.append(1);
        res.reverse();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < res.length(); ) {
            int j = i;
            while (j < res.length() && res.charAt(i) == res.charAt(j)) j++;
            list.add(j - i);
            i = j;
        }
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) out.print(",");
            out.print(list.get(i));
        }
        out.println();
    }
}
