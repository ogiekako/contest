package on2013_12.on2013_12_08_Recruit_2013.TaskA;

import net.egork.chelper.tester.NewTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!NewTester.test("java/test/on2013_12/on2013_12_08_Recruit_2013/TaskA/TaskA.task"))
            Assert.fail();
    }
}
