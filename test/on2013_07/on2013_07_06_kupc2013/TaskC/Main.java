package on2013_07.on2013_07_06_kupc2013.TaskC;

import net.egork.chelper.tester.NewTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!NewTester.test("java/test/on2013_07/on2013_07_06_kupc2013/TaskC/TaskC.task"))
            Assert.fail();
    }
}
