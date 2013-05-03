package on2012_7_5.taskb;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_7_5.taskb.TaskB",
			"SINGLE",
			"X Y Z/__;;X,Y,Z;;true::A B, C/__;;A,B,,C;;true::QWERTY/__;;QWERTY;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
