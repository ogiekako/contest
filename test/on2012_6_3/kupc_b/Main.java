package on2012_6_3.kupc_b;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_3.kupc_b.KUPC_B",
			"SINGLE",
			"empty",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
