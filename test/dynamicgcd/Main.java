package dynamicgcd;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"dynamicgcd.DynamicGCD",
			"SINGLE",
			"6/__0 4/__0 5/__1 5/__5 2/__3 5/__3 1 3 2 4 2/__5/__F 3 5/__C 1 3 1/__C 3 4 4/__F 3 0/__F 2 5/__;;2/__7/__1/__;;true::6/__0 4/__0 5/__1 5/__5 2/__3 5/__3 1 3 2 4 2/__7/__F 3 5/__C 1 3 1/__C 3 4 4/__F 3 0/__F 2 5/__C 2 0 1/__F 2 4;;2/__7/__1/__4;;true::1/__4/__1/__F 0 0;;4;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
