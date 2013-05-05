package on_2012.on2012_6_3.kupc_h;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_6_3.kupc_h.KUPC_H",
                "SINGLE",
                "4 4/__0 0 0 1/__0 1 1 0/__0 1 1 0/__1 0 0 0/__�o�͗� 1;;1 0 0 0/__0 0 0 0/__0 0 0 0/__0 0 0 1;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
