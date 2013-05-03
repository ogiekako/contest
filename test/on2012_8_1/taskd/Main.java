package on2012_8_1.taskd;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_8_1.taskd.TaskD",
			"SINGLE",
			"2 1/__;;36/__;;true::2 2/__;;240/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
