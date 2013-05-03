package on2012_6_7.dom2012b;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
			"on2012_6_7.dom2012b.Dom2012B",
			"SINGLE",
			"2012 4/__83268 6/__1112 4/__0 1/__99 2/__0 0;;3 6174 1/__1 862632 7/__5 6174 1/__0 0 1/__1 0 1;;true",
			"java.io.PrintWriter"))
		{
			Assert.fail();
		}
	}
}
