package on2012_6_4.giftrift;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_4.giftrift.GiftRift",
			"SINGLE",
			"2 3/__9 8 8/__2 6 11/__;;8/__;;true::3 3/__9 8 11/__2 6 34/__5 9 11/__;;GUESS/__;;true::2 2/__10 10/__10 10/__;;10/__;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
