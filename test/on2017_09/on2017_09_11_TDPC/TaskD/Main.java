package on2017_09.on2017_09_11_TDPC.TaskD;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("test/on2017_09/on2017_09_11_TDPC/TaskD/TaskD.task"))
			Assert.fail();
	}
}
