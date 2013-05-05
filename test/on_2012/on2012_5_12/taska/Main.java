package on_2012.on2012_5_12.taska;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_5_12.taska.TaskA",
                "SINGLE",
                "2 5/__1 2/__3 5;;4;;true::2 6/__1 3/__5 3;;-1;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
