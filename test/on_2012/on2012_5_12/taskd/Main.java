package on_2012.on2012_5_12.taskd;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_5_12.taskd.TaskD",
                "SINGLE",
                "2 0 0;;First/__3;;true::4 0 1;;Infinite;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
