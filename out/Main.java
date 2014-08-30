import java.util.Scanner;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 * @author ogiekako
 */
public class Main {
	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		MyScanner in = new MyScanner(inputStream);
		MyPrintWriter out = new MyPrintWriter(outputStream);
		TestTask solver = new TestTask();
		solver.solve(1, in, out);
		out.close();
	}
}

class TestTask {
    public void solve(int testNumber, Scanner in, PrintWriter out) {
        out.println(in.nextInt() + in.nextInt());
    }
}

