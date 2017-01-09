package on2016_09.on2016_09_04_AGC004.TaskB;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("test/on2016_09/on2016_09_04_AGC004/TaskB/TaskB.task"))
			Assert.fail();
	}
}
