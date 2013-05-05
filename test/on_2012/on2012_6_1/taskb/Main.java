package on_2012.on2012_6_1.taskb;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_1.taskb.TaskB",
                "SINGLE",
                "2 4/__1234 4321;;43211234;;true::3 3/__111 333 222/__;;333222111;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
