package tmp;

import java.io.PrintWriter;
import java.util.Scanner;

public class TaskC {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        String current = "";
        int n = in.nextInt();
        for (int i = 0; i < n; i++) {
            String cmd = in.next();
            if (cmd.equals("pwd")) {
                out.println(current + "/");
            } else {
                String s = in.next();
                if (s.charAt(0) == '/') {
                    current = "";
                    s = s.substring(1);
                }
                String[] ss = s.split("/");
                for (String t : ss)
                    if (!t.isEmpty()) {
                        if (t.equals("")) {
                            if (!current.equals("")) {
                                int j = current.lastIndexOf('/');
                                current = current.substring(0, j);
                            }
                        } else {
                            current = current + "/" + t;
                        }
                    }
            }
        }
    }
}
