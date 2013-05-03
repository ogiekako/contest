package on2012_7_5.taska;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_7_5.taska.TaskA",
			"SINGLE",
			"0;;1;;true::5;;8;;true::45;;1836311903;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
