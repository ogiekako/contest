package on2013_07.on2013_07_06_kupc2013.TaskD;


import net.ogiekako.algorithm.io.MyPrintWriter;
import net.ogiekako.algorithm.io.MyScanner;

import java.util.Stack;

public class TaskD {
    public void solve(int testNumber, MyScanner in, MyPrintWriter out) {
        int n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        int res = n;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && stack.peek() > a[i]) stack.pop();
            if (!stack.isEmpty() && ((int) stack.peek()) == a[i]) res--;
            else stack.push(a[i]);
        }
        out.println(res);

    }
}
