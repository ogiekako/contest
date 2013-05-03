package on2012_8_15.cuttingbitstring;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("int getmin(String S)",
			"on2012_8_15.cuttingbitstring.CuttingBitString",
			"101101101;;3;;true::1111101;;1;;true::00000;;-1;;true::110011011;;3;;true::1000101011;;-1;;true::111011100110101100101110111;;5;;true"))
		{
			Assert.fail();
		}
	}
}
