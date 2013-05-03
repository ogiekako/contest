package on2012_6_3.kupc_c;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_3.kupc_c.KUPC_C",
			"SINGLE",
			"6 2/__2 1 5/__4 2 3 4 6/__2/__1 2/__2 5;;0;;true::6 2/__2 1 5/__4 2 3 4 6/__3/__2 3/__3 4/__1 2;;3;;true::10 3/__3 4 1 9/__3 10 5 2/__4 3 8 7 6/__9/__3 4/__1 2/__1 9/__6 8/__1 6/__1 8/__6 7/__6 10/__7 8;;5;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
