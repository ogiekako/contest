package on2012_6_3.kingdomanddice;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("double newFairness(int[] firstDie, int[] secondDie, int X)",
			"on2012_6_3.kingdomanddice.KingdomAndDice",
			"0,2,7,0;;6,3,8,10;;12;;0.4375;;true::0,2,7,0;;6,3,8,10;;10;;0.375;;true::0,0;;5,8;;47;;0.5;;true::19,50,4;;26,100,37;;1000;;0.2222222222222222;;true::6371,0,6256,1852,0,0,6317,3004,5218,9012;;1557,6318,1560,4519,2012,6316,6315,1559,8215,1561;;10000;;0.49;;true::11,12,13,0;;6,3,8,10;;14;;0.75;;true"))
		{
			Assert.fail();
		}
	}
}
