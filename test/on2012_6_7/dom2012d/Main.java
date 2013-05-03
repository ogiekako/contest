package on2012_6_7.dom2012d;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_7.dom2012d.Dom2012D",
			"SINGLE",
			"4 4 2 1 4/__1 2 2 1/__2 3 2 1/__3 4 5 1/__2 4 4 2/__3 1/__3 6/__10 5 3/__/__10/__2 0 1 1 2/__1/__/__1/__4 5 2 4 1/__4 3 10 1/__3 2 2 1/__3 2 1 2/__3 2 5 2/__2 1 10 1/__3 3/__20 30/__3 2 1/__5 10/__3 2 1/__5 5 2 1 5/__1 2 10 2/__1 3 20 2/__2 4 20 1/__3 4 10 1/__4 5 20 1/__2 2/__20/__4 1/__20/__3 1/__0 0 0 0 0;;54/__-1/__63/__130;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
