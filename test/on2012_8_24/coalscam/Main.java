package on2012_8_24.coalscam;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_8_24.coalscam.CoalScam",
			"MULTI_NUMBER",
			"2/__3 2 1/__0 1 5/__1 2 4/__0 1 10/__3 1 1/__0 1 1/__0 1 3/__;;10 14/__Impossible/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
