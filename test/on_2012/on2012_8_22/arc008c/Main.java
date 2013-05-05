package on_2012.on2012_8_22.arc008c;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_22.arc008c.ARC008C",
                "SINGLE",
                "empty",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
