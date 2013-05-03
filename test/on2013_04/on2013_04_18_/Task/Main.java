package on2013_04.on2013_04_18_.Task;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("test/on2013_04/on2013_04_18_/Task/Task.task"))
			Assert.fail();
	}
}
