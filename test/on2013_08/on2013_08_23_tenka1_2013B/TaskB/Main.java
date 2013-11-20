package on2013_08.on2013_08_23_tenka1_2013B.TaskB;

import net.egork.chelper.tester.NewTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!NewTester.test("test/on2013_08/on2013_08_23_tenka1_2013B/TaskB/TaskB.task"))
            Assert.fail();
    }
}
