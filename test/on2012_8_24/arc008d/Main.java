package on2012_8_24.arc008d;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_8_24.arc008d.ARC008D",
			"SINGLE",
			"1 1/__1 1 0;;1/__1;;true::3 2/__2 -1 1/__2 1 0.5;;0/__1.5;;true::4 5/__1 -0.8 0.5/__2 0.72 -0.21/__3 1 0.8/__4 0.3 0.4142/__3 1 0.8;;-0.426/__1;;true::10 10/__6 0.5674 -1/__3 -0.432 0.1235/__8 0.92 0/__4 -0.673 0.12578/__6 0.986 -0.567/__1 0.11111 1/__10 0.98765 -0.1234/__10 0.18543 -0.16532/__9 -0.756 0.54849/__2 -1 0.74436;;-1.175043/__1;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
