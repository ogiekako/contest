package on2013_07.on2013_07_14_kupc2012.TaskJ;

import net.egork.chelper.tester.NewTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!NewTester.test("java/test/on2013_07/on2013_07_14_kupc2012/TaskJ/TaskJ.task"))
            Assert.fail();
    }
}
