package on2012_6_3.kingdomandtrees;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("int minLevel(int[] heights)",
			"on2012_6_3.kingdomandtrees.KingdomAndTrees",
			"9,5,11;;3;;true::5,8;;0;;true::1,1,1,1,1;;4;;true::548,47,58,250,2012;;251;;true"))
		{
			Assert.fail();
		}
	}
}
