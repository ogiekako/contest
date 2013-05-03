package on2012_6_7.dom2012c;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_7.dom2012c.Dom2012C",
			"SINGLE",
			"4/__6 4/__6 4/__6 4/__6 4/__2/__5 3/__5 3/__8/__4 2/__4 2/__4 2/__4 2/__4 2/__4 2/__4 2/__4 2/__6/__4 6/__1 5/__2 3/__5 3/__2 4/__4 2/__0;;0 1 1 0 0 1/__1 0 0 0 1 0/__1 2 2 1 0 0/__2 3 0 0 0 0;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
