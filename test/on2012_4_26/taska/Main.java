package on2012_4_26.taska;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_4_26.taska.TaskA",
			"MULTI_NUMBER",
			"4/__3/__3 4/__4 10/__6 10/__9/__3/__3 4/__4 10/__7 10/__9/__2/__6 6/__10 3/__13/__2/__6 6/__10 3/__14;;Case #1/: YES/__Case #2/: NO/__Case #3/: YES/__Case #4/: NO;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
