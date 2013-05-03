package on2012_5_19.connectingsoldiers;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_5_19.connectingsoldiers.ConnectingSoldiers",
			"MULTI_NUMBER",
			"4/__3 8/__3 9/__2 4/__5 25/__;;0/__0/__-1/__5/__;;true::10/__30 1000/__30 1000/__30 1000/__30 1000/__30 1000/__30 1000/__30 1000/__30 1000/__30 1000/__30 1000;;505/__505/__505/__505/__505/__505/__505/__505/__505/__505/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
