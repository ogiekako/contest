package on2012_4_26.taskb;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_4_26.taskb.TaskB",
			"MULTI_NUMBER",
			"empty",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
