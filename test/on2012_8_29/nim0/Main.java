package on2012_8_29.nim0;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("int count(int K, int L)",
			"on2012_8_29.nim0.Nim",
			"3;;7;;6;;true::4;;13;;120;;true::10;;100;;294844622;;true::123456789;;12345;;235511047;;true::1;;2;;0;;true"))
		{
			Assert.fail();
		}
	}
}
