package on2017_01.on2017_01_09_FHC17Qual.TaskC;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("test/on2017_01/on2017_01_09_FHC17Qual/TaskC/TaskC.task"))
			Assert.fail();
	}
}
