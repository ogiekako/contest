package on2012_8_24.oldbridges;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("String isPossible(String[] bridges, int a1, int a2, int an, int b1, int b2, int bn)",
			"on2012_8_24.oldbridges.OldBridges",
			"XOXX,OXOX,XOXO,XXOX;;0;;1;;1;;2;;3;;1;;Yes;;true::XOXX,OXOX,XOXO,XXOX;;0;;2;;1;;1;;3;;1;;No;;true::XOXO,OXOX,XOXO,OXOX;;0;;2;;1;;1;;3;;1;;Yes;;true::XNXO,NXOX,XOXO,OXOX;;0;;2;;1;;1;;3;;2;;No;;true::XOXOO,OXOXO,XOXOO,OXOXO,OOOOX;;0;;2;;2;;1;;3;;2;;Yes;;true::XOOOX,OXOOX,OOXOX,OOOXN,XXXNX;;0;;4;;3;;1;;2;;2;;No;;true::XOXX,OXOX,XOXO,XXOX;;1;;2;;1;;0;;3;;1;;No;;true::XOONNOOOO,OXOOOOOOO,OOXOXNOOO,NOOXOOOOO,NOXOXOOOO,OONOOXOXO,OOOOOOXOO,OOOOOXOXX,OOOOOOOXX;;8;;1;;8;;4;;0;;49;;No;;true"))
		{
			Assert.fail();
		}
	}
}
