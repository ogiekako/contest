package on2012_8_15.greedyfromlarger;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_8_15.greedyfromlarger.GreedyFromLarger",
			"SINGLE",
			"((((()())(()(()())))())(((()())(()(()())))(()())))/__;;5/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
