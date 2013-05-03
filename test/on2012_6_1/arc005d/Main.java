package on2012_6_1.arc005d;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_1.arc005d.ARC005D",
			"SINGLE",
			"01257/__2380;;2270+110=;;true::0123456789/__17564523527628452;;17564523527628452;;true::01/__9;;1+1+1+1+1+1+1+1+1=;;true::019/__2727;;909+909+909=;;true::01457/__245723852196245230;;175711751155145110+70011701041100110+400000000010=;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
