package on2012_8_15.thebricktowerharddivone;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("int find(int C, int K, long H)",
			"on2012_8_15.thebricktowerharddivone.TheBrickTowerHardDivOne",
			"2;;0;;2;;4;;true::1;;7;;19;;1;;true::2;;3;;1;;14;;true::4;;7;;47;;1008981254;;true::4747;;7;;474747474747474747;;473182063;;true"))
		{
			Assert.fail();
		}
	}
}
