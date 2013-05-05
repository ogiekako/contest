package on_2012.on2012_8_24.tenka_a;

import net.egork.chelper.tester.Tester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!Tester.test("net.ogiekako.algorithm.io.MyScanner",
                "on_2012.on2012_8_24.tenka_a.Tenka_A",
                "SINGLE",
                "10;;2;;true::11;;2;;true::10000000000;;16;;true::1;;1;;true::3;;1;;true",
                "java.io.PrintWriter")) {
            Assert.fail();
        }
    }
}
