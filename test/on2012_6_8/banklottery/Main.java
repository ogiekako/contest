package on2012_6_8.banklottery;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("double expectedAmount(int[] accountBalance, int weeklyJackpot, int weekCount)",
			"on2012_6_8.banklottery.BankLottery",
			"100,100;;100;;2;;200.0;;true::2,2,2;;1;;2;;2.6666666666666665;;true::1,2,3,4,5,6,7,8,9,10;;100;;20;;37.36363636363636;;true::0,200,200,0,300,300,600;;3;;776;;0.0;;true"))
		{
			Assert.fail();
		}
	}
}
