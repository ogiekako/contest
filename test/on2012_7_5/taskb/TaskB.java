package on2012_7_5.taskb;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;

public class TaskB {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.nextLine();
        String[] ss = s.split("\\s+");
        for (int i = 0; i < ss.length; i++) {
            out.print(ss[i]);
            out.print(i==ss.length-1 ? '\n' : ',');
        }
    }
}
