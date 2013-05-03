package on2012_5_26.tworectangles;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!TopCoderTester.test("String describeIntersection(int[] A, int[] B)",
			"on2012_5_26.tworectangles.TwoRectangles",
			"0,0,3,2;;1,1,5,3;;rectangle;;true::0,0,5,3;;1,2,2,3;;rectangle;;true::1,1,6,2;;3,2,5,4;;segment;;true::0,1,2,3;;2,0,5,2;;segment;;true::0,0,1,1;;1,1,5,2;;point;;true::1,1,2,2;;3,1,4,2;;none;;true"))
		{
			Assert.fail();
		}
	}
}
