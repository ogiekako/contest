package on2012_6_3.kupc_d;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_3.kupc_d.KUPC_D",
			"SINGLE",
			"8 4/__3 7/__1 5/__2 5/__4 8;;2;;true::8 4/__1 4/__2 5/__3 6/__4 7;;Impossible;;true::1 1/__1 1;;1;;true::2 1/__2 2;;Impossible;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
