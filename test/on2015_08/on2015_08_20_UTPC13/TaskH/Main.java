package on2015_08.on2015_08_20_UTPC13.TaskH;

import net.egork.chelper.tester.NewTester;

import org.junit.Assert;
import org.junit.Test;

public class Main {
	@Test
	public void test() throws Exception {
		if (!NewTester.test("test/on2015_08/on2015_08_20_UTPC13/TaskH/TaskH.task"))
			Assert.fail();
	}
}
