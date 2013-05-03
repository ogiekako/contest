package on2012_4_11.poj3281_maxflow_dining;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_4_11.poj3281_maxflow_dining.POJ3281_MaxFlow_Dining",
			"SINGLE",
			"4 3 3/__2 2 1 2 3 1/__2 2 2 3 1 2/__2 2 1 3 1 2/__2 1 1 3 3;;3;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
