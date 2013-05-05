package on_2012.on2012_8_15.tenka1b1;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_15.tenka1b1.Tenka1B1",
                "SINGLE",
                "2 3 2;;23;;true::1 1 1;;1/__106;;true::2 4 6;;104;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
