package on_2012.on2012_8_1.taska;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_1.taska.TaskA",
                "SINGLE",
                "2/__1 2/__;;YES/__;;true::3/__3 2 1/__;;YES/__;;true::4/__4 3 2 1/__;;NO/__;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
