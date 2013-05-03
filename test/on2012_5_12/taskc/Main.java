package on2012_5_12.taskc;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_5_12.taskc.TaskC",
			"SINGLE",
			"2/__1.0/__0.(9);;1;;true::3/__3.(142857)/__3.1(428571)/__3.14(285714);;1;;true::2/__1.0/__1.(0);;1;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
