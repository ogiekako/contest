package on2012_8_1.taske;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_8_1.taske.TaskE",
			"SINGLE",
			"3 1/__1 3 2/__;;3/__;;true::5 2/__1 3 2 1 7/__;;6/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
