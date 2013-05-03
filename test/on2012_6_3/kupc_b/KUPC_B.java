package on2012_6_3.kupc_b;



import net.ogiekako.algorithm.io.MyScanner;
import java.io.PrintWriter;

public class KUPC_B {
	public void solve(int testNumber, MyScanner in, PrintWriter out) {
        String s = in.next();
        if(s.charAt(0)=='x' && s.charAt(s.length()-1)=='x'){
            out.println('x');
        }else out.println('o');
	}
}
