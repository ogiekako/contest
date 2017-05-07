package on2017_04.on2017_04_29_RCC.Task;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("test/on2017_04/on2017_04_29_RCC/Task/Task.task"))
			Assert.fail();
	}
}
